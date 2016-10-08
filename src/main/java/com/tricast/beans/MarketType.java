package com.tricast.beans;

public enum MarketType {

    SINGLE(1), SYSTEM(2);

    private long marketTypeId;

    private MarketType(long typeId) {
        this.marketTypeId = typeId;
    }

    public long getTypeId() {
        return marketTypeId;
    }

    public static MarketType getType(long id) {
        return id == 1 ? SINGLE : SYSTEM;
    }

}
