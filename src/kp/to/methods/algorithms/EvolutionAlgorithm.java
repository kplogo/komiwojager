package kp.to.methods.algorithms;

import kp.to.methods.evolution.childcreators.ChildCreator;
import kp.to.methods.constructors.SolutionConstructor;
import kp.to.methods.evolution.populationCollectors.PopulationCollector;
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
    private PopulationCollector populationCollector;

    public EvolutionAlgorithm(LocalSearch localSearch, SolutionConstructor solutionConstructor, StopCondition stopCondition, ChildCreator childCreator, PopulationCollector populationCollector) {
        this.localSearch = localSearch;
        this.solutionConstructor = solutionConstructor;
        this.stopCondition = stopCondition;
        this.childCreator = childCreator;
        this.populationCollector = populationCollector;
    }

    @Override
    public Result run(List<Point> pointList) {
        Result population = new StandardAlgorithm(localSearch, solutionConstructor, new IterationStopCondition(ITEMS_IN_POPULATION)).run(pointList);
        int i = 0;
        stopCondition.startAlgorithm();
        while (!stopCondition.shouldStop(i)) {
            //Epoka ewolucyjna
            //losowanie rodziców
            int firstParent = (int) Math.round(Math.random() * (ITEMS_IN_POPULATION - 1));
            int secondParent = firstParent;
            while (secondParent == firstParent) {
                secondParent = (int) Math.round(Math.random() * (ITEMS_IN_POPULATION - 1));
            }
            RoundResult child = makeChild(population.get(firstParent), population.get(secondParent));
            RoundResult betterChild = localSearch.run(child);
            populationCollector.addChildToPopulationIfBetter(population, betterChild);
            i++;
        }
        population.stop();
        population.setIterationCount(i);
        return population;
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
                    + ", " + populationCollector
                    + ", " + childCreator;
        }
    }
}
