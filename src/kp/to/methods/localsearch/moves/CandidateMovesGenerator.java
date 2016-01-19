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

        List<Edge> result = new ArrayList<>();
        Point point = solution.get(i);
        List<Edge> nearestPoints = getNearestPoints(i, solution, point);
        for (Edge nearestPoint : nearestPoints) {
            result.add(new Edge(i, nearestPoint.getN2()));
            result.add(new Edge(i - 1, nearestPoint.getN2() - 1));
        }

        return result;
    }

    private List<Edge> getNearestPoints(int i, RoundResult solution, Point point) {
        List<Edge> nearestPoints = map.get(i);
        if (nearestPoints != null) {
            return nearestPoints;
        }
        nearestPoints = new ArrayList<>();
        for (int j = 0; j < solution.size(); j++) {
            if (i == j) {
                continue;
            }
            Point point1 = solution.get(j);
            int length = Utils.length(point, point1);
            nearestPoints.add(new Edge(length, j));
        }
        nearestPoints.sort((o1, o2) -> o1.getN1() - o2.getN1());
        List<Edge> list = nearestPoints.subList(0, CANDIDATES_NUMBER);
        map.put(i, list);

        return list;
    }

    @Override
    public String toString() {
        return "CandidateMovesGenerator";
    }
}
