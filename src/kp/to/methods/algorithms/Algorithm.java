package kp.to.methods.algorithms;

import kp.to.model.Point;
import kp.to.model.Result;

import java.util.List;

/**
 * Created by student on 2015-12-08.
 */
public interface Algorithm {
    Result run(List<Point> pointList);
}
