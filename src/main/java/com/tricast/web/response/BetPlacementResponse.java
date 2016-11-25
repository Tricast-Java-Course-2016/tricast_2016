package com.tricast.web.response;

import java.sql.Date;
import java.util.List;

public class BetPlacementResponse {

//	le kell kérni: 
//1 OK account balance id alapján (accountGetBalance.sql)
//2 OK eseményről alap infókat (új REST hívás: eventGetDetailsById.sql)
//3 OK periódusok listáját (periodtypeGetAll.sql)
//4 OK ehhez az eseményhez tartozó Market-ok listáját (új REST hívás: MarketResponse objektummal) (marketGetDetailsByEventId.sql)
//5 OK ki kell listázni majd táblázat szerűen a Market-hoz tartozó Outcome-okat (outcomeGetDetailsById.sql)

	private double balance;
	private long eventId;
    private String eventDescription;
    private String countryDescription;
    private String leagueDescription;
    private String homeTeamDescription;
    private String awayTeamDescription;
    private Date eventStartDate;
    private String eventStatus;	//csak akkor engedjük legenerálni a fogadás oldalat, ha ez OPEN
    private List<PeriodTypeResponse> periods;
    //private List<String> periodDescription;
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

	public List<MarketResponse> getMarkets() {
		return markets;
	}
	public void setMarkets(List<MarketResponse> markets) {
		this.markets = markets;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public List<PeriodTypeResponse> getPeriods() {
		return periods;
	}
	public void setPeriods(List<PeriodTypeResponse> periods) {
		this.periods = periods;
	}


}
