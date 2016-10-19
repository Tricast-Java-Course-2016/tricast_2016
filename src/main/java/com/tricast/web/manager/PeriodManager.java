package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tricast.beans.Period;
import com.tricast.database.Workspace;

public interface PeriodManager {

	List<Period> getAll(Workspace workspace) throws SQLException, IOException;

	Period getById(Workspace workspace, long id) throws SQLException, IOException;

	Long create(Workspace workspace, Period event) throws SQLException, IOException;

	Long update(Workspace workspace, Period event) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
}
