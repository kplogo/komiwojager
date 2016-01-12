package kp.to.methods.localsearch;

import kp.to.methods.localsearch.moves.CandidatesGenerator;
import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 2015-12-22.
 */
public class StromyLocalSearch extends LocalSearch {

    private final CandidatesGenerator candidatesGenerator;

    public StromyLocalSearch(LocalSearchType type, CandidatesGenerator candidatesGenerator) {
        super(type);
        this.candidatesGenerator = candidatesGenerator;
    }

    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        RoundResult newSolution;
        int max = Integer.MIN_VALUE;
        int minJ = -1;
        int minI = -1;
        for (int i = 0; i < solution.size(); i++) {
            List<Integer> listToSearch = candidatesGenerator.createListToSearch(i, solution);
            for (Integer j : listToSearch) {
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
