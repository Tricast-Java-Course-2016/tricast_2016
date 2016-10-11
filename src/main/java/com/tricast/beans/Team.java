package com.tricast.beans;

public class Team {
	
	private long id;
	private String description;
	public Team(long id, String description) {
		super();
		this.id = id;
		this.description = description;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
