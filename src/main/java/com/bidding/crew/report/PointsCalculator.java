package com.bidding.crew.report;

import java.util.Comparator;
import java.util.List;

public class PointsCalculator {

    public void interpretStarsAsPoints(Report report) {
        List<Request> requests = requestSortedByStars(report);
        calculateStarsForPoints(requests);
    }

    private List<Request> requestSortedByStars(Report report) {
        return report.getRequests().stream()
                .sorted(Comparator.comparing(Request::getStars)
                        .reversed()).toList();
    }

    private void calculateStarsForPoints(List<Request> finalRequests) {
        int maxPoints = 200;
        for (Request finalRequest : finalRequests) {
            finalRequest.setPoints(maxPoints);
            maxPoints -= 10;
        }
    }

}


