package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.tricast.beans.Country;
import com.tricast.database.Workspace;

public interface CountryManager {

	List<Country> getAll(Workspace workspace) throws SQLException, IOException;

	Country getById(Workspace workspace, long id) throws SQLException, IOException;

	Long create(Workspace workspace, Country country) throws SQLException, IOException;

	Long update(Workspace workspace, Country country) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
}
