package kp.to.methods;

import kp.to.Utils;
import kp.to.model.Point;
import kp.to.model.RoundResult;

/**
 * Created by student on 2015-12-22.
 */
public abstract class LocalSearch {
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


    protected abstract RoundResult getNeighbour(RoundResult solution);

    protected int isNewSolutionCostBetter(RoundResult solution, int a, int b) {
        int length1 = getLength(solution, a, b, false);
        int length2 = getLength(solution, a, b, true);
        return length1 - length2;

    }

    private int getLength(RoundResult solution, int a, int b, boolean inverted) {
        int a1 = (solution.size() + a - 1) % solution.size();
        int a3 = (a + 1) % solution.size();
        int b1 = (solution.size() + b - 1) % solution.size();
        int b3 = (b + 1) % solution.size();
        if (inverted) {
            if (a1 == b) {
                a1 = a;
            }
            if (a3 == b) {
                a3 = a;
            }
            if (b1 == a) {
                b1 = b;
            }
            if (b3 == a) {
                b3 = b;
            }

        }
        Point pointA1 = solution.get(a1);
        Point pointA2 = solution.get(a);
        Point pointA3 = solution.get(a3);
        Point pointB1 = solution.get(b1);
        Point pointB2 = solution.get(b);
        Point pointB3 = solution.get(b3);
        int length1;
        if (!inverted) {
            length1 = Utils.length(pointA1, pointA2);
            length1 += Utils.length(pointA2, pointA3);
            length1 += Utils.length(pointB1, pointB2);
            length1 += Utils.length(pointB2, pointB3);
        } else {
            length1 = Utils.length(pointA1, pointB2);
            length1 += Utils.length(pointB2, pointA3);
            length1 += Utils.length(pointB1, pointA2);
            length1 += Utils.length(pointA2, pointB3);
        }
        return length1;
    }

    protected RoundResult generateSolution(RoundResult solution, int a, int b) {
        RoundResult result = solution.copy();
        result.swap(a, b);
        return result;
    }
}
