package kp.to.methods.localsearch;

import kp.to.Utils;
import kp.to.methods.localsearch.moves.CandidatesGenerator;
import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.Point;
import kp.to.model.RoundResult;

import javax.rmi.CORBA.Util;
import java.util.List;

/**
 * Created by student on 2015-12-22.
 */
public class GreedyCandidatesLocalSearch extends LocalSearch {

    private LocalSearchType type;
    private final CandidatesGenerator candidatesGenerator;

    public GreedyCandidatesLocalSearch(LocalSearchType type, CandidatesGenerator candidatesGenerator) {
        super(type);
        this.type = type;
        this.candidatesGenerator = candidatesGenerator;
    }

    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        RoundResult newSolution;
        for (int i = 0; i < solution.size(); i++) {
            List<Edge> listToSearch = createListToSearch(i, solution);
            for (Edge j : listToSearch) {
                if (type.isNewSolutionCostBetter(solution, j.getN1(), j.getN2()) > 0) {
                    newSolution = generateSolution(solution, j.getN1(), j.getN2());
                    return newSolution;
                }
                if (type.isNewSolutionCostBetter(solution, j.getN1()-1, j.getN2()-1) > 0) {
                    newSolution = generateSolution(solution, j.getN1()-1, j.getN2()-1);
                    return newSolution;
                }

            }
        }
        return null;
    }

    private List<Edge> createListToSearch(int i, RoundResult solution) {
        Point point = solution.get(i);
        for (Point point1 : solution.getAll()) {
            int length = Utils.length(point, point1);

        }
        return null;
    }
}
