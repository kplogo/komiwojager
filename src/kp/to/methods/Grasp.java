package kp.to.methods;

import kp.to.Utils;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Grasp implements Algorithm {

    public static final int MAX_ITERATIONS = 123;
    public static final int ALFA = 5;
    public static final int BETA = 35;

    @Override
    public Result run(List<Point> pointList) {
        RoundResult bestSolution = new RoundResult(null);
        Result result = new Result();
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            RoundResult solution = constructSolution(pointList);
            solution = localSearch(solution);
            if (isBetterSolution(solution, bestSolution)) {
                bestSolution = solution;
            }
            result.addResult(solution);
        }
        result.addResult(bestSolution);

        return result;
    }

    private boolean isBetterSolution(RoundResult solution, RoundResult bestSolution) {
        return solution.getRouteLength() < bestSolution.getRouteLength();
    }

    private RoundResult localSearch(RoundResult solution) {
//TODO
        return solution;
    }

    private RoundResult constructSolution(List<Point> pointList) {
        RoundResult roundResult = null;
        do {
            List<Point> rclPoints = buildRCL(pointList, roundResult);
            Point selectedPoint = rclPoints.get(Utils.round(Math.random() * pointList.size()) % pointList.size());
            if (roundResult == null) {
                roundResult = new RoundResult(selectedPoint);
            } else {
                roundResult.add(selectedPoint);
            }
        } while (roundResult.size() < pointList.size());
        return roundResult;
    }

    private List<Point> buildRCL(List<Point> pointList, RoundResult solution) {
        List<Point> rclPoints = new LinkedList<>();
        List<Cost> costList = calculateCost(pointList, solution);
        int maxCost = getMaxCost(costList);
        int limit = maxCost - Utils.round(maxCost / 100.0 * ALFA);
        Stream<Cost> stream = costList.stream();
        if (solution != null) {
            stream = stream.filter(cost -> !solution.contains(cost.point));
        }
        if (ALFA > -1) {
            stream = stream.filter(cost -> cost.length >= limit);
        }
        if (BETA > -1) {
            stream = stream.limit(BETA);
        }
        stream.forEach(cost -> rclPoints.add(cost.point));
//        for (Cost point : costList) {
//            if (solution.contains(point.point)) {
//                continue;
//            }
//             if (point.length < limit) {
//                continue;
//            }
//            rclPoints.add(point.point);
//            if (rclPoints.size() > beta) {
//                break;
//            }
//        }
        return rclPoints;
    }

    private int getMaxCost(List<Cost> costList) {
        return costList.stream().max((o1, o2) -> o1.length - o2.length).get().length;
    }

    private List<Cost> calculateCost(List<Point> pointList, RoundResult solution) {
        List<Cost> costList = new LinkedList<>();
        for (Point point : pointList) {
            Point solutionEnd = solution.get(solution.size() - 1);
            int length = Utils.length(point, solutionEnd);
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
