package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.tricast.beans.Country;
import com.tricast.database.Workspace;
import com.tricast.web.dao.CountryDao;

public class CountryManagerImpl implements CountryManager {

	private final CountryDao countryDao;
	@Inject
	public CountryManagerImpl(CountryDao countryDao) {
		this.countryDao = countryDao;
	}
	
	@Override
	public List<Country> getAll(Workspace workspace) throws SQLException, IOException {
		List<Country> list = new ArrayList<Country>();
		list = this.countryDao.getAll(workspace);
		return list;
	}

	@Override
	public Country getById(Workspace workspace, long id) throws SQLException, IOException {
		Country country = this.countryDao.getById(workspace, id);
		return country;
	}

	@Override
	public Long create(Workspace workspace, Country newItem) throws SQLException, IOException {
		Long l = null;
		l = this.countryDao.create(workspace, newItem);
		return l;
	}

	@Override
	public Long update(Workspace workspace, Country updateItem) throws SQLException, IOException {
		Long l;
		l = this.countryDao.create(workspace, updateItem);
		return l;
	}

	@Override
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		return this.countryDao.deleteById(workspace,Id);
	}

}
