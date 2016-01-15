package kp.to.methods.algorithms;

import kp.to.methods.constructors.SolutionConstructor;
import kp.to.methods.localsearch.LocalSearch;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;

public class StandardAlgorithm implements Algorithm {
    protected static final int MAX_ITERATIONS = 150;
    protected LocalSearch localSearch;
    protected SolutionConstructor solutionConstructor;

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
                solution = localSearch.run(solution);
            }
            result.addResult(solution);
        }

        return result;
    }

    @Override
    public String toString() {
        if (localSearch == null) {
            return getClass().getSimpleName();
        } else {
            return getClass().getSimpleName() + " with " + localSearch.toString() + ", " + localSearch.getType().toString() + ", " + localSearch.getMovesGenerator().toString();
        }
    }
}
