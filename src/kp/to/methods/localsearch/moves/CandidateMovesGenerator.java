package kp.to.methods.localsearch.moves;

import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 2016-01-12.
 */
public class CandidateMovesGenerator implements CandidatesGenerator {
    @Override
    public List<Integer> createListToSearch(int i, RoundResult solution) {
        List<Integer> result = new ArrayList<>();
        for (int j = i + 1; j < solution.size(); j++) {
            result.add(j);
        }
        return result;
    }
}
