package kp.to.methods.localsearch.kiepskie;

import kp.to.methods.localsearch.LocalSearch;
import kp.to.methods.localsearch.moves.MovesGenerator;
import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.RoundResult;

public class GreedyRandomLocalSearch extends LocalSearch {

    public GreedyRandomLocalSearch(LocalSearchType type,MovesGenerator movesGenerator) {
        super(type, movesGenerator);
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
