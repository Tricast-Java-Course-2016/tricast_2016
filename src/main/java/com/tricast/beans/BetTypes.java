package com.tricast.beans;

public enum BetTypes {
	SINGLE(1), 
	SYSTEM(2);
	
	private long typeId;
	private String description;
	
	private BetTypes(long typeId)
	{
		this.typeId = typeId;
	}

	public static BetTypes getType(long id)
	{
		return id == 1 ? SINGLE : SYSTEM;
	}

	public long getTypeId() { return typeId; }
	public void setTypeId(long typeId) { this.typeId = typeId; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

}
