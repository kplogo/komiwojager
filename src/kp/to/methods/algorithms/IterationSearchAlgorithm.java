package kp.to.methods.algorithms;

import kp.to.methods.constructors.SolutionConstructor;
import kp.to.methods.localsearch.LocalSearch;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;
import java.util.Random;

public class IterationSearchAlgorithm extends StandardAlgorithm {

    public static final int MAX_REPEATS = 10;
    public static final Random random = new Random(System.currentTimeMillis());

    public IterationSearchAlgorithm(LocalSearch localSearch, SolutionConstructor solutionConstructor) {
        super(localSearch, solutionConstructor);
    }

    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        for (int i = 0; i < MAX_REPEATS; i++) {
            RoundResult solution = solutionConstructor.constructSolution(pointList);
            if (localSearch != null) {
                Result iterationResult = new Result();
                for (int j = 0; j < MAX_ITERATIONS; j++) {
                    RoundResult temporarySolution = doubleBridgeMove(solution);
                    temporarySolution = localSearch.run(temporarySolution);
                    iterationResult.addResult(temporarySolution);
                    solution = iterationResult.getBestResult();
                }
            }
            result.addResult(solution);
        }

        return result;
    }

    private RoundResult doubleBridgeMove(RoundResult solution) {
       ///TU BYL BLAD, bo nie bylo tej linijki:P
        solution = solution.copy();
        int size = solution.size();
        int p1 = random.nextInt(size / 4);
        int p2 = p1 + 1 + random.nextInt(size / 4);
        int p3 = p2 + 1 + random.nextInt(size / 4);
        solution.performPerturbation(p1, p2, p3);
        return solution;
    }
}
