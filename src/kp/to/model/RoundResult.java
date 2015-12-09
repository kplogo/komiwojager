package kp.to.model;

import kp.to.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Wynik dla pojedyñczej iteracji (pojedynczy wierzcho³ek startowy)
 */
public class RoundResult {
    public static final String SEPARATOR = ";";
    private int startPointIndex;
    private final List<Point> resultList;

    public RoundResult(Point startPoint) {
        this.startPointIndex = startPoint.getLabel();
        resultList = new LinkedList<>();
        resultList.add(startPoint);
    }

    public void add(Point p) {
        resultList.add(p);
    }

    public boolean contains(Point point) {
        return resultList.contains(point);
    }

    public int size() {
        return resultList.size();
    }

    public Point get(int i) {
        return resultList.get(i);
    }

    public void add(int position, Point point) {
        resultList.add(position, point);
    }

    @Override
    public String toString() {
        return print(false);
    }

    public String print(boolean detailed) {

        int startIndex = -1;
        for (Point p : resultList) {
            if (p.getLabel() == startPointIndex) {
                startIndex = resultList.indexOf(p);
                break;
            }
        }
        int direction;
        if (resultList.get(getIndex(-1, startIndex)).getLabel() > resultList.get(getIndex(1, startIndex)).getLabel()) {
            direction = 1;
        } else {
            direction = -1;
        }
        StringBuilder builder = new StringBuilder();
        int i = startIndex;
        do {
            Point point = resultList.get(i);
            if (detailed) {
                builder.append(point.getLabel())
                        .append(SEPARATOR);
                builder.append(point.getX())
                        .append(SEPARATOR)
                        .append(point.getY())
                        .append(System.lineSeparator());
            } else {
                builder.append(point.getLabel())
                        .append(SEPARATOR);
            }
            i = getIndex(direction, i);
        } while (i != startIndex);
        builder.append(getRouteLength());
        return builder.toString();
    }

    private int getIndex(int direction, int i) {
        int index = i + direction;
        if (index < 0) {
            index = resultList.size() + index;
        }
        return index % resultList.size();
    }

    public int getRouteLength() {
        int routeLength = 0;
        for (int i = 0; i < resultList.size(); i++) {
            Point point = resultList.get((i + 1) % resultList.size());
            Point lastPoint = resultList.get(i);
            routeLength += Utils.length(lastPoint, point);
        }
        return routeLength;
    }

    public void setRouteLength(int routeLength) {

    }
}
