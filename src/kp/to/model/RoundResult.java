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

    public int getRoutelength() {
        return routelength;
    }

    @Override
    public String toString() {
        int startIndex = -1;
        for (Point p : resultList) {
            if (p.getLabel() == 0) {
                startIndex = resultList.indexOf(p);
                break;
            }
        }

        StringBuilder builder = new StringBuilder();
        int i = startIndex;
        do {
            builder.append(resultList.get(i).getLabel()).append(' ');
            i = (i+1) % resultList.size();
        } while(i != startIndex);
        builder.append(routelength);
        return builder.toString();
    }
}
