package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.BetData;
import com.tricast.database.Workspace;

public interface BetDataManager {

    List<BetData> getAll(Workspace workspace) throws SQLException, IOException;

    BetData getById(Workspace workspace, long betId, long outcomeId) throws SQLException, IOException;

    BetData create(Workspace workspace, BetData newBetData) throws SQLException, IOException;

    BetData update(Workspace workspace, BetData updateBetData) throws SQLException, IOException;

    boolean deleteById(Workspace workspace, long betId, long outcomeId) throws SQLException, IOException;
}