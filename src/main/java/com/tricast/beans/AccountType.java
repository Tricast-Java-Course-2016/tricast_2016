package com.tricast.beans;

public enum AccountType {

	OPERATOR(1),
    PLAYER(2);
	private long typeId;

    private AccountType(long typeId) {
		this.typeId = typeId;
	}

	public long getTypeId() {
		return typeId;
	}

    public static AccountType getType(long id) {
        return id == OPERATOR.getTypeId() ? OPERATOR : PLAYER;
	}

}
