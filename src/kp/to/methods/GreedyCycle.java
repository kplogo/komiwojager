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
        int count = pointList.size();
        for (int i = 0; i < count; i++) {
            RoundResult roundResult = calculate(pointList, i);
            result.addResult(roundResult);
            System.out.println(roundResult.print(false));
        }
        return result;
    }

    private RoundResult calculate(List<Point> pointList, int startPointIndex) {
        RoundResult roundResult = new RoundResult(pointList.get(startPointIndex));
        while (roundResult.size() != pointList.size()) {
            addNearestPoint(pointList, roundResult);
        }
        return roundResult;
    }

    private int addNearestPoint(List<Point> pointList, RoundResult startPoint) {
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
                if (minForPoint > length) {
                    minForPoint = length;
                    minPositionForPoint = j+1;
                }
            }
//            System.out.println(i);
//            System.out.println(minForPoint);
            if (min > minForPoint) {
                min = minForPoint;
                minPosition = minPositionForPoint;
                minId = i;
            }
        }
        Point point = pointList.get(minId);
        startPoint.add(minPosition, point);

        return minId;
    }

    private int calculateLength(RoundResult startPoint, Point point, int newPosition) {
        int length = 0;
        boolean added = false;
        for (int k = 0; k < startPoint.size(); k++) {
            Point prevPoint = startPoint.get(k);
            Point actualPoint;
            actualPoint = startPoint.get((k + 1) % startPoint.size());
            if (newPosition == k) {
                added = true;
                length += Utils.length(prevPoint, point);
                length += Utils.length(point, actualPoint);
            } else {
                length += Utils.length(prevPoint, actualPoint);
            }
        }
        if (!added) {
            length += Utils.length(startPoint.get(0), point);
            length += Utils.length(point, startPoint.get(startPoint.size() - 1));
        }
        return length;
    }
}
