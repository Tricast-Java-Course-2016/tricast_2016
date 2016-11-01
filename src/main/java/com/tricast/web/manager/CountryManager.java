package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.tricast.beans.Country;
import com.tricast.database.Workspace;

public interface CountryManager {

	List<Country> getAll(Workspace workspace) throws SQLException, IOException;

	Country getById(Workspace workspace, long id) throws SQLException, IOException;

	Country create(Workspace workspace, Country newCountry) throws SQLException, IOException;

	Country update(Workspace workspace, Country updateCountry) throws SQLException, IOException;

	boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException;
}
