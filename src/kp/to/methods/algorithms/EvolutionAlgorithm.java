package kp.to.methods.algorithms;

import kp.to.methods.childcreators.ChildCreator;
import kp.to.methods.constructors.SolutionConstructor;
import kp.to.methods.localsearch.LocalSearch;
import kp.to.methods.stopconditions.IterationStopCondition;
import kp.to.methods.stopconditions.StopCondition;
import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.Collections;
import java.util.List;

public class EvolutionAlgorithm implements Algorithm {
    public static final int ITEMS_IN_POPULATION = 20;
    protected StopCondition stopCondition;
    protected LocalSearch localSearch;
    protected SolutionConstructor solutionConstructor;
    private ChildCreator childCreator;

    public EvolutionAlgorithm(LocalSearch localSearch, SolutionConstructor solutionConstructor, StopCondition stopCondition, ChildCreator childCreator) {
        this.localSearch = localSearch;
        this.solutionConstructor = solutionConstructor;
        this.stopCondition = stopCondition;
        this.childCreator = childCreator;
    }

    @Override
    public Result run(List<Point> pointList) {
        Result population = new StandardAlgorithm(localSearch, solutionConstructor, new IterationStopCondition(ITEMS_IN_POPULATION)).run(pointList);
        int i = 0;
        stopCondition.startAlgorithm();
        long time = 0;
        long time1 = 0;

        while (!stopCondition.shouldStop(i)) {
            //Epoka ewolucyjna
            //losowanie rodziców
            int firstParent = (int) Math.round(Math.random() * (ITEMS_IN_POPULATION - 1));
            int secondParent = firstParent;
            while (secondParent == firstParent) {
                secondParent = (int) Math.round(Math.random() * (ITEMS_IN_POPULATION - 1));
            }
            long l = System.currentTimeMillis();
            RoundResult child = makeChild(population.get(firstParent), population.get(secondParent));
            time += System.currentTimeMillis() - l;
            long l1 = System.currentTimeMillis();
            RoundResult betterChild = localSearch.run(child);
            time1 += System.currentTimeMillis() - l1;
            addChildToPopulationIfBetter(population, betterChild);
            i++;
        }
        population.stop();
        population.setIterationCount(i);
        return population;
    }

    private void addChildToPopulationIfBetter(Result population, RoundResult child) {
        RoundResult worstResult = population.getWorstResult();
        if (child.getRouteLength() < worstResult.getRouteLength()) {
            if (!population.containsItemWithThisRouteLength(child)) {
                population.replace(worstResult, child);
            }
        }
    }

    private RoundResult makeChild(RoundResult mother, RoundResult father) {
        RoundResult roundResult = new RoundResult();
        List<List<Point>> parts = childCreator.createChildParts(mother, father);
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

    @Override
    public String toString() {
        if (localSearch == null) {
            return getClass().getSimpleName();
        } else {
            return getClass().getSimpleName() + " with "
                    + solutionConstructor
                    + ", " + stopCondition
                    + ", " + localSearch
                    + ", " + localSearch.getType()
                    + ", " + localSearch.getMovesGenerator()
                    + ", " + childCreator;
        }
    }
}
