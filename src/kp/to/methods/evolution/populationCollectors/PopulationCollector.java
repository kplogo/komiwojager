package kp.to.methods.evolution.populationCollectors;

import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;

public interface PopulationCollector {
    void addChildToPopulationIfBetter(Result population, RoundResult child);
}
