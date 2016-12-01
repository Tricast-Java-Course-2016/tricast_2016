package com.tricast.beans;

public enum MarketType {

    WDW(1, "Win/Draw/Win"),
    TOTAL_GOALS_OU(2, "Total Goals O/U 2.5"),
    CORRECT_SCORE(3, "Correct score"),
    DOUBLE_CHANCE(4, "Chance");

    private int id;
    private String description;

    private MarketType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}
