package kp.to.methods.localsearch;

import kp.to.methods.localsearch.moves.MovesGenerator;
import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.RoundResult;

import java.util.List;

/**
 * Created by student on 2015-12-22.
 */
public class GreedyLocalSearch extends LocalSearch {


    public GreedyLocalSearch(LocalSearchType type, MovesGenerator movesGenerator) {
        super(type,movesGenerator);
    }

    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        RoundResult newSolution;
        for (int i = 0; i < solution.size(); i++) {
            List<Edge> listToSearch = getMovesGenerator().createListToSearch(i, solution);
            for (Edge edge : listToSearch) {
                if (isNewSolutionCostBetter(solution, edge.getN1(), edge.getN2()) > 0) {
                    newSolution = generateSolution(solution, edge.getN1(), edge.getN2());
                    return newSolution;
                }
            }
        }
        return null;
    }
}
