package com.tricast.web.request;

import java.math.BigDecimal;

public class BetRequest {

	private BigDecimal stake;
    private long outcomeId;
    private long accountId;
    // private long betTypeId;
    
    public BigDecimal getStake() {
		return stake;
	}
	public void setStake(BigDecimal stake) {
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

}
