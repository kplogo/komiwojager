package kp.to;

import kp.to.methods.algorithms.Algorithm;
import kp.to.methods.algorithms.IterationSearchAlgorithm;
import kp.to.methods.algorithms.MultipleStartAlgorithm;
import kp.to.methods.algorithms.StandardAlgorithm;
import kp.to.methods.constructors.Grasp;
import kp.to.methods.localsearch.GreedyLocalSearch;
import kp.to.methods.localsearch.StromyLocalSearch;
import kp.to.methods.localsearch.moves.CandidateMovesGenerator;
import kp.to.methods.localsearch.moves.StandardMovesGenerator;
import kp.to.methods.localsearch.type.EdgeSwap;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {

    public static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws IOException {
        long timeMillis = System.currentTimeMillis();
        List<Point> pointList = parseToPoints(readFromFile("resources/data.tsp"));
//        Algorithm algorithm = new StandardAlgorithm(new GreedyLocalSearch(new NodeSwap()),new Grasp());
//        Algorithm algorithm = new StandardAlgorithm(new GreedyLocalSearch(new EdgeSwap()),new Grasp());
//        Algorithm algorithm = new StandardAlgorithm(new GreedyRandomLocalSearch(new NodeSwap()),new Grasp());
//        Algorithm algorithm = new StandardAlgorithm(new StromyLocalSearch(new NodeSwap()),new Grasp());
//        Algorithm algorithm = new NearestNeighbour();
        List<Algorithm> algorithms = new ArrayList<>();
        //algorithms.add(new GreedyCycle());
        //algorithms.add(new NearestNeighbour());
//        algorithms.add(new StandardAlgorithm(null, new Grasp()));
//        algorithms.add(new StandardAlgorithm(new GreedyLocalSearch(new EdgeSwap()),new Grasp()));
//        algorithms.add(new StandardAlgorithm(new StromyLocalSearch(new EdgeSwap()),new Grasp()));
//        algorithms.add(new StandardAlgorithm(new GreedyRandomLocalSearch(new EdgeSwap()),new Grasp()));
//        algorithms.add(new StandardAlgorithm(new GreedyLocalSearch(new EdgeSwap()),new Grasp()));
//        algorithms.add(new MultipleStartAlgorithm(new StromyLocalSearch(new EdgeSwap()), new Grasp()));
        EdgeSwap swapType = new EdgeSwap();
        CandidateMovesGenerator candidateMovesGenerator = new CandidateMovesGenerator();
        StandardMovesGenerator standardMovesGenerator = new StandardMovesGenerator();
        Grasp solutionConstructor = new Grasp();
        algorithms.add(new StandardAlgorithm(new StromyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor));
        algorithms.add(new StandardAlgorithm(new StromyLocalSearch(swapType, standardMovesGenerator), solutionConstructor));
        algorithms.add(new StandardAlgorithm(new GreedyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor));
        algorithms.add(new StandardAlgorithm(new GreedyLocalSearch(swapType, standardMovesGenerator), solutionConstructor));
        algorithms.add(new MultipleStartAlgorithm(new StromyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor));
        algorithms.add(new MultipleStartAlgorithm(new StromyLocalSearch(swapType, standardMovesGenerator), solutionConstructor));
        algorithms.add(new MultipleStartAlgorithm(new GreedyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor));
        algorithms.add(new MultipleStartAlgorithm(new GreedyLocalSearch(swapType, standardMovesGenerator), solutionConstructor));
        algorithms.add(new IterationSearchAlgorithm(new StromyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor));
        algorithms.add(new IterationSearchAlgorithm(new StromyLocalSearch(swapType, standardMovesGenerator), solutionConstructor));
        algorithms.add(new IterationSearchAlgorithm(new GreedyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor));
        algorithms.add(new IterationSearchAlgorithm(new GreedyLocalSearch(swapType, standardMovesGenerator), solutionConstructor));
        printResult(pointList, algorithms);
        System.out.println(System.currentTimeMillis() - timeMillis);
    }

    private static void printResult(List<Point> pointList, List<Algorithm> algorithms) {
        for (Algorithm algorithm : algorithms) {
            Result r = algorithm.run(pointList);
            r.stop();
            System.err.println(algorithm.toString());
            System.err.println("Time: " + r.getTime());
            for (RoundResult rr : r.getResultsToReport()) {
                System.err.println(rr.printResult());
//                System.out.println(rr.print(true));
            }
            System.err.println();
        }
    }

    private static void writeToFile(Algorithm a, RoundResult[] resultsToReport) {
        try {
            PrintWriter pw = new PrintWriter("resources/results_" + a.getClass().getSimpleName() + "_" + new SimpleDateFormat("yyyyMMdd_kkmm").format(new Date(System.currentTimeMillis())));
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
