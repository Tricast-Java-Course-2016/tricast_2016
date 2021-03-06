package com.tricast.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Outcome;
import com.tricast.database.Workspace;
import com.tricast.web.response.OutcomeResponse;

public interface OutcomeDao {
    List<Outcome> getAll(Workspace workspace) throws SQLException, IOException;

    Outcome getById(Workspace workspace, long id) throws SQLException, IOException;

    Long create(Workspace workspace, Outcome newItem) throws SQLException, IOException;

    Long update(Workspace workspace, Outcome updateItem) throws SQLException, IOException;

    boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;

    List<OutcomeResponse> getByMarketId(Workspace workspace, long marketId) throws SQLException, IOException;

    void updateOutcomeResult(Workspace workspace, long outcomeId, String result) throws SQLException, IOException;
}
