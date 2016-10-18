package com.tricast.web.dao;
import com.tricast.beans.Period;
import com.tricast.database.Workspace;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface PeriodDao {

	List<Period> getAll(Workspace workspace) throws SQLException,IOException;
	Period getById(Workspace workspace, long id) throws SQLException, IOException;
	Long create(Workspace workspace, Period newItem) throws SQLException, IOException;
	Long update(Workspace workspace, Period updateItem) throws SQLException, IOException;
	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
	
}
