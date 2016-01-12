package kp.to.methods.localsearch;

import kp.to.methods.localsearch.moves.CandidatesGenerator;
import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.RoundResult;

import java.util.List;

/**
 * Created by student on 2015-12-22.
 */
public class GreedyLocalSearch extends LocalSearch {

    private final CandidatesGenerator candidatesGenerator;

    public GreedyLocalSearch(LocalSearchType type,CandidatesGenerator candidatesGenerator) {
        super(type);
        this.candidatesGenerator = candidatesGenerator;
    }

    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        RoundResult newSolution;
        for (int i = 0; i < solution.size(); i++) {
            List<Integer> listToSearch = candidatesGenerator.createListToSearch(i, solution);
            for (Integer j : listToSearch) {
                if (isNewSolutionCostBetter(solution, i, j) > 0) {
                    newSolution = generateSolution(solution, i, j);
                    return newSolution;
                }

            }
        }
        return null;
    }
}
