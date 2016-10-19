package com.tricast.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.tricast.beans.League;
import com.tricast.database.Workspace;

public interface LeagueDao {

	List<League> getAll(Workspace workspace) throws SQLException, IOException;

	League getById(Workspace workspace, long id) throws SQLException, IOException;

	Long create(Workspace workspace, League newItem) throws SQLException, IOException;

	Long update(Workspace workspace, League updateItem) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
}
