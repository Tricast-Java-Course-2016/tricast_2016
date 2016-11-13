package com.tricast.beans;

public enum PeriodType {

	FIRSTHALF(1),
	SECONDHALF(2),
	NINETYNINEMINS(3),
	FULLTIME(4);
	
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
	
	public static PeriodType getType(long id)
	{
		if(id== FIRSTHALF.getTypeId()){
			return FIRSTHALF;
		}
		else if(id== SECONDHALF.getTypeId()){
			return SECONDHALF;
		}
		else if(id== NINETYNINEMINS.getTypeId()){
			return SECONDHALF;
		}
		else
			return FULLTIME;
		}
}
