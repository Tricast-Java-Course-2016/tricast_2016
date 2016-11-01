package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.League;
import com.tricast.database.Workspace;

public interface LeagueManager {
	List<League> getAll(Workspace workspace) throws SQLException, IOException;

	League getById(Workspace workspace, long id) throws SQLException, IOException;

	League create(Workspace workspace, League newLeague) throws SQLException, IOException;

	League update(Workspace workspace, League updateLeague) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;

}
