package com.tricast.beans;

import java.sql.Date;

public class Transaction {

	private long id;
	private long accountId;
	private long betid;
	private Date createdDate;
	private String description;
	private double amount;

	public Transaction(long id, long accountId, long betid, Date createdDate,
		String description, double amount)
	{
		this.id = id;
		this.accountId = accountId;
		this.betid = betid;
		this.createdDate = createdDate;
		this.description = description;
		this.amount = amount;
	}

	public Transaction(long id, long accountId, Date createdDate,
		String description, double amount)
	{
		this(id, accountId, -1, createdDate, description, amount);
	}

    public Transaction() {
        this(-1, -1, -1, new Date(0), "", -1.0);
    }

	public long getId() { return id; }
	public void setId(long id) { this.id = id; }

	public long getAccountId() { return accountId; }
	public void setAccountId(long accountId) { this.accountId = accountId; }

	public long getBetId() { return betid; }
	public void setBetId(long betid) { this.betid = betid; }

	public Date getCreatedDate() { return createdDate; }
	public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public double getAmount() { return amount; }
	public void setAmount(double amount) { this.amount = amount; }

}
