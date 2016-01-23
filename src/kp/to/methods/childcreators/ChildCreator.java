package kp.to.methods.childcreators;

import kp.to.model.Point;
import kp.to.model.RoundResult;

import java.util.List;

public interface ChildCreator {
    List<List<Point>> createChildParts(RoundResult mother, RoundResult father);
}
