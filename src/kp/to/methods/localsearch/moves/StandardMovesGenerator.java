package kp.to.methods.localsearch.moves;

import kp.to.methods.localsearch.Edge;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardMovesGenerator implements MovesGenerator {
    private static Map<Integer, List<Edge>> map = new HashMap<>();

    @Override
    public List<Edge> createListToSearch(int i, RoundResult solution) {
        List<Edge> candidates = map.get(i);
        if (candidates != null) {
            return candidates;
        }
        List<Edge> result = new ArrayList<>();
        for (int j = i + 1; j < solution.size(); j++) {
            result.add(new Edge(i, j));
        }
        map.put(i, result);
        return result;
    }

    @Override
    public String toString() {
        return "StandardMovesGenerator";
    }
}
