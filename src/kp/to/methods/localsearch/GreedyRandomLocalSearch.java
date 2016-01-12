package kp.to.methods.localsearch;

import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.RoundResult;

/**
 * Created by student on 2015-12-22.
 */
public class GreedyRandomLocalSearch extends LocalSearch {

    public GreedyRandomLocalSearch(LocalSearchType type) {
        super(type);
    }

    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        int random = (int) Math.round(Math.random() * solution.size());
        for (int i = random; i < random + solution.size(); i++) {
            int value = (i+1) % solution.size();
            for (int j = value+1; j < solution.size(); j++) {
                if (isNewSolutionCostBetter(solution, value, j) > 0) {
                    return generateSolution(solution, value, j);
                }
            }

        }
        return null;
    }
}
