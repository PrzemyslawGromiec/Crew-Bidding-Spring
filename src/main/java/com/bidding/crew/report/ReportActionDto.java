package com.bidding.crew.report;

enum ActionType {
    CREATE_REPORT, NEXT_PERIOD, CHOOSE_FLIGHT
}


public class ReportActionDto {
    private ActionType actionType;

    public ReportActionDto() {
        this.actionType = ActionType.CREATE_REPORT;
    }

    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public String toString() {
        return "ReportActionDto{" +
                "actionType=" + actionType +
                '}';
    }
}
