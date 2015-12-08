package kp.to.model;

import java.util.ArrayList;
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
}
