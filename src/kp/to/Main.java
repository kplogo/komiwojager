package kp.to;

import kp.to.methods.algorithms.*;
import kp.to.methods.childcreators.ParentChildCreator;
import kp.to.methods.childcreators.RandomChildCreator;
import kp.to.methods.constructors.Grasp;
import kp.to.methods.localsearch.GreedyLocalSearch;
import kp.to.methods.localsearch.StromyLocalSearch;
import kp.to.methods.localsearch.moves.CandidateMovesGenerator;
import kp.to.methods.localsearch.moves.StandardMovesGenerator;
import kp.to.methods.localsearch.type.EdgeSwap;
import kp.to.methods.stopconditions.IterationStopCondition;
import kp.to.methods.stopconditions.IterationStopConditionWithTime;
import kp.to.methods.stopconditions.StopCondition;
import kp.to.methods.stopconditions.TimeStopCondition;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {

    public static final int MAX_ITERATIONS = 100;
    public static final int MAX_REPEATS = 10;

    public static void main(String[] args) throws IOException {
        long timeMillis = System.currentTimeMillis();
        List<Point> pointList = parseToPoints(readFromFile("resources/data.tsp"));
//        List<Algorithm> algorithms = firstLab();
//        List<Algorithm> algorithms = secondLab();
//        List<Algorithm> algorithms = thirdLab(getTimeLimit(pointList));
        List<Algorithm> algorithms = forthLab(getTimeLimit(pointList));
        printResult(pointList, algorithms);
        System.out.println(System.currentTimeMillis() - timeMillis);
    }

    private static long getTimeLimit(List<Point> pointList) {
        IterationStopConditionWithTime getTimeCondition = new IterationStopConditionWithTime(MAX_ITERATIONS);
        new MultipleStartAlgorithm(new StromyLocalSearch(new EdgeSwap(), new StandardMovesGenerator()), new Grasp(), getTimeCondition, 1).run(pointList);
        final long timeLimit = getTimeCondition.getTime();
        System.out.println("Time limit: " + timeLimit);
        return timeLimit;
    }

    private static List<Algorithm> forthLab(long timeLimit) {
        List<Algorithm> algorithms = new ArrayList<>();
        algorithms.add(new EvolutionAlgorithm(new StromyLocalSearch(new EdgeSwap(), new StandardMovesGenerator()), new Grasp(), new TimeStopCondition(timeLimit), new RandomChildCreator()));
        algorithms.add(new EvolutionAlgorithm(new StromyLocalSearch(new EdgeSwap(), new StandardMovesGenerator()), new Grasp(), new TimeStopCondition(timeLimit), new ParentChildCreator()));
        return algorithms;
    }

    private static List<Algorithm> thirdLab(long timeLimit) {
        List<Algorithm> algorithms = new ArrayList<>();
        StandardMovesGenerator standardMovesGenerator = new StandardMovesGenerator();
        EdgeSwap swapType = new EdgeSwap();
        CandidateMovesGenerator candidateMovesGenerator = new CandidateMovesGenerator();
        Grasp solutionConstructor = new Grasp();
        StopCondition timeStopCondition = new TimeStopCondition(timeLimit);
//        algorithms.add(new StandardAlgorithm(new StromyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor, timeStopCondition,MAX_REPEATS));
//        algorithms.add(new StandardAlgorithm(new StromyLocalSearch(swapType, standardMovesGenerator), solutionConstructor, timeStopCondition,MAX_REPEATS));
//        algorithms.add(new StandardAlgorithm(new GreedyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor, timeStopCondition,MAX_REPEATS));
//        algorithms.add(new StandardAlgorithm(new GreedyLocalSearch(swapType, standardMovesGenerator), solutionConstructor, timeStopCondition,MAX_REPEATS));
//        algorithms.add(new MultipleStartAlgorithm(new StromyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor, timeStopCondition,MAX_REPEATS));
//        algorithms.add(new MultipleStartAlgorithm(new GreedyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor, timeStopCondition,MAX_REPEATS));
//        algorithms.add(new IterationSearchAlgorithm(new StromyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor, timeStopCondition,MAX_REPEATS));
//        algorithms.add(new IterationSearchAlgorithm(new GreedyLocalSearch(swapType, candidateMovesGenerator), solutionConstructor, timeStopCondition,MAX_REPEATS));
        algorithms.add(new MultipleStartAlgorithm(new StromyLocalSearch(swapType, standardMovesGenerator), solutionConstructor, timeStopCondition, MAX_REPEATS));
        algorithms.add(new IterationSearchAlgorithm(new StromyLocalSearch(swapType, standardMovesGenerator), solutionConstructor, timeStopCondition, MAX_REPEATS));
        algorithms.add(new MultipleStartAlgorithm(new GreedyLocalSearch(swapType, standardMovesGenerator), solutionConstructor, timeStopCondition, MAX_REPEATS));
        algorithms.add(new IterationSearchAlgorithm(new GreedyLocalSearch(swapType, standardMovesGenerator), solutionConstructor, timeStopCondition, MAX_REPEATS));
        return algorithms;
    }

    private static List<Algorithm> secondLab() {
        List<Algorithm> algorithms = new ArrayList<>();
        algorithms.add(new StandardAlgorithm(new GreedyLocalSearch(new EdgeSwap(), new StandardMovesGenerator()), new Grasp(), new IterationStopCondition(MAX_ITERATIONS)));
        algorithms.add(new StandardAlgorithm(new StromyLocalSearch(new EdgeSwap(), new StandardMovesGenerator()), new Grasp(), new IterationStopCondition(MAX_ITERATIONS)));
        return algorithms;
    }

    private static List<Algorithm> firstLab() {
        List<Algorithm> algorithms = new ArrayList<>();
        algorithms.add(new GreedyCycle());
        algorithms.add(new NearestNeighbour());
        algorithms.add(new StandardAlgorithm(null, new Grasp(), new IterationStopCondition(MAX_ITERATIONS)));
        return algorithms;
    }

    private static void printResult(List<Point> pointList, List<Algorithm> algorithms) {
        for (Algorithm algorithm : algorithms) {
            Result r = algorithm.run(pointList);
            r.stop();
            System.err.println(algorithm.toString());
            System.err.println("Time: " + r.getTime());
            System.err.println("Iterations: " + r.getIterationCount());
            for (RoundResult rr : r.getResultsToReport()) {
                System.err.println(rr.printResult());
            }
//            System.out.println(r.getBestResult().print(true));
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
