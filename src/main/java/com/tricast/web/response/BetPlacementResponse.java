package com.tricast.web.response;

import java.sql.Date;
import java.util.List;

public class BetPlacementResponse {

	private long eventId;
    private String eventDescription;
    private String countryDescription;
    private String leagueDescription;
    private String homeTeamDescription;
    private String awayTeamDescription;
    private Date eventStartDate;
    private String eventStatus;
    private List<String> periodDescription;
    private List<MarketResponse> markets;
    
    public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getCountryDescription() {
		return countryDescription;
	}
	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}
	public String getLeagueDescription() {
		return leagueDescription;
	}
	public void setLeagueDescription(String leagueDescription) {
		this.leagueDescription = leagueDescription;
	}
	public String getHomeTeamDescription() {
		return homeTeamDescription;
	}
	public void setHomeTeamDescription(String homeTeamDescription) {
		this.homeTeamDescription = homeTeamDescription;
	}
	public String getAwayTeamDescription() {
		return awayTeamDescription;
	}
	public void setAwayTeamDescription(String awayTeamDescription) {
		this.awayTeamDescription = awayTeamDescription;
	}
	public Date getEventStartDate() {
		return eventStartDate;
	}
	public void setEventStartDate(Date eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public List<String> getPeriodDescription() {
		return periodDescription;
	}
	public void setPeriodDescription(List<String> periodDescription) {
		this.periodDescription = periodDescription;
	}
	public List<MarketResponse> getMarkets() {
		return markets;
	}
	public void setMarkets(List<MarketResponse> markets) {
		this.markets = markets;
	}


}
