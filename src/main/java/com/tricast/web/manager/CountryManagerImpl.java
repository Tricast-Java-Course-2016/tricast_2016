package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.tricast.beans.Country;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.CountryDao;

public class CountryManagerImpl implements CountryManager {
	private final CountryDao countryDao;
	
	@Inject
	public CountryManagerImpl(CountryDao countryDao) {
		this.countryDao = countryDao;
	}
	
	@Override
	@JdbcTransaction
	public List<Country> getAll(Workspace workspace) throws SQLException, IOException {
		return countryDao.getAll(workspace);
	}

	@Override
	@JdbcTransaction
	public Country getById(Workspace workspace, long id) throws SQLException, IOException {
		Country country = countryDao.getById(workspace, id);
		return country;
	}

	@JdbcTransaction
	@Override
	public Country create(Workspace workspace, Country newCountry) throws SQLException, IOException {
		
		Long id = countryDao.create(workspace, newCountry);
		if (id != null) {
            return countryDao.getById(workspace, id);
        } else {
            return null;
        }
	}

	@JdbcTransaction
	@Override
	public Country update(Workspace workspace, Country updateCountry) throws SQLException, IOException {
		 Long id = countryDao.update(workspace, updateCountry);
	        if (id != null) {
	            return countryDao.getById(workspace, id);
	        } else {
	            return null;
	        }
	}

	@JdbcTransaction
	@Override
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		return countryDao.deleteById(workspace,Id);
	}

}
