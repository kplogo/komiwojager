package kp.to.model;

/**
 * Wynik dla pojedy�czej iteracji (pojedynczy wierzcho�ek startowy)
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
