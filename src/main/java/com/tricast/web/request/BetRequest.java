package com.tricast.web.request;

public class BetRequest {

	private double stake;
    private long outcomeId;
    private long accountId;
    private long betTypeId;
    
    public double getStake() {
		return stake;
	}
	public void setStake(double stake) {
		this.stake = stake;
	}
	public long getOutcomeId() {
		return outcomeId;
	}
	public void setOutcomeId(long outcomeId) {
		this.outcomeId = outcomeId;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getBetTypeId() {
		return betTypeId;
	}
	public void setBetTypeId(long betTypeId) {
		this.betTypeId = betTypeId;
	}

}
