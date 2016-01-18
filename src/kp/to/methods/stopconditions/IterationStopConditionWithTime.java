package kp.to.methods.stopconditions;

/**
 * Created by Mikołaj Szychowiak on 18.01.2016.
 */
public class IterationStopConditionWithTime extends IterationStopCondition {
    private long currentTime = 0;
    private long startTime = 0;

    public IterationStopConditionWithTime(long limit) {
        super(limit);
    }

    @Override
    public int startAlgorithm() {
        startTime = System.currentTimeMillis();
        return super.startAlgorithm();
    }

    @Override
    public boolean shouldStop(int iteration) {
        currentTime = System.currentTimeMillis() - startTime;
        return super.shouldStop(iteration);
    }

    public long getTime() {
        return currentTime;
    }

}
