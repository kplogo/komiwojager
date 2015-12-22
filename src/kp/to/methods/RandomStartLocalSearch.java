package kp.to.methods;

import kp.to.model.Point;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by inf106580 on 2015-12-22.
 */
public class RandomStartLocalSearch extends AbstractLocalSearchAlgorithm {

    public RandomStartLocalSearch(LocalSearch localSearch) {
        super(localSearch);
    }

    @Override
    protected RoundResult constructSolution(List<Point> pointList) {
        List<Point> randomizedSolutionList = new ArrayList<>(pointList);
        Collections.shuffle(randomizedSolutionList);
        RoundResult r = new RoundResult();
        for (Point p : randomizedSolutionList) {
            r.add(p);
        }
        return r;
    }
}
