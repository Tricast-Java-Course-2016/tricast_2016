package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Bet;
import com.tricast.database.Workspace;
import com.tricast.web.response.BetPlacementResponse;

public interface BetManager {

    List<Bet> getAll(Workspace workspace) throws SQLException, IOException;

    Bet getById(Workspace workspace, long id) throws SQLException, IOException;

    Bet create(Workspace workspace, Bet newBet) throws SQLException, IOException;

    Bet update(Workspace workspace, Bet updateBet) throws SQLException, IOException;

    boolean deleteById(Workspace workspace, long betId) throws SQLException, IOException;
    
    BetPlacementResponse getBetInformation(Workspace workspace, long eventId, long accountId) throws SQLException, IOException;
}
