package kp.to.methods.localsearch.moves;

import kp.to.model.RoundResult;

import java.util.List;

/**
 * Created by student on 2016-01-12.
 */
public interface CandidatesGenerator {
       List<Integer> createListToSearch(int i, RoundResult solution);

}
