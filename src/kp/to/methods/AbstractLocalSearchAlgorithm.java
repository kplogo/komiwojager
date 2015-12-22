package kp.to.methods;

import kp.to.Utils;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;

/**
 * Created by inf106580 on 2015-12-22.
 */
public abstract class AbstractLocalSearchAlgorithm implements Algorithm {
    LocalSearch localSearch;

    public AbstractLocalSearchAlgorithm(LocalSearch localSearch) {
        this.localSearch = localSearch;
    }

    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        for (int i = 0; i < Grasp.MAX_ITERATIONS; i++) {
            RoundResult solution = constructSolution(pointList);
            solution = localSearch.run(solution);
            result.addResult(solution);
            System.out.println(solution.toString());
        }

        return result;
    }

    protected abstract RoundResult constructSolution(List<Point> pointList);
}
