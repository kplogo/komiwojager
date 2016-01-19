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
        Result result = new Result();
        //Odpaliæ 20 razy SA
        Result population = new StandardAlgorithm(new StromyLocalSearch(new EdgeSwap(), new CandidateMovesGenerator()), new Grasp(), new IterationStopCondition(ITEMS_IN_POPULATION)).run(pointList);
        int i;
        for (i = stopCondition.startAlgorithm(); !stopCondition.shouldStop(i); i++) {
            //Epoka ewolucyjna
            //losowanie rodziców
            int firstParent = (int) Math.round(Math.random() * ITEMS_IN_POPULATION);
            int secondParent = firstParent;
            while (secondParent == firstParent) {
                secondParent = (int) Math.round(Math.random() * ITEMS_IN_POPULATION);
            }
            RoundResult child = makeChild(population.get(firstParent), population.get(secondParent));
            addChildToPopulationIfBetter(population,child);
        }
        System.out.println(this + " " + i);
        return result;
    }

    private void addChildToPopulationIfBetter(Result population, RoundResult child) {
        RoundResult worstResult = population.getWorstResult();
        if (child.getRouteLength()<worstResult.getRouteLength()){
            population.replace(worstResult,child);
        }
    }

    private RoundResult makeChild(RoundResult mother, RoundResult father) {
        List<List<Integer>> parts = createChildParts(mother,father);
        return null;
    }

    private List<List<Integer>> createChildParts(RoundResult mother, RoundResult father) {
        return null;
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
