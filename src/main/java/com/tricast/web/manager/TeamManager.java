package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Team;
import com.tricast.database.Workspace;

public interface TeamManager {
	List<Team> getAll(Workspace workspace) throws SQLException, IOException;

	Team getById(Workspace workspace, long id) throws SQLException, IOException;

    Team create(Workspace workspace, Team event) throws SQLException, IOException;

    Team update(Workspace workspace, Team event) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
}
