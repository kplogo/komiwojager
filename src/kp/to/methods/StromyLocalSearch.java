package kp.to.methods;

import kp.to.model.RoundResult;

/**
 * Created by student on 2015-12-22.
 */
public class StromyLocalSearch extends LocalSearch {

    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        RoundResult newSolution;
        int max = Integer.MIN_VALUE;
        int minJ = -1;
        int minI = -1;
        for (int i = 0; i < solution.size(); i++) {
            for (int j = i; j < solution.size(); j++) {
                int difference = isNewSolutionCostBetter(solution, i, j);
                if (difference > 0 && max < difference) {
                    minI = i;
                    minJ = j;
                    max = difference;
                }

            }
        }
        if (max != Integer.MIN_VALUE) {
            newSolution = generateSolution(solution, minI, minJ);
            return newSolution;
        }
        return null;
    }
}
