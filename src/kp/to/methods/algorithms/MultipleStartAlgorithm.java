package kp.to.methods.algorithms;

import kp.to.methods.constructors.SolutionConstructor;
import kp.to.methods.localsearch.LocalSearch;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;

/**
 * Created by inf106580 on 2016-01-12.
 */
public class MultipleStartAlgorithm extends StandardAlgorithm {
    public MultipleStartAlgorithm(LocalSearch localSearch, SolutionConstructor solutionConstructor) {
        super(localSearch, solutionConstructor);
    }

    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            RoundResult solution = null;
            if (localSearch != null) {
                Result iterationResult = new Result();
                for (int j = 0; j < localSearch.getSearchCount(); j++) {
                    solution = solutionConstructor.constructSolution(pointList);
                    solution = localSearch.run(solution);
                    iterationResult.addResult(solution);
                }
                solution = iterationResult.getBestResult();
            }
            addSolution(result, solution);
        }

        return result;
    }
}
