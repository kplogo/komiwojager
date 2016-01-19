package kp.to.methods.algorithms;

import kp.to.methods.constructors.SolutionConstructor;
import kp.to.methods.localsearch.LocalSearch;
import kp.to.methods.stopconditions.StopCondition;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;

public class MultipleStartAlgorithm extends StandardAlgorithm {

    private final int repeatsNumber;

    public MultipleStartAlgorithm(LocalSearch localSearch, SolutionConstructor solutionConstructor, StopCondition stopCondition, int repeatsNumber) {
        super(localSearch, solutionConstructor, stopCondition);
        this.repeatsNumber = repeatsNumber;
    }

    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        int it = 0;
        for (int i = 0;i < repeatsNumber ; i++) {
            RoundResult solution = null;
            if (localSearch != null) {
                Result iterationResult = new Result();
                for (int j = stopCondition.startAlgorithm(); !stopCondition.shouldStop(j); j++) {
                    it++;
                    solution = solutionConstructor.constructSolution(pointList);
                    solution = localSearch.run(solution);
                    iterationResult.addResult(solution);
                }
                solution = iterationResult.getBestResult();
            }
            result.addResult(solution);
        }
        result.setIterationCount(it);
        return result;
    }
}
