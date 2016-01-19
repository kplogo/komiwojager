package kp.to.methods.algorithms;

import kp.to.methods.constructors.Grasp;
import kp.to.methods.constructors.SolutionConstructor;
import kp.to.methods.localsearch.LocalSearch;
import kp.to.methods.localsearch.StromyLocalSearch;
import kp.to.methods.localsearch.moves.CandidateMovesGenerator;
import kp.to.methods.localsearch.type.EdgeSwap;
import kp.to.methods.stopconditions.IterationStopCondition;
import kp.to.methods.stopconditions.StopCondition;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EvolutionAlgorithm implements Algorithm {
    public static final int ITEMS_IN_POPULATION = 20;
    protected StopCondition stopCondition;
    protected LocalSearch localSearch;
    protected SolutionConstructor solutionConstructor;

    public EvolutionAlgorithm(LocalSearch localSearch, SolutionConstructor solutionConstructor, StopCondition stopCondition) {
        this.localSearch = localSearch;
        this.solutionConstructor = solutionConstructor;
        this.stopCondition = stopCondition;
    }

    @Override
    public Result run(List<Point> pointList) {
        Result population = new StandardAlgorithm(localSearch, solutionConstructor, new IterationStopCondition(ITEMS_IN_POPULATION)).run(pointList);
        int i;
        for (i = stopCondition.startAlgorithm(); !stopCondition.shouldStop(i); i++) {
            //Epoka ewolucyjna
            //losowanie rodziców
            int firstParent = (int) Math.round(Math.random() * (ITEMS_IN_POPULATION - 1));
            int secondParent = firstParent;
            while (secondParent == firstParent) {
                secondParent = (int) Math.round(Math.random() * (ITEMS_IN_POPULATION - 1));
            }
            RoundResult child = makeChild(population.get(firstParent), population.get(secondParent));
            RoundResult betterChild = localSearch.run(child);
            addChildToPopulationIfBetter(population, betterChild);
        }
        population.setIterationCount(i);
        return population;
    }

    private void addChildToPopulationIfBetter(Result population, RoundResult child) {
        RoundResult worstResult = population.getWorstResult();
        if (child.getRouteLength() < worstResult.getRouteLength()) {
            population.replace(worstResult, child);
        }
    }

    private RoundResult makeChild(RoundResult mother, RoundResult father) {
        RoundResult roundResult = new RoundResult();
        List<List<Point>> parts = createChildParts(mother, father);
        while (!parts.isEmpty()) {
            int partNo = (int) Math.round(Math.random() * (parts.size() - 1));
            int direction = (Math.random() < 0.5) ? -1 : 1;
            List<Point> actualPart = parts.get(partNo);
            if (direction == -1) {
                Collections.reverse(actualPart);
            }
            roundResult.addAll(actualPart);
            parts.remove(actualPart);
        }
        return roundResult;
    }

    private List<List<Point>> createChildParts2(RoundResult mother, RoundResult father) {

        ArrayList<List<Point>> lists = new ArrayList<>();
        List<Point> list = new ArrayList<>();
        for (Point point : mother.getAll()) {
            if (Math.random() < 0.05) {
                if (!list.isEmpty()) {
                    lists.add(list);
                }
                list = new ArrayList<>();
            }
            list.add(point);
        }
        lists.add(list);
        return lists;
    }

    private List<List<Point>> createChildParts(RoundResult mother, RoundResult father) {
        return mother.commonEdges(father);
    }

    @Override
    public String toString() {
        if (localSearch == null) {
            return getClass().getSimpleName();
        } else {
            return getClass().getSimpleName() + " with " + localSearch.toString() + ", " + localSearch.getType().toString() + ", " + localSearch.getMovesGenerator().toString();
        }
    }
}
