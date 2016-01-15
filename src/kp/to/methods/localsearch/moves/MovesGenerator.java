package kp.to.methods.localsearch.moves;

import kp.to.methods.localsearch.Edge;
import kp.to.model.RoundResult;

import java.util.List;

public interface MovesGenerator {
       List<Edge> createListToSearch(int i, RoundResult solution);

}
