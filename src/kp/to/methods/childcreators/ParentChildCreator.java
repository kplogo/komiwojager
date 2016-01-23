package kp.to.methods.childcreators;

import kp.to.model.Point;
import kp.to.model.RoundResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParentChildCreator implements ChildCreator {
    @Override
    public List<List<Point>> createChildParts(RoundResult mother, RoundResult father) {
        int size = mother.size();
        List<List<Point>> lists = getChildren(size, mother.getSortedPointList(), father.getSortedPointList());
        List<List<Point>> lists2 = getChildren(size, mother.getSortedPointList(true), father.getSortedPointList(true));
        List<List<Point>> lists3 = getChildren(size, mother.getSortedPointList(), father.getSortedPointList(true));
        List<List<Point>> lists4 = getChildren(size, mother.getSortedPointList(true), father.getSortedPointList());
        List<List<Point>> result;
        if (lists.size() < lists2.size()) {
            result = lists;
        } else {
            result = lists2;
        }
        if (result.size() > lists3.size()) {
            result = lists3;

        }
        if (result.size() > lists4.size()) {
            result = lists4;
        }
        return result;
    }

    private List<List<Point>> getChildren(int size, List<Point> motherSortedPointList, List<Point> fatherSortedPointList) {
        List<List<Point>> lists = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Point point = motherSortedPointList.get(i);
            int otherIndex = fatherSortedPointList.indexOf(point);
            List<Point> part = new ArrayList<>();
            Point otherPoint = fatherSortedPointList.get(otherIndex % fatherSortedPointList.size());
            while (otherPoint == point) {
                part.add(motherSortedPointList.get(i));
                otherIndex++;
                i++;
                if (i >= size) {
                    break;
                }
                otherPoint = fatherSortedPointList.get(otherIndex % fatherSortedPointList.size());
                point = motherSortedPointList.get(i);
            }
            i--;
            lists.add(part);
        }
        return lists;
    }

    @Override
    public String toString() {
        return "ParentChildCreator";
    }
}
