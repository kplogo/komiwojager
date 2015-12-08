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
}
