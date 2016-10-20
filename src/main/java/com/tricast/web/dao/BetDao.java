package com.tricast.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Bet;
import com.tricast.database.Workspace;

public interface BetDao {

    List<Bet> getAll(Workspace workspace) throws SQLException, IOException;

    Bet getById(Workspace workspace, long id) throws SQLException, IOException;

    Long create(Workspace workspace, Bet newItem) throws SQLException, IOException;

    Long update(Workspace workspace, Bet updateItem) throws SQLException, IOException;

    boolean deleteById(Workspace workspace, long betId) throws SQLException, IOException;
}
