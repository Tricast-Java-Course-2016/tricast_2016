package com.tricast.beans;

public class Period {
	private long id;
	private long eventId;
	private long periodTypeId;
	private String description;
    private String periodTypeDescription;
	private long homeTeamScore;
	private long awayTeamScore;
    private String periodResult;

    public String getPeriodResult() {
        periodResult = homeTeamScore + " : " + awayTeamScore;
        return periodResult;
    }

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public long getPeriodTypeId() {
		return periodTypeId;
	}
	public void setPeriodTypeId(long periodTypeId) {
		this.periodTypeId = periodTypeId;
	}
	public String getDescription() {
		return description;
	}

    public String getPeriodTypeDescription() {
        if (periodTypeId == PeriodType.FIRSTHALF.getTypeId()) {
            periodTypeDescription = "First half";
        } else if (periodTypeId == PeriodType.SECONDHALF.getTypeId()) {
            periodTypeDescription = "Second half";
        } else if (periodTypeId == PeriodType.NINETYNINEMINS.getTypeId()) {
            periodTypeDescription = "90 min";
        } else {
            periodTypeDescription = "Full time";
        }
        return periodTypeDescription;
    }
	public void setDescription(String description) {
		this.description = description;
	}
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





}
