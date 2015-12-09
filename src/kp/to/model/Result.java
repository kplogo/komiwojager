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
}
