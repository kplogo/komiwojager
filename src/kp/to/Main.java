package kp.to;

import kp.to.methods.*;
import kp.to.model.Point;
import kp.to.model.Result;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Point> pointList = parseToPoints(readFromFile("resources/data.tsp"));
//        Algorithm algorithm = new GreedyCycle();
        Algorithm algorithm = new GraspGreedy();
//        Algorithm algorithm = new GraspStromy();
//        Algorithm algorithm = new NearestNeighbour();
        Date start = Calendar.getInstance().getTime();
        Result result = algorithm.run(pointList);
        System.out.println(result.getBestResult().print(true));
        System.out.println(Calendar.getInstance().getTime().getTime() - start.getTime());

    }

    private static List<Point> parseToPoints(String data) {
        List<Point> points = new ArrayList<>();
        for (String line : data.split(System.lineSeparator())) {
            points.add(new Point(line));
        }
        return points;
    }

    private static String readFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            boolean start = false;

            while (line != null) {
                if (line.equals("EOF")) {
                    start = false;
                }
                if (start) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                if (line.equals("NODE_COORD_SECTION")) {
                    start = true;
                }
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return null;
    }
}
