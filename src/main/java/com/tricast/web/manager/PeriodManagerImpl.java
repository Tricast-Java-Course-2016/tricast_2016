package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.tricast.beans.Period;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.PeriodDao;

public class PeriodManagerImpl implements PeriodManager {

	private final PeriodDao periodDao;
	@Inject
	public PeriodManagerImpl(PeriodDao perioddao) {
		this.periodDao = perioddao;
	}
	@Override
	public List<Period> getAll(Workspace workspace) throws SQLException, IOException {
		List<Period> list;
		list = this.periodDao.getAll(workspace);
		return list;
	}

	@Override
	public Period getById(Workspace workspace, long id) throws SQLException, IOException {
		Period period;
		period = this.periodDao.getById(workspace, id);
		return period;
	}

	@JdbcTransaction
	@Override
	public Long create(Workspace workspace,Period period) throws SQLException, IOException {
		Long l = this.periodDao.create(workspace, period);
		return l;
	}

	@JdbcTransaction
	@Override
	public Long update(Workspace workspace,Period period) throws SQLException, IOException {
		long l = this.periodDao.update(workspace, period);
		return l;
	}

	@JdbcTransaction
	@Override
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		boolean b = this.periodDao.deleteById(workspace, Id);
		return b;
	}


}
