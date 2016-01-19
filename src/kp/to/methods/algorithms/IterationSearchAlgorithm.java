package kp.to.methods.algorithms;

import kp.to.methods.constructors.SolutionConstructor;
import kp.to.methods.localsearch.LocalSearch;
import kp.to.methods.stopconditions.StopCondition;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;
import java.util.Random;

public class IterationSearchAlgorithm extends StandardAlgorithm {

    public static final Random random = new Random(System.currentTimeMillis());
    private final int repeatsNumber;

    public IterationSearchAlgorithm(LocalSearch localSearch, SolutionConstructor solutionConstructor, StopCondition stopCondition, int repeatsNumber) {
        super(localSearch, solutionConstructor, stopCondition);
        this.repeatsNumber = repeatsNumber;
    }

    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        int it = 0;
        for (int i = 0; i < repeatsNumber; i++) {
            RoundResult solution = solutionConstructor.constructSolution(pointList);
            if (localSearch != null) {
                Result iterationResult = new Result();
                for (int j = stopCondition.startAlgorithm(); !stopCondition.shouldStop(j); j++) {
                    it++;
                    RoundResult temporarySolution = doubleBridgeMove(solution);
                    temporarySolution = localSearch.run(temporarySolution);
                    iterationResult.addResult(temporarySolution);
                    solution = iterationResult.getBestResult();
                }
            }
            result.addResult(solution);
        }
        result.setIterationCount(it);
        return result;
    }

    private RoundResult doubleBridgeMove(RoundResult solution) {
        solution = solution.copy();
        int size = solution.size();
        int p1 = random.nextInt(size / 4);
        int p2 = p1 + 1 + random.nextInt(size / 4);
        int p3 = p2 + 1 + random.nextInt(size / 4);
        solution.performPerturbation(p1, p2, p3);
        return solution;
    }
}
