package kp.to.model;

public class Point {
    private int label;
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(String line) {
        String[] elements = line.split(" ");
        label = Integer.parseInt(elements[0]) - 1;
        x = Integer.parseInt(elements[1]);
        y = Integer.parseInt(elements[2]);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLabel() {
        return label;
    }
}
