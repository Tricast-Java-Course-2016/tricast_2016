package com.tricast.web.manager;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import com.tricast.beans.Bet;
import com.tricast.beans.BetData;
import com.tricast.beans.BetForSettlement;
import com.tricast.beans.Outcome;
import com.tricast.beans.Transaction;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.BetDao;
import com.tricast.web.dao.BetDataDao;
import com.tricast.web.dao.EventDao;
import com.tricast.web.dao.MarketDao;
import com.tricast.web.dao.OutcomeDao;
import com.tricast.web.dao.PeriodDao;
import com.tricast.web.dao.TransactionDao;
import com.tricast.web.request.BetRequest;
import com.tricast.web.response.BetPlacementResponse;
import com.tricast.web.response.EventResponse;
import com.tricast.web.response.MarketResponse;

public class BetManagerImpl implements BetManager {

    private final BetDao betDao;
    private final BetDataDao betDataDao;
    private final MarketDao marketDao;
    private final TransactionDao transactionDao;
    private final EventDao eventDao;
    private final OutcomeDao outcomeDao;
    private final PeriodDao periodDao;

    @Inject
    public BetManagerImpl(BetDao betDao, MarketDao marketDao, PeriodDao periodDao,
    		TransactionDao transactionDao, EventDao eventDao, OutcomeDao outcomeDao,
    		BetDataDao betDataDao) {
        this.betDao = betDao;
        this.marketDao = marketDao;
        this.transactionDao = transactionDao;
        this.eventDao = eventDao;
        this.outcomeDao = outcomeDao;
        this.betDataDao = betDataDao;
        this.periodDao = periodDao;
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

		// Periods
		data.setPeriods(periodDao.getAllPeriodType(workspace));

		//all markets for this eventId - TODO Only if the event is OPEN
		List<MarketResponse> marketList = marketDao.getDetailsByEventId(workspace, eventId);

		// all outcomes for these markets
		for(MarketResponse m : marketList) {
				long marketId = m.getMarketId();
				m.setOutcomes(outcomeDao.getByMarketId(workspace, marketId));

		}
		data.setMarkets(marketList);

		return data;
	}

	@Override
	@JdbcTransaction
	public BetRequest placeBet(Workspace workspace, BetRequest betRequest) throws SQLException, IOException {

		Bet newBet = new Bet();
		BetData newBetData = new BetData();
		Transaction newTransaction = new Transaction();
		Outcome selectedOutcome = outcomeDao.getById(workspace, betRequest.getOutcomeId());

		newBet.setAccountId(betRequest.getAccountId());
		newBet.setBetTypeId(betRequest.getBetTypeId());

		// get the newly created bet's CORRECT betId!!!
		long betId = betDao.create(workspace, newBet);

		newBetData.setOdds(selectedOutcome.getOdds());
		newBetData.setOutcomeId(betRequest.getOutcomeId());
		newBetData.setBetId(betId);
		betDataDao.create(workspace, newBetData);

		newTransaction.setAccountId(betRequest.getAccountId());
		newTransaction.setBetId(betId);
		newTransaction.setAmount(-betRequest.getStake());
		newTransaction.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
		newTransaction.setDescription("Place bet for " + betRequest.getStake() + " HUF");
		transactionDao.create(workspace, newTransaction);

		return betRequest;
	}

    @Override
    @JdbcTransaction
    public long settleBetsForOutcome(Workspace workspace, long outcomeId) throws SQLException, IOException {

        List<BetForSettlement> betsToSettle = betDao.getBetsForSettlement(workspace, outcomeId);

        for (BetForSettlement bet : betsToSettle) {
            double winnings = bet.getAmount() * -1 * bet.getOdds();
            Transaction newTransaction = new Transaction();
            newTransaction.setAccountId(bet.getAccountId());
            newTransaction.setBetId(bet.getBetId());
            newTransaction.setDescription("Bet won. Credited by settlement.");
            newTransaction.setAmount(winnings);
            transactionDao.create(workspace, newTransaction);
        }

        return betsToSettle.size();
    }

}
