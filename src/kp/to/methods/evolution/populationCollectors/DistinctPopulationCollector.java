package kp.to.methods.evolution.populationCollectors;

import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Dzia³a bardzo dobrze dla Parent generatora, ale Ÿle dla random
 */
public class DistinctPopulationCollector implements PopulationCollector {

    public void addChildToPopulationIfBetter(Result population, RoundResult child) {
        RoundResult worstResult = population.getWorstResult();
        if (child.getRouteLength() < worstResult.getRouteLength()) {
            if (!population.containsItemWithThisRouteLength(child)) {
                population.replace(worstResult, child);
            }
        }
    }
    @Override
    public String toString() {
        return "DistinctPopulationCollector";
    }
}
