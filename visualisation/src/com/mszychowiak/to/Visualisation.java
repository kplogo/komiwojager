package com.mszychowiak.to;

import kp.to.Main;
import kp.to.model.*;
import kp.to.model.Point;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by Miko³aj Szychowiak on 21.12.2015.
 */
public class Visualisation extends JFrame {

    private List<Point> points;
    private Map<String, List<Integer>> orders;
    private static final String[] res = new String[]{"Best", "Middle", "Worst"};

    public static void main(String[] args) throws IOException {
        List<kp.to.model.Point> pointList = Main.parseToPoints(Main.readFromFile("resources/data.tsp"));
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Visualisation v = new Visualisation();
                    v.init(pointList);
                    v.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void init(List<Point> pointList) {
        this.points = pointList;
        orders = create();
        for (String key : orders.keySet()) {
            List<Integer> order = orders.get(key);
            VisualisePanel panel = new VisualisePanel();
            //panel.draw(key, order);
        }
        System.out.println("Finished");
        System.exit(0);
    }
    private static int result = 0;
    private Map<String, List<Integer>> create() {
        Map<String, List<Integer>> map = new HashMap<>();
        File main = new File ("resources");
        for (File f : main.listFiles()) {
            if (f.getName().startsWith("results_")) {
                final String keyBase = f.getName().split("_")[1];
                final List<Integer> list = new LinkedList<>();
                try {
                    result = 0;
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    br.lines().forEach(s -> {
                        String[] v = s.split(";");
                        if (result == 0)
                        System.out.println(keyBase);
                        for (int i = 0; i < v.length - 1; i++) {
                            list.add(Integer.parseInt(v[i]));
                            if (result == 0)
                            System.out.println(points.get(Integer.parseInt(v[i])).getLabel() + ";" + points.get(Integer.parseInt(v[i])).getX() + ";" + points.get(Integer.parseInt(v[i])).getY());
                        }
                        map.put(keyBase + res[result], list);
                        result++;
                    });
                    br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    private class VisualisePanel extends JPanel {
        private int maxX;
        private int maxY;
        private int minX;
        private int minY;
        private List<Integer> order;

        public void draw(String fileName, List<Integer> order) {
            this.maxX = getMaxX();
            this.maxY = getMaxY();
            this.minX = getMinX();
            this.minY = getMinY();
            this.order = order;
            setSize(maxX + minX, maxY + minY);
            BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g2 = bi.createGraphics();
            this.paint(g2);
            try {
                ImageIO.write(bi, "png", new File("resources/" + fileName + ".png"));
                System.out.println(fileName + " written");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.BLACK);

            Point lastPoint = points.get(order.get(0));
            for (int i = 1; i < order.size(); i++) {
                Point p = points.get(order.get(i));
                g.drawLine(lastPoint.getX(), lastPoint.getY(), p.getX(), p.getY());
                lastPoint = p;
            }
            g.drawLine(lastPoint.getX(), lastPoint.getY(), points.get(order.get(0)).getX(), points.get(order.get(0)).getY());

            for (Point p : points) {
                g.setColor(Color.RED);
                g.drawRect(p.getX()-2, p.getY() - 2, 4, 4);
                g.fillRect(p.getX()-2, p.getY() - 2, 4, 4);
                g.setColor(Color.BLUE);
                g.drawString(p.getLabel()+ "", p.getX()+3, p.getY());
            }
        }
    }

    private String getFileName() {
        return null;
    }

    private int getMaxY() {
        int max = Integer.MIN_VALUE;
        for (Point p : points) {
            max = Math.max(max, p.getY());
        }
        return max;
    }

    private int getMaxX() {
        int max = Integer.MIN_VALUE;
        for (Point p : points) {
            max = Math.max(max, p.getX());
        }
        return max;
    }
    private int getMinY() {
        int min = Integer.MAX_VALUE;
        for (Point p : points) {
            min = Math.min(min, p.getY());
        }
        return min;
    }

    private int getMinX() {
        int min = Integer.MAX_VALUE;
        for (Point p : points) {
            min = Math.min(min, p.getX());
        }
        return min;
    }

}
