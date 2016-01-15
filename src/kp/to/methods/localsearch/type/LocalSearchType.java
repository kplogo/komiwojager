package kp.to.methods.localsearch.type;

import kp.to.model.RoundResult;

public interface LocalSearchType {
    int isNewSolutionCostBetter(RoundResult solution, int a, int b);

    RoundResult generateSolution(RoundResult solution, int a, int b);
}
