package kp.to.methods.localsearch.type;

import kp.to.Utils;
import kp.to.model.Point;
import kp.to.model.RoundResult;

/**
 * Created by student on 2015-12-22.
 */
public class EdgeSwap implements LocalSearchType {
    @Override
    public int isNewSolutionCostBetter(RoundResult solution, int a, int b) {
        int length1 = getLength(solution, a, b, false);
        int length2 = getLength(solution, a, b, true);
        return length1 - length2;

    }

    private int getLength(RoundResult solution, int a, int b, boolean inverted) {
        Point point2 = solution.get((a + 1) % solution.size());
        Point point1 = solution.get(a);
        Point point3 = solution.get(b);
        Point point4 = solution.get((b + 1) % solution.size());
        if (point1 == point3) {
            return 0;
        }
        if (inverted) {
            return Utils.length(point1, point3) + Utils.length(point2, point4);
        } else {
            return Utils.length(point1, point2) + Utils.length(point3, point4);
        }
    }

    @Override
    public RoundResult generateSolution(RoundResult solution, int a, int b) {
        RoundResult result = solution.copy();
        result.swapRange(a+1, b);
        return result;
    }
}
