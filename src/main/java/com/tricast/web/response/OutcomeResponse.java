package com.tricast.web.response;

public class OutcomeResponse {

    private long outcomeId;
    private String description;
    private String outcomeCode;
    private Double odds;
    private String result;

    public long getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(long outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutcomeCode() {
        return outcomeCode;
    }

    public void setOutcomeCode(String outcomeCode) {
        this.outcomeCode = outcomeCode;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
