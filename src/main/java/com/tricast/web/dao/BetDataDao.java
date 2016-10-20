package com.tricast.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.BetData;
import com.tricast.database.Workspace;

public interface BetDataDao {

    List<BetData> getAll(Workspace workspace) throws SQLException, IOException;

    BetData getById(Workspace workspace, long betId, long outcomeId) throws SQLException, IOException;

    Long[] create(Workspace workspace, BetData newItem) throws SQLException, IOException;

    Long[] update(Workspace workspace, BetData updateItem) throws SQLException, IOException;

    boolean deleteById(Workspace workspace, long betId, long outcomeId) throws SQLException, IOException;
}
