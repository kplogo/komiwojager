package kp.to.methods;

import kp.to.Utils;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public abstract class Grasp implements Algorithm {

    public static final int MAX_ITERATIONS = 150;
    public static final int ALFA = 25;
    public static final int BETA = 5;

    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            RoundResult solution = constructSolution(pointList);
            solution = localSearch(solution);
            result.addResult(solution);
            System.out.println(solution.toString());
        }

        return result;
    }

    private RoundResult localSearch(RoundResult solution) {
        RoundResult newSolution;
        do {
            newSolution = getNeighbour(solution);
            if (newSolution != null) {
                solution = newSolution;
            }
        } while (newSolution != null);
        return solution;
    }

    protected abstract RoundResult getNeighbour(RoundResult solution) ;

    protected int isNewSolutionCostBetter(RoundResult solution, int a, int b) {
        int length1 = getLength(solution, a, b, false);
        int length2 = getLength(solution, a, b, true);
        return length1 - length2;

    }

    private int getLength(RoundResult solution, int a, int b, boolean inverted) {
        int a1 = (solution.size() + a - 1) % solution.size();
        int a3 = (a + 1) % solution.size();
        int b1 = (solution.size() + b - 1) % solution.size();
        int b3 = (b + 1) % solution.size();
        if (inverted) {
            if (a1 == b) {
                a1 = a;
            }
            if (a3 == b) {
                a3 = a;
            }
            if (b1 == a) {
                b1 = b;
            }
            if (b3 == a) {
                b3 = b;
            }

        }
        Point pointA1 = solution.get(a1);
        Point pointA2 = solution.get(a);
        Point pointA3 = solution.get(a3);
        Point pointB1 = solution.get(b1);
        Point pointB2 = solution.get(b);
        Point pointB3 = solution.get(b3);
        int length1;
        if (!inverted) {
            length1 = Utils.length(pointA1, pointA2);
            length1 += Utils.length(pointA2, pointA3);
            length1 += Utils.length(pointB1, pointB2);
            length1 += Utils.length(pointB2, pointB3);
        } else {
            length1 = Utils.length(pointA1, pointB2);
            length1 += Utils.length(pointB2, pointA3);
            length1 += Utils.length(pointB1, pointA2);
            length1 += Utils.length(pointA2, pointB3);
        }
        return length1;
    }

    protected RoundResult generateSolution(RoundResult solution, int a, int b) {
        RoundResult result = solution.copy();
        result.swap(a, b);
        return result;
    }

    private RoundResult constructSolution(List<Point> pointList) {
        RoundResult roundResult = new RoundResult();
        do {
            List<Point> rclPoints = buildRCL(pointList, roundResult);
            Point selectedPoint = rclPoints.get(Utils.round(Math.random() * rclPoints.size()) % rclPoints.size());
            roundResult.add(selectedPoint);
        } while (roundResult.size() < pointList.size());
        return roundResult;
    }

    private List<Point> buildRCL(List<Point> pointList, RoundResult solution) {
        List<Point> rclPoints = new LinkedList<>();
        List<Cost> costList = calculateCost(pointList, solution);
        int maxCost = getMaxCost(costList);
        int limit = maxCost + Utils.round(maxCost / 100.0 * ALFA);
        Stream<Cost> stream = costList.stream();
        if (solution != null) {
            stream = stream.filter(cost -> !solution.contains(cost.point));
        }
        if (ALFA > -1) {
            stream = stream.filter(cost -> cost.length <= limit);
        }
        if (BETA > -1) {
            stream = stream.limit(BETA);
        }
        stream.forEach(cost -> rclPoints.add(cost.point));
        return rclPoints;
    }

    private int getMaxCost(List<Cost> costList) {
        return costList.stream().min((o1, o2) -> o1.length - o2.length).get().length;
    }

    private List<Cost> calculateCost(List<Point> pointList, RoundResult solution) {

        List<Cost> costList = new LinkedList<>();
        Point solutionEnd = null;
        if (solution.size() != 0) {
            solutionEnd = solution.get(solution.size() - 1);
        }
        for (Point point : pointList) {
            if (solution.contains(point)) {
                continue;
            }
            int length;
            if (solutionEnd != null) {
                length = Utils.length(point, solutionEnd);
            } else {
                length = 0;
            }
            costList.add(new Cost(point, length));
        }
        Collections.sort(costList, (o1, o2) -> o1.length - o2.length);
        return costList;
    }

    private class Cost {
        private final Point point;
        private final int length;

        public Cost(Point point, int length) {

            this.point = point;
            this.length = length;
        }
    }
}
