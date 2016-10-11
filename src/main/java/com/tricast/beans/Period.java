package com.tricast.beans;

public class Period {
	private long id;
	private long eventId;
	private long periodTypeId;
	private String description;
	private int homeTeamScore;
	private int awayTeamScore;
	
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
	public void setDescription(String description) {
		this.description = description;
	}
	public int getHomeTeamScore() {
		return homeTeamScore;
	}
	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}
	public int getAwayTeamScore() {
		return awayTeamScore;
	}
	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}
	
	
	
	

}
