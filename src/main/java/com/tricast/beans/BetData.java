package com.tricast.beans;

public class BetData {

	private long betId;
	private long outcomeId;
	private double odds;

	public long getBetId() { return betId; }
	public void setBetId(long betId) { this.betId = betId; }

	public long getOutcomeId() { return outcomeId; }
	public void setOutcomeId(long outcomeId) { this.outcomeId = outcomeId; }

	public double getOdds() { return odds; }
	public void setOdds(double odds) { this.odds = odds; }
}
