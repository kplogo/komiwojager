package kp.to.methods;

import kp.to.model.RoundResult;

/**
 * Created by Krzysztof.Pawlak on 2015-12-11.
 */
public class GraspGreedyRandom extends Grasp {
    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        RoundResult newSolution;
        int random = (int) Math.round(Math.random() * solution.size());
        for (int i = random; i < random + solution.size(); i++) {
            int value = i % solution.size();
            for (int j = value; j < solution.size(); j++) {
                if (isNewSolutionCostBetter(solution, value, j) > 0) {
                    newSolution = generateSolution(solution, value, j);
                    return newSolution;
                }
            }

        }
        return null;

    }
}
