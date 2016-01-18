package kp.to.methods.stopconditions;

/**
 * Created by Mikołaj Szychowiak on 18.01.2016.
 */
public class IterationStopCondition extends StopCondition {

    public IterationStopCondition(long limit) {
        super(limit);
    }

    @Override
    public boolean shouldStop(int currentValue) {
        return currentValue >= limit;
    }
}
