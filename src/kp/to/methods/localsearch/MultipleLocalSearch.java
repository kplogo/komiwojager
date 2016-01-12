package kp.to.methods.localsearch;

import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.RoundResult;

/**
 * Created by student on 2016-01-12.
 */
public abstract class MultipleLocalSearch extends LocalSearch{

    private final LocalSearch localSearch;

    public MultipleLocalSearch(LocalSearchType type, LocalSearch localSearch) {
        super(type);
        this.localSearch = localSearch;
    }

    @Override
    public int getSearchCount() {
        return 100;
    }

    @Override
    public RoundResult run(RoundResult solution) {
        return localSearch.run(solution);
    }
}
