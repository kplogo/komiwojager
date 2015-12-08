package kp.to.methods;

import kp.to.Utils;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.LinkedList;
import java.util.List;

public class GreedyCycle implements Algorithm {
    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        for (int i = 0; i < pointList.size(); i++) {
            RoundResult roundResult = calculate(pointList, i);
            result.addResult(roundResult);
        }
        return result;
    }

    private RoundResult calculate(List<Point> pointList, int startPointIndex) {
        RoundResult roundResult = new RoundResult(startPointIndex);
        int size = pointList.size();
        boolean[] usedPoints = new boolean[size];
        for (int i = 0; i < size; i++) {
            usedPoints[i] = false;
        }
        Point startPoint = pointList.get(startPointIndex);
        usedPoints[startPointIndex] = true;
        int nearestPointId = getNearestPoint(pointList, startPoint);
        usedPoints[nearestPointId] = true;
        LinkedList<Point> result = new LinkedList<>();

        return null;
    }

    private int getNearestPoint(List<Point> pointList, Point startPoint) {
        int min = Integer.MAX_VALUE;
        int minId = -1;
        for (int i = 0; i < pointList.size(); i++) {

            Point point = pointList.get(i);
            if (point == startPoint) {
                continue;
            }
            int length = Utils.length(startPoint, point);
            if (min > length) {
                min = length;
                minId = i;
            }
        }
        return minId;
    }
}
