package kp.to.methods.localsearch.type;

import kp.to.Utils;
import kp.to.model.Point;
import kp.to.model.RoundResult;

public class EdgeSwap implements LocalSearchType {
    @Override
    public int isNewSolutionCostBetter(RoundResult solution, int a, int b) {
        int length1 = getLength(solution, a, b, false);
        int length2 = getLength(solution, a, b, true);
        return length1 - length2;
    }

    private int getLength(RoundResult solution, int a, int b, boolean inverted) {
        Point point1 = solution.get((solution.size() + a) % solution.size());
        Point point2 = solution.get((solution.size() + a + 1) % solution.size());
        Point point3 = solution.get((solution.size() + b) % solution.size());
        Point point4 = solution.get((solution.size() + b + 1) % solution.size());
        if (inverted) {
            return Utils.length(point1, point3) + Utils.length(point2, point4);
        } else {
            return Utils.length(point1, point2) + Utils.length(point3, point4);
        }
    }

    @Override
    public RoundResult generateSolution(RoundResult solution, int a, int b) {
        RoundResult result = solution.copy();
        result.swapRange(a + 1, b,solution.size());
        return result;
    }

    @Override
    public String toString() {
        return "EdgeSwap";
    }
}
