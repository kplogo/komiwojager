package kp.to.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Wynik dla pojedyñczej iteracji (pojedynczy wierzcho³ek startowy)
 */
public class RoundResult {
    private int startPointIndex;
    private int routelength;
    private final List<Point> resultList;

    public RoundResult(Point startPoint) {
        this.startPointIndex = startPoint.getLabel();
        resultList = new LinkedList<>();
        resultList.add(startPoint);
    }

    public void add(Point p) {
        resultList.add(p);
    }

    public void setRouteLength(int length) {
        this.routelength = length;
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
}
