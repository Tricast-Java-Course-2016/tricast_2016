package com.tricast.web.response;

import java.sql.Date;
import java.util.List;

public class BetPlacementResponse {

    private long eventId;
    private String eventDescription;
    private String countryDescription;
    private String leagueDescription;
    private String homeTeamDescription;
    private String awayTeamDescription;
    private Date eventStartDate;
    private String eventStatus;
    private List<String> periodDescription;
    private List<MarketResponse> markets;

}
