package com.tricast.beans;

public enum MarketType {

    SINGLE(1), SYSTEM(2);

    private long marketTypeId;
    private String marketTypeIdDescription;

    private MarketType(long typeId) {
        this.marketTypeId = typeId;
    }

    public String getMarketTypeIdDescription() {
        return marketTypeIdDescription;
    }

    public void setMarketTypeIdDescription(String marketTypeIdDescription) {
        this.marketTypeIdDescription = marketTypeIdDescription;
    }
    public long getTypeId() {
        return marketTypeId;
    }

    public static MarketType getType(long id) {
        return id == SINGLE.getTypeId() ? SINGLE : SYSTEM;
    }

}
