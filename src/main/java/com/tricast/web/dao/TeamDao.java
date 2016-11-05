package com.tricast.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.tricast.beans.Team;
import com.tricast.database.Workspace;

public interface TeamDao {

	List<Team> getAll(Workspace workspace) throws SQLException,IOException;
	
	Team getById(Workspace workspace, long id) throws SQLException, IOException;
	
	Long create(Workspace workspace, Team newItem) throws SQLException, IOException;
	
	Long update(Workspace workspace, Team updateItem) throws SQLException, IOException;
	
	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
}
