package kp.to;

import kp.to.model.Point;

/**
 * Created by inf106580 on 2015-12-08.
 */
public class Utils {

    public static int round(double value) {
        return (int) Math.floor((value) + 0.5);
    }

    public static int length(Point p1, Point p2) {
        return round(Math.sqrt((Math.pow((p1.getX()-p2.getX()),2) + Math.pow((p1.getY()-p2.getY()),2))));
    }

}
