package kp.to.methods.localsearch;

import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.RoundResult;

/**
 * Created by student on 2015-12-22.
 */
public class GreedyLocalSearch extends LocalSearch {

    public GreedyLocalSearch(LocalSearchType type) {
        super(type);
    }

    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        RoundResult newSolution;
        for (int i = 0; i < solution.size(); i++) {
            for (int j = i+2; j < solution.size(); j++) {
                if (isNewSolutionCostBetter(solution, i, j) > 0) {
                    newSolution = generateSolution(solution, i, j);
                    return newSolution;
                }
            }
        }
        return null;
    }
}
