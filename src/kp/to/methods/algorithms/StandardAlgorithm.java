package kp.to.methods.algorithms;

import kp.to.methods.constructors.SolutionConstructor;
import kp.to.methods.localsearch.LocalSearch;
import kp.to.methods.stopconditions.StopCondition;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;

public class StandardAlgorithm implements Algorithm {
    protected StopCondition stopCondition;
    protected LocalSearch localSearch;
    protected SolutionConstructor solutionConstructor;

    public StandardAlgorithm(LocalSearch localSearch, SolutionConstructor solutionConstructor, StopCondition stopCondition) {
        this.localSearch = localSearch;
        this.solutionConstructor = solutionConstructor;
        this.stopCondition = stopCondition;
    }

    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        int i = 0;
        stopCondition.startAlgorithm();
        while (!stopCondition.shouldStop(i)) {
            RoundResult solution = solutionConstructor.constructSolution(pointList);
            if (localSearch != null) {
                solution = localSearch.run(solution);
            }
            result.addResult(solution);
            i++;
        }
        result.setIterationCount(i);
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
