package kp.to.model;

/**
 * Wynik dla pojedyñczej iteracji (pojedynczy wierzcho³ek startowy)
 */
public class RoundResult {
    private int startPointIndex;
    private int routelength;

    public RoundResult(int startPoint) {
        this.startPointIndex = startPoint;
    }

    public void add(Point p) {
        //TODO: implement
    }

    public void setRouteLength(int length) {
        this.routelength = length;
    }
}
