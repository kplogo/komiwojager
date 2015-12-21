package kp.to.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Wynik dla 100 iteracji (ka�dy wierzcho�ek jako startowy)
 */
public class Result {
    private List<RoundResult> resultList = new ArrayList<>();

    public List<RoundResult> getResultList() {
        return resultList;
    }

    public void addResult(RoundResult roundResult) {
        resultList.add(roundResult);
    }

    @Override
    public String toString() {
        List<RoundResult> results = new ArrayList<>(resultList);
        Collections.sort(results, new Comparator<RoundResult>() {
            @Override
            public int compare(RoundResult o1, RoundResult o2) {
                return o1.getRouteLength() - o2.getRouteLength();
            }
        });
        StringBuilder builder = new StringBuilder();
        for (RoundResult r : results) {
            builder.append(r.toString()).append('\n');
        }
        return builder.toString();
    }

    public RoundResult[] getResultsToReport() {
        List<RoundResult> results = new ArrayList<>(resultList);
        Collections.sort(results, new Comparator<RoundResult>() {
            @Override
            public int compare(RoundResult o1, RoundResult o2) {
                return o1.getRouteLength() - o2.getRouteLength();
            }
        });
        RoundResult[] rounds = new RoundResult[3];
        rounds[0] = results.get(0);
        rounds[1] = results.get(results.size()/2);
        rounds[2] = results.get(results.size()-1);
        return rounds;
    }

    public RoundResult getBestResult() {
        return resultList.stream().max((i1, i2) -> i2.getRouteLength() - i1.getRouteLength()).get();
    }
}
