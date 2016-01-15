package kp.to.methods.algorithms;

import kp.to.model.Point;
import kp.to.model.Result;

import java.util.List;

public interface Algorithm {
    Result run(List<Point> pointList);
}
