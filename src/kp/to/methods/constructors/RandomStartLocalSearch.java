package kp.to.methods.constructors;

import kp.to.model.Point;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomStartLocalSearch implements SolutionConstructor {

    @Override
    public RoundResult constructSolution(List<Point> pointList) {
        List<Point> randomizedSolutionList = new ArrayList<>(pointList);
        Collections.shuffle(randomizedSolutionList);
        RoundResult r = new RoundResult();
        for (Point p : randomizedSolutionList) {
            r.add(p);
        }
        return r;
    }
}
