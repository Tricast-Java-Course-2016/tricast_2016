package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.tricast.beans.Period;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.PeriodDao;
import com.tricast.web.response.MarketResponse;
import com.tricast.web.response.PeriodTypeResponse;

public class PeriodManagerImpl implements PeriodManager {
	private final PeriodDao periodDao;
    private MarketManager marketManager;

	@Inject
    public PeriodManagerImpl(PeriodDao perioddao, MarketManager marketManager) {
		this.periodDao = perioddao;
        this.marketManager = marketManager;
	}

	@Override
	@JdbcTransaction
	public List<Period> getAll(Workspace workspace) throws SQLException, IOException {
		return periodDao.getAll(workspace);
	}

	@Override
	@JdbcTransaction
	public Period getById(Workspace workspace, long id) throws SQLException, IOException {
		return periodDao.getById(workspace,id);
	}

	@Override
	@JdbcTransaction
	public Period create(Workspace workspace,Period newPeriod) throws SQLException, IOException {
		 Long id = periodDao.create(workspace, newPeriod);
	        if (id != null) {
	            return periodDao.getById(workspace, id);
	        } else {
	            return null;
	        }
	}

	@Override
	@JdbcTransaction
	public Period update(Workspace workspace,Period updatePeriod) throws SQLException, IOException {
		 Long id = periodDao.update(workspace, updatePeriod);
	        if (id != null) {
	            return periodDao.getById(workspace, id);
	        } else {
	            return null;
	        }
	}

	@Override
	@JdbcTransaction
	public boolean deleteById(Workspace workspace, long id) throws SQLException, IOException {
		return periodDao.deleteById(workspace, id);

	}

	@Override
	@JdbcTransaction
	public List<PeriodTypeResponse> getAllPeriodType(Workspace workspace) throws SQLException, IOException {
		return periodDao.getAllPeriodType(workspace);
	}

    @Override
    @JdbcTransaction
    public void settlePeriod(Workspace workspace, Period updatePeriod) throws SQLException, IOException {

        update(workspace, updatePeriod);

        List<MarketResponse> markets = marketManager.loadMarketsByPeriodId(workspace, updatePeriod.getId());

        for (MarketResponse market : markets) {
            marketManager.resultMarket(workspace, market, updatePeriod.getHomeTeamScore(),
                    updatePeriod.getAwayTeamScore());
        }

    }

}
