package kp.to.methods.localsearch;

import kp.to.Main;
import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.Point;
import kp.to.model.RoundResult;

import java.util.List;

/**
 * Created by student on 2016-01-12.
 */
public class IterationLocalSearch extends LocalSearch{

    private final LocalSearch localSearch;

    public IterationLocalSearch(LocalSearchType type, LocalSearch localSearch) {
        super(type);
        this.localSearch = localSearch;
    }

    @Override
    public int getSearchCount() {
        return 100;
    }

    @Override
    public RoundResult run(RoundResult solution) {
        solution = doubleBridgeMove(solution);
        return super.run(solution);
    }

    @Override
    protected RoundResult getNeighbour(RoundResult solution) {
        return localSearch.getNeighbour(solution);
    }

    private RoundResult doubleBridgeMove(RoundResult solution) {
        int size = solution.size();
        int p1 = Main.random.nextInt(size/4);
        int p2 = p1 + 1 + Main.random.nextInt(size/4);
        int p3 = p2 + 1 + Main.random.nextInt(size/4);
        solution.performPerturbation(p1, p2, p3);
        return solution;
    }
}
