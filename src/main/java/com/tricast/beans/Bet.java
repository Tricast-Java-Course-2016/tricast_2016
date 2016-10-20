package com.tricast.beans;

public class Bet {
	
	private long id;
	private long accountId;
	private long betTypeId;
	
	public Bet(long id, long accountId, long betTypeId)
	{
		this.id = id;
		this.accountId = accountId;
		this.betTypeId = betTypeId;
	}

    public Bet() {
        this(-1, -1, -1);
    }
	
	public long getId() { return id; }
	public void setId(long id) { this.id = id; }
	
	public long getBetTypeId() { return betTypeId; }
	public void setBetTypeId(long betTypeId) { this.betTypeId = betTypeId; }
	
	public long getAccountId() { return accountId; }
	public void setAccountId(long accountId) { this.accountId = accountId; }
	
}
