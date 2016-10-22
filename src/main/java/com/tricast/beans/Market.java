package com.tricast.beans;

public class Market {

	private long id;
	private long periodId;
	private long marketTypeId;
	private String description;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(long periodId) {
		this.periodId = periodId;
	}
	
	public long getMarketTypeId() {
		return marketTypeId;
	}
	public void setMarketTypeId(long marketTypeId) {
		this.marketTypeId = marketTypeId;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
