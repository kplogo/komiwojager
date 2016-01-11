package kp.to.methods;

import kp.to.Utils;
import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.Point;
import kp.to.model.RoundResult;

/**
 * Created by student on 2015-12-22.
 */
public abstract class LocalSearch {
    LocalSearchType type;

    public LocalSearch(LocalSearchType type) {
        this.type = type;
    }

    public RoundResult run(RoundResult solution) {
        RoundResult newSolution;
        do {
            newSolution = getNeighbour(solution);
            if (newSolution != null) {
                solution = newSolution;
            }
        } while (newSolution != null);
        return solution;
    }


    protected abstract RoundResult getNeighbour(RoundResult solution);

    protected int isNewSolutionCostBetter(RoundResult solution, int a, int b) {
        return type.isNewSolutionCostBetter(solution, a, b);
    }

    protected RoundResult generateSolution(RoundResult solution, int a, int b) {
        return type.generateSolution(solution, a, b);
    }
}
