package com.tricast.beans;

public class Outcome {
    private long id;
    private long marketId;
    private String outcomeCode;
    private String description;
    private double odds;
    private char result;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMarketId() {
        return marketId;
    }

    public void setMarketId(long marketId) {
        this.marketId = marketId;
    }

    public String getOutcomeCode() {
        return outcomeCode;
    }

    public void setOutcomeCode(String outcomeCode) {
        this.outcomeCode = outcomeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public char getResult() {
        return result;
    }

    public void setResult(char result) {
        this.result = result;
    }

}
