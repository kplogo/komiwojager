package kp.to.methods.localsearch;

import kp.to.methods.localsearch.moves.MovesGenerator;
import kp.to.methods.localsearch.type.LocalSearchType;
import kp.to.model.RoundResult;

public abstract class LocalSearch {
    private LocalSearchType type;
    private MovesGenerator movesGenerator;

    public LocalSearch(LocalSearchType type, MovesGenerator movesGenerator) {
        this.type = type;
        this.movesGenerator = movesGenerator;
    }

    public RoundResult run(RoundResult solution) {
        RoundResult newSolution;
        do {
            newSolution = getNeighbour(solution);
            if (newSolution != null) {
                solution = newSolution;
            }
        } while (newSolution != null);
        return solution;
    }

    public LocalSearchType getType() {
        return type;
    }

    protected abstract RoundResult getNeighbour(RoundResult solution);

    protected int isNewSolutionCostBetter(RoundResult solution, int a, int b) {
        return type.isNewSolutionCostBetter(solution, a, b);
    }

    protected RoundResult generateSolution(RoundResult solution, int a, int b) {
        return type.generateSolution(solution, a, b);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public MovesGenerator getMovesGenerator() {
        return movesGenerator;
    }
}
