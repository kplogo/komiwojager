package kp.to.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Wynik dla 100 iteracji (ka¿dy wierzcho³ek jako startowy)
 */
public class Result {
    private List<RoundResult> resultList = new ArrayList<>();
    private long start;
    private long stop = 0;

    public Result() {
        start = System.currentTimeMillis();
    }

    public void stop() {
        stop = System.currentTimeMillis();
    }

    public List<RoundResult> getResultList() {
        return resultList;
    }

    public void addResult(RoundResult roundResult) {
        resultList.add(roundResult);
    }

    @Override
    public String toString() {
        List<RoundResult> results = new ArrayList<>(resultList);
        Collections.sort(results, (o1, o2) -> o1.getRouteLength() - o2.getRouteLength());
        StringBuilder builder = new StringBuilder();
        for (RoundResult r : results) {
            builder.append(r.toString()).append('\n');
        }
        return builder.toString();
    }

    public RoundResult[] getResultsToReport() {
        List<RoundResult> results = new ArrayList<>(resultList);
        Collections.sort(results, (o1, o2) -> o1.getRouteLength() - o2.getRouteLength());
        RoundResult[] rounds = new RoundResult[3];
        rounds[0] = results.get(0).setTitle("Maksimum:");
        rounds[1] = results.get(results.size() / 2).setTitle("Srednia:");
        rounds[2] = results.get(results.size() - 1).setTitle("Minimum:");
        return rounds;
    }

    public RoundResult getBestResult() {
        return resultList.stream().max((i1, i2) -> i2.getRouteLength() - i1.getRouteLength()).get();
    }
    public RoundResult getWorstResult() {
        return resultList.stream().min((i1, i2) -> i2.getRouteLength() - i1.getRouteLength()).get();
    }

    public long getTime() {
        return stop - start;
    }

    public RoundResult get(int i) {
        return resultList.get(i);
    }

    public void replace(RoundResult toReplace, RoundResult newItem) {
        resultList.remove(toReplace);
        resultList.add(newItem);
    }
}
