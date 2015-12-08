package kp.to.methods;

import kp.to.Utils;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;

public class GreedyCycle implements Algorithm {
    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        int count = 1;//pointList.size();
        for (int i = 0; i < count; i++) {
            RoundResult roundResult = calculate(pointList, i);
            result.addResult(roundResult);
        }
        return result;
    }

    private RoundResult calculate(List<Point> pointList, int startPointIndex) {
        RoundResult roundResult = new RoundResult(pointList.get(startPointIndex));
        int nearestPointId = 0;
        while (roundResult.size() != pointList.size()) {
            nearestPointId = getNearestPoint(pointList, roundResult);
        }
        return roundResult;
    }

    private int getNearestPoint(List<Point> pointList, RoundResult startPoint) {
        int min = Integer.MAX_VALUE;
        int minId = -1;
        int minPosition = -1;
        for (int i = 0; i < pointList.size(); i++) {
            Point point = pointList.get(i);
            if (startPoint.contains(point)) {
                continue;
            }
            int minForPoint = Integer.MAX_VALUE;
            int minPositionForPoint = 0;

            for (int j = 0; j < startPoint.size(); j++) {
                int length = calculateLength(startPoint, point, j);
                System.out.println(length);
                if (minForPoint > length) {
                    minForPoint = length;
                    minPositionForPoint = i;
                }
            }
            if (min > minForPoint) {
                min = minForPoint;
                minPosition = minPositionForPoint;
                minId = i;
            }
        }
        System.out.println(min);
        System.out.println(minId);
        System.out.println(minPosition);
        Point point = pointList.get(minId);
        startPoint.add(minPosition, point);

        return minId;
    }

    private int calculateLength(RoundResult startPoint, Point point, int newPosition) {
        int length = 0;
        for (int k = 0; k < startPoint.size(); k++) {
            Point prevPoint = startPoint.get(k);
            Point actualPoint;
            if (k == startPoint.size() - 1) {
                actualPoint = point;
                newPosition = -1;
            } else {
                actualPoint = startPoint.get(k + 1);
            }
            if (newPosition == k) {
                length += Utils.length(prevPoint, point);
                length += Utils.length(point, actualPoint);
            } else {
                length += Utils.length(prevPoint, actualPoint);
            }
        }
        return length;
    }
}
