package kp.to.methods.localsearch.type;

import kp.to.model.RoundResult;

/**
 * Created by student on 2015-12-22.
 */
public interface LocalSearchType {
    int isNewSolutionCostBetter(RoundResult solution, int a, int b);

    RoundResult generateSolution(RoundResult solution, int a, int b);
}
