package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.tricast.beans.Market;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.MarketDao;
import com.tricast.web.dao.OutcomeDao;
import com.tricast.web.response.MarketResponse;

public class MarketManagerImpl implements MarketManager{
	private final MarketDao marketDao;
	private final OutcomeDao outcomeDao;

	@Inject
    public MarketManagerImpl(MarketDao marketDao, OutcomeDao outcomeDao) {
		this.marketDao = marketDao;
		this.outcomeDao = outcomeDao;
	}
	
	@Override
	@JdbcTransaction
	public List<Market> getAll(Workspace workspace) throws SQLException, IOException {
		return marketDao.getAll(workspace);
	}

	@Override
	@JdbcTransaction
	public Market getById(Workspace workspace, long id) throws SQLException, IOException {
		Market market = marketDao.getById(workspace, id);
		return market;
	}

	@Override
	@JdbcTransaction
	public Market create(Workspace workspace, Market newMarket) throws SQLException, IOException {
		Long id = marketDao.create(workspace, newMarket);
		if (id != null) {
			return marketDao.getById(workspace, id);
		} else {
			return null;
		}
    }

	@Override
	@JdbcTransaction
	public Market update(Workspace workspace, Market updateMarket) throws SQLException, IOException {
	  Long id = marketDao.update(workspace, updateMarket);
        if (id != null) {
            return marketDao.getById(workspace, id);
        } else {
            return null;
        }
	}

	@Override
	@JdbcTransaction
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		return marketDao.deleteById(workspace, Id);
	}

	@Override
	@JdbcTransaction
	public List<MarketResponse> getDetailsByEventId(Workspace workspace, long eventId)
			throws SQLException, IOException {
		List<MarketResponse> marketList = marketDao.getDetailsByEventId(workspace, eventId);
		for(MarketResponse m : marketList){
			long marketId = m.getMarketId();
			m.setOutcomes(outcomeDao.getByMarketId(workspace, marketId));
		}
		
		return marketList;
	}

}
