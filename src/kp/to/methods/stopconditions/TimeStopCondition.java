package kp.to.methods.stopconditions;

/**
 * Created by Mikołaj Szychowiak on 18.01.2016.
 */
public class TimeStopCondition extends StopCondition{
    private long currentTime = 0;
    private long startTime = 0;

    public TimeStopCondition(long limit) {
        super(limit);
    }

    @Override
    public void startAlgorithm() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public boolean shouldStop(int iteration) {
        currentTime = System.currentTimeMillis() - startTime;
        return currentTime > limit;
    }

    @Override
    public String toString() {
        return "TimeStopCondition";
    }
}
