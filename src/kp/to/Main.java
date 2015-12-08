package kp.to;

import kp.to.model.Point;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String data = readFromFile("resources/data.tsp");
        List<Point> pointList = parseToPoints(data);
    }

    private static List<Point> parseToPoints(String data) {
        ArrayList<Point> points = new ArrayList<>();
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
                if (line.equals("EOF")){
                    start = false;
                }
                if (start) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                if (line.equals("NODE_COORD_SECTION")){
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
