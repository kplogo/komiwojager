package kp.to.methods.localsearch;

/**
 * Created by student on 2016-01-12.
 */
public class Edge {
    private int n1;
    private int n2;
    private int length;

    public Edge(int n1, int n2, int length) {
        this.n1 = n1;
        this.n2 = n2;
        this.length = length;
    }

    public int getN1() {
        return n1;
    }

    public int getN2() {
        return n2;
    }
}
