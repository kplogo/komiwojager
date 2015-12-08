package kp.to.methods;

import kp.to.Utils;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by student on 2015-12-08.
 */
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
        }
        return result;
    }

    private RoundResult calculate(Point startPoint) {
        RoundResult result = new RoundResult(startPoint);
        Point previouslyAdded = startPoint;
        pointList.remove(startPoint);
        int routeLength = 0;
        while (!pointList.isEmpty()) {
            Point nearest = getNearestPoint(previouslyAdded);
            pointList.remove(nearest);
            result.add(nearest);
            routeLength += Utils.length(previouslyAdded, nearest);
            previouslyAdded = nearest;
        }
        routeLength += Utils.length(previouslyAdded, startPoint);
        result.setRouteLength(routeLength);
        return result;
    }
}
