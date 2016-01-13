package kp.to.methods.localsearch.moves;

import kp.to.Utils;
import kp.to.methods.localsearch.Edge;
import kp.to.model.Point;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandidateMovesGenerator implements MovesGenerator {

    public static final int CANDIDATES_NUMBER = 10;
    private static Map<Integer, List<Edge>> map = new HashMap<>();

    @Override
    public List<Edge> createListToSearch(int i, RoundResult solution) {
        List<Edge> candidates = map.get(i);
        if (candidates != null) {
            return candidates;
        }
        List<Edge> result = new ArrayList<>();
        Point point = solution.get(i);

        for (int j = 0; j < solution.getAll().size(); j++) {
            if (i == j) {
                continue;
            }
            Point point1 = solution.getAll().get(j);
            int length = Utils.length(point, point1);
            result.add(new Edge(i, j, length));
            result.add(new Edge(i - 1, j - 1, length));
        }

        result.sort((o1, o2) -> o1.getLength() - o2.getLength());
        List<Edge> list = result.subList(0, CANDIDATES_NUMBER);
        map.put(i, list);
        return list;
    }

    @Override
    public String toString() {
        return "CandidateMovesGenerator";
    }
}
