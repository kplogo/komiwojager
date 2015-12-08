package kp.to.methods;

import kp.to.model.Point;
import kp.to.model.Result;
import kp.to.model.RoundResult;

import java.util.List;

public class GreedyCycle implements Algorithm {
    @Override
    public Result run(List<Point> pointList) {
        Result result = new Result();
        for (int i = 0; i < pointList.size(); i++) {
            RoundResult roundResult = calculate(pointList,i);
            result.addResult(roundResult);
        }
        return null;
    }

    private RoundResult calculate(List<Point> pointList, int startPoint) {

        return null;
    }
}
