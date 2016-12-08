package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.tricast.beans.Market;
import com.tricast.beans.MarketType;
import com.tricast.beans.Outcome;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.MarketDao;
import com.tricast.web.dao.OutcomeDao;
import com.tricast.web.response.MarketResponse;
import com.tricast.web.response.OutcomeResponse;

public class MarketManagerImpl implements MarketManager {
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
        for (MarketResponse m : marketList) {
            long marketId = m.getMarketId();
            m.setOutcomes(outcomeDao.getByMarketId(workspace, marketId));
        }

        return marketList;
    }

    @Override
    @JdbcTransaction
    public boolean createMarketWithOutcomeByMarketType(Workspace workspace, String eventDescription, long periodId,
            MarketType marketType) throws SQLException, IOException {
        // Market creation stub
    	double odds = 1.9;
    	long marketId=(Long) null;
        Market newMarket = new Market();
        Outcome outcome = new Outcome();

        
        newMarket.setDescription(eventDescription + " " + marketType.getDescription());
        newMarket.setPeriodId(periodId);
        newMarket.setMarketTypeId(marketType.getId());
        marketId = marketDao.create(workspace, newMarket);
        String[] csapatok = eventDescription.split("-");

        // WDW outcome create 
        outcome.setDescription(csapatok[0]);
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("1");
        outcomeDao.create(workspace, outcome);
        outcome.setDescription("Draw");
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("X");
        outcomeDao.create(workspace, outcome);
        outcome.setDescription(csapatok[1]);
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("2");
        outcomeDao.create(workspace, outcome);
        
        // over-under 2.5 goal
        
        outcome.setDescription("Over 2.5");
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("O");
        outcomeDao.create(workspace, outcome);
        outcome.setDescription("Under 2.5");
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("U");
        outcomeDao.create(workspace, outcome);
        
        // double chance market
        
        outcome.setDescription(csapatok[0]+"/Draw");
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("1X");
        outcomeDao.create(workspace, outcome);
        outcome.setDescription(csapatok[0]+"/"+csapatok[1]);
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("12");
        outcomeDao.create(workspace, outcome);
        outcome.setDescription(csapatok[1]);
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode(csapatok[1]+"/Draw");
        outcomeDao.create(workspace, outcome);
        
        outcomeDao.create(workspace, outcome);
        return false;
    }

    @Override
    @JdbcTransaction
    public List<MarketResponse> loadMarketsByPeriodId(Workspace workspace, long periodId)
            throws SQLException, IOException {
        return marketDao.getMarketsByPeriodId(workspace, periodId);
    }

    @Override
    @JdbcTransaction
    public void resultMarket(Workspace workspace, MarketResponse market, long homeTeamScore, long awayTeamScore)
            throws SQLException, IOException {

        // set the result field on the outcome records
        if (market.getMarketType() == MarketType.WDW) {
            if (market.getOutcomes() == null) {
                return;
            }
            if (homeTeamScore > awayTeamScore) {
                for (OutcomeResponse outcome : market.getOutcomes()) {
                    if (outcome.getOutcomeCode() == "1") {
                        outcome.setResult("W");
                        outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
                    } else {
                        outcome.setResult("L");
                        outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
                    }
                }
            } else if (homeTeamScore < awayTeamScore) {
                for (OutcomeResponse outcome : market.getOutcomes()) {
                    if (outcome.getOutcomeCode() == "2") {
                        outcome.setResult("W");
                        outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
                    } else {
                        outcome.setResult("L");
                        outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
                    }
                }
            } else if (homeTeamScore == awayTeamScore) {
                for (OutcomeResponse outcome : market.getOutcomes()) {
                    if (outcome.getOutcomeCode() == "X") {
                        outcome.setResult("W");
                        outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
                    } else {
                        outcome.setResult("L");
                        outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
                    }
                }
            }
        }
    }

}
