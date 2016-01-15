package kp.to.model;

import kp.to.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Wynik dla pojedyñczej iteracji (pojedynczy wierzcho³ek startowy)
 */
public class RoundResult {
    public static final String SEPARATOR = ";";
    private List<Point> resultList;
    private int routeLength = -1;
    private String title;


    public RoundResult() {
        resultList = new LinkedList<>();
    }

    public RoundResult(Point startPoint) {
        resultList = new LinkedList<>();
        resultList.add(startPoint);
        routeLength = -1;
    }

    public RoundResult(List<Point> resultList) {
        this.resultList = new LinkedList<>();
        this.resultList = resultList;
        routeLength = -1;
    }

    public void add(Point p) {
        resultList.add(p);
        routeLength = -1;
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

    public List<Point> getAll() {
        return resultList;
    }

    public void add(int position, Point point) {
        resultList.add(position, point);
        routeLength = -1;
    }

    @Override
    public String toString() {
        return print(false);
    }

    public String print(boolean detailed) {

        int startIndex = -1;
        for (Point p : resultList) {
            if (p.getLabel() == 0) {
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
        builder.append(title).append("\n");
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
        if (routeLength == -1) {
            routeLength = 0;
            for (int i = 0; i < resultList.size(); i++) {
                Point point = resultList.get((i + 1) % resultList.size());
                Point lastPoint = resultList.get(i);
                routeLength += Utils.length(lastPoint, point);
            }
        }
        return routeLength;
    }

    public void addAll(List<Point> bestSolution) {
        resultList.addAll(bestSolution);
        routeLength = -1;
    }

    public RoundResult copy() {
        return new RoundResult(new LinkedList<>(resultList));
    }

    public void swap(int a, int b) {
        Point pointA = resultList.get(a);
        Point pointB = resultList.get(b);
        resultList.set(a, pointB);
        resultList.set(b, pointA);
        routeLength = -1;
    }

    public void swapRange(int a, int b, int size) {
        if (a > b) {
            b = b + size;
        }
        for (int i = 0; i <= (b - a) / 2; i++) {
            Point pointA = resultList.get((a + i) % size);
            Point pointB = resultList.get((b - i) % size);
            resultList.set((a + i) % size, pointB);
            resultList.set((b - i) % size, pointA);
        }
        routeLength = -1;
    }

    public void performPerturbation(int break1, int break2, int break3) {
        List<Point> currentResult = resultList;
        resultList = new LinkedList<>();
        resultList.addAll(currentResult.subList(0, break1));
        resultList.addAll((currentResult.subList(break3, currentResult.size())));
        resultList.addAll((currentResult.subList(break2, break3)));
        resultList.addAll((currentResult.subList(break1, break2)));
        routeLength = -1;
    }

    public RoundResult setTitle(String title) {
        this.title = title;
        return this;
    }

    public String printResult() {
        return title + ": " + getRouteLength();
    }
}
