package kp.to.methods.constructors;

import kp.to.model.Point;
import kp.to.model.RoundResult;

import java.util.List;

public interface SolutionConstructor {
    RoundResult constructSolution(List<Point> pointList);

}
