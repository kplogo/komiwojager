package kp.to.methods.evolution.populationCollectors;

import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Dzia³a bardzo dobrze dla Random generatora, ale Ÿle dla Parent
 */
public class DefaultPopulationCollector implements PopulationCollector {
    @Override

    public void addChildToPopulationIfBetter(Result population, RoundResult child) {
        RoundResult worstResult = population.getWorstResult();
        if (child.getRouteLength() < worstResult.getRouteLength()) {
                population.replace(worstResult, child);
        }
    }

    @Override
    public String toString() {
        return "DefaultPopulationCollector";
    }
}
