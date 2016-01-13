package kp.to.methods.localsearch.moves;

import kp.to.methods.localsearch.Edge;
import kp.to.model.RoundResult;

import java.util.List;

/**
 * Created by student on 2016-01-12.
 */
public interface MovesGenerator {
       List<Edge> createListToSearch(int i, RoundResult solution);

}
