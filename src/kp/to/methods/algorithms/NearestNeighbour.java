package kp.to.methods.algorithms;

import kp.to.Utils;
import kp.to.methods.algorithms.Algorithm;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NearestNeighbour implements Algorithm {

    private final Random random;
    private List<Point> pointList;
    public NearestNeighbour() {
        this.random = new Random(System.currentTimeMillis());
    }

    private Point getNearestPoint(Point reference) {
        Point nearestPoint = null;
        int minDist = Integer.MAX_VALUE;
        for (Point p : pointList) {
            int dist = Utils.length(reference, p);
            if (dist < minDist || (dist == minDist && random.nextBoolean())) {
                minDist = dist;
                nearestPoint = p;
            }
        }
        return nearestPoint;
    }

    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        for (Point startPoint : pointList) {
            this.pointList = new ArrayList<>(pointList);
            RoundResult r = calculate(startPoint);
            result.addResult(r);
            System.out.println(r.print(false));
        }
        return result;
    }

    private RoundResult calculate(Point startPoint) {
        RoundResult result = new RoundResult(startPoint);
        Point previouslyAdded = startPoint;
        pointList.remove(startPoint);
        while (!pointList.isEmpty()) {
            Point nearest = getNearestPoint(previouslyAdded);
            pointList.remove(nearest);
            result.add(nearest);
            previouslyAdded = nearest;
        }
        return result;
    }
}
