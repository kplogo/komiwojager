package kp.to.methods;

import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;

/**
 * Created by inf106580 on 2015-12-22.
 */
public abstract class AbstractLocalSearchAlgorithm implements Algorithm {
    private static final int MAX_ITERATIONS = 150;
    LocalSearch localSearch;

    public AbstractLocalSearchAlgorithm(LocalSearch localSearch) {
        this.localSearch = localSearch;
    }

    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            RoundResult solution = constructSolution(pointList);
            if (localSearch != null) {
                Result iterationResult = new Result();
                for (int j = 0; j < localSearch.getSearchCount(); j++) {
                    solution = localSearch.run(solution);
                    iterationResult.addResult(solution);
                }
                solution = iterationResult.getBestResult();
            }
            addSolution(result, solution);
        }

        return result;
    }

    private void addSolution(Result result, RoundResult solution) {
        System.out.println(solution.toString());
    }

    protected abstract RoundResult constructSolution(List<Point> pointList);
}
