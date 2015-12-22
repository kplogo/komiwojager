package kp.to.methods;

import kp.to.model.RoundResult;

/**
 * Created by student on 2015-12-22.
 */
public class GreedyRandomLocalSearch extends LocalSearch {

    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        int random = (int) Math.round(Math.random() * solution.size());
        for (int i = random; i < random + solution.size(); i++) {
            int value = i % solution.size();
            for (int j = value; j < solution.size(); j++) {
                if (isNewSolutionCostBetter(solution, value, j) > 0) {
                    return generateSolution(solution, value, j);
                }
            }

        }
        return null;
    }
}
