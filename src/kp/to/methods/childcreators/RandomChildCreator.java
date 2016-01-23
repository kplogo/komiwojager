package kp.to.methods.childcreators;

import kp.to.model.Point;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.List;

public class RandomChildCreator implements ChildCreator {
    @Override
    public List<List<Point>> createChildParts(RoundResult mother, RoundResult father) {

        ArrayList<List<Point>> lists = new ArrayList<>();
        List<Point> list = new ArrayList<>();
        for (Point point : mother.getAll()) {
            if (Math.random() < 0.05) {
                if (!list.isEmpty()) {
                    lists.add(list);
                }
                list = new ArrayList<>();
            }
            list.add(point);
        }
        lists.add(list);
        return lists;
    }

    @Override
    public String toString() {
        return "RandomChildCreator";
    }
}
