package com.tricast.beans;

public enum PeriodType {

	ELSOFELIDO(1),
	MASODIKFELIDO(2),
	KILENCVENPERC(3),
	TELJESJATEKIDO(4);
	
	private long typeId;

	private PeriodType(long typeId) {
		this.typeId = typeId;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	
	
}
