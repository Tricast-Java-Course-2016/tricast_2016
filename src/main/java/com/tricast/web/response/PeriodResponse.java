package com.tricast.web.response;

public class PeriodResponse {
    private long periodId;
    private long homeTeamScore;
    private long awayTeamScore;

    public long getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(long homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public long getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(long awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }
}
