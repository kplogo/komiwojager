package kp.to.methods.localsearch;

import kp.to.methods.localsearch.moves.MovesGenerator;
import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.RoundResult;

import java.util.List;

/**
 * Created by student on 2015-12-22.
 */
public class StromyLocalSearch extends LocalSearch {

    public StromyLocalSearch(LocalSearchType type, MovesGenerator generator) {
        super(type, generator);
    }

    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        RoundResult newSolution;
        int max = Integer.MIN_VALUE;
        int minJ = -1;
        int minI = -1;
        for (int i = 0; i < solution.size(); i++) {
            List<Edge> listToSearch = getMovesGenerator().createListToSearch(i, solution);
            for (Edge edge : listToSearch) {
                int difference = isNewSolutionCostBetter(solution, edge.getN1(), edge.getN2());
                if (difference > 0 && max < difference) {
                    minI = edge.getN1();
                    minJ = edge.getN2();
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
