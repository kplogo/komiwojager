package kp.to.methods.algorithms;

import kp.to.methods.constructors.SolutionConstructor;
import kp.to.methods.localsearch.LocalSearch;
import kp.to.methods.stopconditions.StopCondition;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.LinkedList;
import java.util.List;

public class IterationSearchAlgorithm extends StandardAlgorithm {

    public static final int MAX_PERTURBATION_PART_SIZE = 30;
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
                int j = 0;
                stopCondition.startAlgorithm();
                while (!stopCondition.shouldStop(j)) {
                    it++;
                    RoundResult temporarySolution = randomPerturbation(solution, MAX_PERTURBATION_PART_SIZE);
                    temporarySolution = localSearch.run(temporarySolution);
                    iterationResult.addResult(temporarySolution);
                    solution = iterationResult.getBestResult();
                    j++;
                }
                solution.setTitle("Best Round " + i);
            }
            result.addResult(solution);
        }
        result.setIterationCount(it);
        return result;
    }

    private RoundResult randomPerturbation(RoundResult solution, int size) {
        List<Point> points = new LinkedList<>(solution.getAll());
        List<Point> result = new LinkedList<>();
        while (!points.isEmpty()) {
            int start = (int) Math.round(Math.random() * (points.size() - size));
            int end = Math.min(start + size / 2 + (int) Math.round(Math.random() * (size)), points.size());
            if (start > points.size() || start < 0) {
                start = 0;
                end = points.size();
            }
            List<Point> points1 = new LinkedList<>(points.subList(start, end));
            result.addAll(points1);
            points1.forEach(points::remove);
        }
        return new RoundResult(result);
    }
}
