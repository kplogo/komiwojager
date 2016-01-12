package kp.to;

import kp.to.methods.*;
import kp.to.methods.localsearch.type.EdgeSwap;
import kp.to.methods.localsearch.type.NodeSwap;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException {
        List<Point> pointList = parseToPoints(readFromFile("resources/data.tsp"));
//        Algorithm algorithm = new Grasp(new GreedyLocalSearch(new NodeSwap()));
        //Algorithm algorithm = new Grasp(new GreedyLocalSearch(new EdgeSwap()));
//        Algorithm algorithm = new Grasp(new GreedyRandomLocalSearch(new NodeSwap()));
//        Algorithm algorithm = new Grasp(new StromyLocalSearch(new NodeSwap()));
//        Algorithm algorithm = new NearestNeighbour();
        List<Algorithm> algorithms = new ArrayList<>();
        //algorithms.add(new GreedyCycle());
        //algorithms.add(new NearestNeighbour());
        algorithms.add(new Grasp(null));
//        algorithms.add(new Grasp(new GreedyLocalSearch(new EdgeSwap())));
//        algorithms.add(new Grasp(new StromyLocalSearch(new EdgeSwap())));
//        algorithms.add(new Grasp(new GreedyRandomLocalSearch(new EdgeSwap())));
//        algorithms.add(new Grasp(new GreedyRandomLocalSearch2(new EdgeSwap())));
        for (Algorithm a : algorithms) {
            Result r = a.run(pointList);
            System.err.println(a.toString());
            for (RoundResult rr : r.getResultsToReport()) {
                System.err.println(rr);
            }
            System.err.println();
        }
    }

    private static void writeToFile(Algorithm a, RoundResult[] resultsToReport) {
        try {
            PrintWriter pw = new PrintWriter("resources/results_"+ a.getClass().getSimpleName() + "_" + new SimpleDateFormat("yyyyMMdd_kkmm").format(new Date(System.currentTimeMillis())));
            for (RoundResult r : resultsToReport) {
                pw.println(r.toString());
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            for (RoundResult r : resultsToReport) {
                System.out.println(r.toString());
            }
        }
    }

    public static List<Point> parseToPoints(String data) {
        List<Point> points = new ArrayList<>();
        for (String line : data.split(System.lineSeparator())) {
            points.add(new Point(line));
        }
        return points;
    }

    public static String readFromFile(String fileName) throws IOException {
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
