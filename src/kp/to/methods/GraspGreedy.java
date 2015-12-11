package kp.to.methods;

import kp.to.model.RoundResult;

/**
 * Created by Krzysztof.Pawlak on 2015-12-11.
 */
public class GraspGreedy extends Grasp {
    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
            RoundResult newSolution;
            for (int i = 0; i < solution.size(); i++) {
                for (int j = i; j < solution.size(); j++) {
                    if (isNewSolutionCostBetter(solution, i, j)>0) {
                        newSolution = generateSolution(solution, i, j);
                        return newSolution;
                    }
                }

            }
            return null;

    }
}
