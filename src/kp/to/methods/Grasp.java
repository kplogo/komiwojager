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

    public static final int MAX_ITERATIONS = 150;
    public static final int ALFA = 25;
    public static final int BETA = 5;
    LocalSearch localSearch;


    public Grasp(LocalSearch localSearch) {
        this.localSearch = localSearch;
    }

    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            RoundResult solution = constructSolution(pointList);
            solution = localSearch.run(solution);
            result.addResult(solution);
            System.out.println(solution.toString());
        }

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
