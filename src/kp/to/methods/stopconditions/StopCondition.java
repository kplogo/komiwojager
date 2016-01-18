package kp.to.methods.stopconditions;

/**
 * Created by Mikołaj Szychowiak on 18.01.2016.
 */
public abstract class StopCondition {

    protected final long limit;

    public StopCondition(long limit) {
        this.limit = limit;
    }
    public int startAlgorithm() {
        return 0;
    }
    public abstract boolean shouldStop(int iteration);
}
