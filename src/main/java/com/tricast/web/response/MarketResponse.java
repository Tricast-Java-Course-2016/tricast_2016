package com.tricast.web.response;

import java.util.List;

import com.tricast.beans.MarketType;

public class MarketResponse {

    private long marketId;
    private long periodId;
    private String periodDescription;
    private long eventId;
    private long marketTypeId;
    private String marketTypeDescription;
    private MarketType marketType;
    private List<OutcomeResponse> outcomes;

    public long getMarketId() {
        return marketId;
    }

    public void setMarketId(long marketId) {
        this.marketId = marketId;
    }

    public long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    public String getPeriodDescription() {
        return periodDescription;
    }

    public void setPeriodDescription(String periodDescription) {
        this.periodDescription = periodDescription;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getMarketTypeId() {
        return marketTypeId;
    }

    public void setMarketTypeId(long marketTypeId) {
        this.marketTypeId = marketTypeId;
    }

    public List<OutcomeResponse> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<OutcomeResponse> outcomes) {
        this.outcomes = outcomes;
    }

	public void setMarketTypeDescription(String marketTypeDescription) {
		this.marketTypeDescription = marketTypeDescription;
	}

    public String getMarketTypeDescription() {
        return marketTypeDescription;
    }

    public void setMarketType(MarketType marketType) {
        this.marketType = marketType;
    }

    public MarketType getMarketType() {
        return marketType;
    }

}
