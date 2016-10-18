package com.tricast.web.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.tricast.beans.Country;
import com.tricast.database.Workspace;

public interface CountryDao {

	List<Country> getAll(Workspace workspace) throws SQLException, IOException;

	Country getById(Workspace workspace, long id) throws SQLException, IOException;

	Long create(Workspace workspace, Country newItem) throws SQLException, IOException;

	Long update(Workspace workspace, Country updateItem) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;

}
