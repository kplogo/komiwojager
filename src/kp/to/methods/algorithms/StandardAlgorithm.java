package kp.to.methods.algorithms;

import kp.to.methods.constructors.SolutionConstructor;
import kp.to.methods.localsearch.LocalSearch;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;

/**
 * Created by inf106580 on 2015-12-22.
 */
public class StandardAlgorithm implements Algorithm {
    private static final int MAX_ITERATIONS = 150;
    protected LocalSearch localSearch;
    private SolutionConstructor solutionConstructor;

    public StandardAlgorithm(LocalSearch localSearch, SolutionConstructor solutionConstructor) {
        this.localSearch = localSearch;
        this.solutionConstructor = solutionConstructor;
    }

    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            RoundResult solution = solutionConstructor.constructSolution(pointList);
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
    @Override
    public String toString() {
        if (localSearch == null) {
            return super.toString();
        } else {
            return super.toString() + " with " + localSearch.toString() + " " + localSearch.getType().toString();
        }
    }
}
