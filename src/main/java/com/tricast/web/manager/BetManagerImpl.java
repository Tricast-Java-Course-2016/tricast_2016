package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.tricast.beans.Bet;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.BetDao;
import com.tricast.web.dao.EventDao;
import com.tricast.web.dao.MarketDao;
import com.tricast.web.dao.OutcomeDao;
import com.tricast.web.dao.PeriodDao;
import com.tricast.web.dao.TransactionDao;
import com.tricast.web.response.BetPlacementResponse;
import com.tricast.web.response.EventResponse;
import com.tricast.web.response.MarketResponse;
import com.tricast.web.response.OutcomeResponse;

public class BetManagerImpl implements BetManager {

    private final BetDao betDao;
    private final MarketDao marketDao;
    private final TransactionDao transactionDao;
    private final EventDao eventDao;
    private final OutcomeDao outcomeDao;

    @Inject
    public BetManagerImpl(BetDao betDao, MarketDao marketDao, PeriodDao periodDao,
    		TransactionDao transactionDao, EventDao eventDao, OutcomeDao outcomeDao) {
        this.betDao = betDao;
        this.marketDao = marketDao;
        this.transactionDao = transactionDao;
        this.eventDao = eventDao;
        this.outcomeDao = outcomeDao;
    }

    @Override
    @JdbcTransaction
    public List<Bet> getAll(Workspace workspace) throws SQLException, IOException {
        return betDao.getAll(workspace);
    }

    @Override
    @JdbcTransaction
    public Bet getById(Workspace workspace, long id) throws SQLException, IOException {
        return betDao.getById(workspace, id);
    }

    @Override
    @JdbcTransaction
    public Bet create(Workspace workspace, Bet newBet) throws SQLException, IOException {
        Long id = betDao.create(workspace, newBet);
        if (id != null) {
            return betDao.getById(workspace, id);
        } else {
            return null;
        }
    }

    @Override
    @JdbcTransaction
    public Bet update(Workspace workspace, Bet updateBet) throws SQLException, IOException {
        Long id = betDao.update(workspace, updateBet);
        if (id != null) {
            return betDao.getById(workspace, id);
        } else {
            return null;
        }
    }

    @Override
    @JdbcTransaction
    public boolean deleteById(Workspace workspace, long betId) throws SQLException, IOException {
        return betDao.deleteById(workspace, betId);
    }
    
	@Override
	@JdbcTransaction
	public BetPlacementResponse getBetInformation(Workspace workspace, long eventId, long accountId)
			throws SQLException, IOException {
		
		
		BetPlacementResponse data = new BetPlacementResponse();
		//account data
		data.setBalance(transactionDao.getAmountByAccountId(workspace, accountId));

		//event data
		EventResponse event = eventDao.getEventDetails(workspace, eventId);
		data.setEventId(eventId);
		data.setEventDescription(event.getDescription());
		data.setCountryDescription(event.getCountry());
		data.setLeagueDescription(event.getLeague());
		data.setHomeTeamDescription(event.getHomeTeam());
		data.setAwayTeamDescription(event.getAwayTeam());
		data.setEventStartDate(event.getStartTime());
		data.setEventStatus(event.getStatus());
		
		// Period Strings
		List<String> periodDescriptions = new ArrayList<String>();
		periodDescriptions.add("1st half");
		periodDescriptions.add("2nd half");
		periodDescriptions.add("90 mins");
		periodDescriptions.add("full time");
		data.setPeriodDescription(periodDescriptions);
		
		//all markets for this eventId - TODO Only if the event is OPEN
		List<MarketResponse> marketList = marketDao.getDetailsByEventId(workspace, eventId);
			for(MarketResponse m : marketList) {
					long marketId = m.getMarketId();
					m.setOutcomes(outcomeDao.getByMarketId(workspace, marketId));
	
			}
			data.setMarkets(marketList);
		
		return data;
	}


}
