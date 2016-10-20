package com.tricast.beans;

public class Outcome {
    private long id;
    private long marketid;
    private String outcomecode;
    private String description;
    private double odds;
    private String result;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMarketid() {
        return marketid;
    }

    public void setMarketid(long marketid) {
        this.marketid = marketid;
    }

    public String getOutcomecode() {
        return outcomecode;
    }

    public void setOutcomecode(String outcomecode) {
        this.outcomecode = outcomecode;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }



}
