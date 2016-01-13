package kp.to.methods.localsearch.kiepskie;

import kp.to.methods.localsearch.LocalSearch;
import kp.to.methods.localsearch.moves.MovesGenerator;
import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by inf106580 on 2015-12-22.
 */
public class GreedyRandomLocalSearch2 extends LocalSearch {
    public GreedyRandomLocalSearch2(LocalSearchType type, MovesGenerator movesGenerator) {
        super(type, movesGenerator);
    }

    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < solution.size(); i++) {
            order.add(i);
        }
        Collections.shuffle(order);
        for (int i = 0; i < order.size(); i++) {
            for (int j = i + 1; j < order.size(); j++) {
                if (isNewSolutionCostBetter(solution, order.get(i), order.get(j)) > 0) {
                    return generateSolution(solution, order.get(i), order.get(j));
                }
            }

        }
        return null;
    }
}
