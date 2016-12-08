package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.tricast.beans.Market;
import com.tricast.beans.MarketType;
import com.tricast.beans.Outcome;
import com.tricast.beans.Team;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.MarketDao;
import com.tricast.web.dao.OutcomeDao;
import com.tricast.web.response.MarketResponse;
import com.tricast.web.response.OutcomeResponse;

public class MarketManagerImpl implements MarketManager {
    private final MarketDao marketDao;
    private final OutcomeDao outcomeDao;
    private final BetManager betManager;

    @Inject
    public MarketManagerImpl(MarketDao marketDao, OutcomeDao outcomeDao, BetManager betManager) {
        this.marketDao = marketDao;
        this.outcomeDao = outcomeDao;
        this.betManager = betManager;
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
            MarketType marketType, Long eventId, Team homeTeam, Team awayTeam) throws SQLException, IOException {
        // Market creation stub
        double odds = 1.9;
        long marketId = 0;
        Market newMarket = new Market();
        Outcome outcome = new Outcome();

        newMarket.setDescription(eventDescription + " " + marketType.getDescription());
        newMarket.setPeriodId(periodId);
        newMarket.setMarketTypeId(marketType.getId());
        newMarket.setEventId(eventId);

        marketId = marketDao.create(workspace, newMarket);
        // String[] csapatok = eventDescription.split("-");

        // WDW outcome create
        outcome.setDescription(homeTeam.getDescription());
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("1");
        outcomeDao.create(workspace, outcome);
        outcome.setDescription("Draw");
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("X");
        outcomeDao.create(workspace, outcome);
        outcome.setDescription(awayTeam.getDescription());
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

        outcome.setDescription(homeTeam.getDescription() + "/Draw");
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("1X");
        outcomeDao.create(workspace, outcome);
        outcome.setDescription(homeTeam.getDescription() + "/" + awayTeam.getDescription());
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("12");
        outcomeDao.create(workspace, outcome);
        outcome.setDescription(awayTeam.getDescription() + "/Draw");
        outcome.setMarketid(marketId);
        outcome.setOdds(odds);
        outcome.setOutcomecode("2X");
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
        List<Long> winningOutcomeIds = new ArrayList<Long>();

        // set the result field on the outcome records
        switch (market.getMarketType()) {
        case WDW:
            winningOutcomeIds = resultWDWMarketType(workspace, market, homeTeamScore, awayTeamScore);
            break;
        case TOTAL_GOALS_OU:
            winningOutcomeIds = resultOU25MarketType(workspace, market, homeTeamScore, awayTeamScore);
            break;
        case CORRECT_SCORE:
            winningOutcomeIds = resultCorrectScoreMarketType(workspace, market, homeTeamScore, awayTeamScore);
            break;
        case DOUBLE_CHANCE:
            winningOutcomeIds = resultDoubleChanceMarketType(workspace, market, homeTeamScore, awayTeamScore);
            break;
        }

        if (winningOutcomeIds == null) {
            return;
        }

        for (Long outcomeId : winningOutcomeIds) {
            betManager.settleBetsForOutcome(workspace, outcomeId);
        }
    }

    private List<Long> resultWDWMarketType(Workspace workspace, MarketResponse market, long homeTeamScore,
            long awayTeamScore) throws SQLException, IOException {
        List<Long> winningOutcomeIds = new ArrayList<Long>();
        if (market.getOutcomes() == null) {
            return null;
        }
        if (homeTeamScore > awayTeamScore) {
            for (OutcomeResponse outcome : market.getOutcomes()) {
                if (outcome.getOutcomeCode().equals("1")) {
                    outcome.setResult("W");
                    winningOutcomeIds.add(outcome.getOutcomeId());
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
                } else {
                    outcome.setResult("L");
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
                }
            }
        } else if (homeTeamScore < awayTeamScore) {
            for (OutcomeResponse outcome : market.getOutcomes()) {
                if (outcome.getOutcomeCode().equals("2")) {
                    outcome.setResult("W");
                    winningOutcomeIds.add(outcome.getOutcomeId());
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
                } else {
                    outcome.setResult("L");
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
                }
            }
        } else if (homeTeamScore == awayTeamScore) {
            for (OutcomeResponse outcome : market.getOutcomes()) {
                if (outcome.getOutcomeCode().equals("X")) {
                    outcome.setResult("W");
                    winningOutcomeIds.add(outcome.getOutcomeId());
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
                } else {
                    outcome.setResult("L");
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
                }
            }
        }
        return winningOutcomeIds;
    }

    private List<Long> resultDoubleChanceMarketType(Workspace workspace, MarketResponse market, long homeTeamScore,
            long awayTeamScore) throws SQLException, IOException {
        List<Long> winningOutcomeIds = new ArrayList<Long>();
        if (market.getOutcomes() == null) {
            return null;
        }
        if (homeTeamScore > awayTeamScore) {
            for (OutcomeResponse outcome : market.getOutcomes()) {
                if (!outcome.getOutcomeCode().equals("X2")) {
                    outcome.setResult("W");
                    winningOutcomeIds.add(outcome.getOutcomeId());
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
                } else {
                    outcome.setResult("L");
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
                }
            }
        } else if (homeTeamScore < awayTeamScore) {
            for (OutcomeResponse outcome : market.getOutcomes()) {
                if (!outcome.getOutcomeCode().equals("1X")) {
                    outcome.setResult("W");
                    winningOutcomeIds.add(outcome.getOutcomeId());
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
                } else {
                    outcome.setResult("L");
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
                }
            }
        } else if (homeTeamScore == awayTeamScore) {
            for (OutcomeResponse outcome : market.getOutcomes()) {
                if (!outcome.getOutcomeCode().equals("12")) {
                    outcome.setResult("W");
                    winningOutcomeIds.add(outcome.getOutcomeId());
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
                } else {
                    outcome.setResult("L");
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
                }
            }
        }
        return winningOutcomeIds;
    }

    private List<Long> resultOU25MarketType(Workspace workspace, MarketResponse market, long homeTeamScore,
            long awayTeamScore) throws SQLException, IOException {
        List<Long> winningOutcomeIds = new ArrayList<Long>();
        if (market.getOutcomes() == null) {
            return null;
        }
        if (homeTeamScore + awayTeamScore > 2.5) {
            for (OutcomeResponse outcome : market.getOutcomes()) {
                if (outcome.getOutcomeCode().equals("O")) {
                    outcome.setResult("W");
                    winningOutcomeIds.add(outcome.getOutcomeId());
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
                } else {
                    outcome.setResult("L");
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
                }
            }
        } else {
            for (OutcomeResponse outcome : market.getOutcomes()) {
                if (outcome.getOutcomeCode().equals("U")) {
                    outcome.setResult("W");
                    winningOutcomeIds.add(outcome.getOutcomeId());
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
                } else {
                    outcome.setResult("L");
                    outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
                }
            }
        }
        return winningOutcomeIds;
    }

    private List<Long> resultCorrectScoreMarketType(Workspace workspace, MarketResponse market, long homeTeamScore,
            long awayTeamScore) throws SQLException, IOException {
        List<Long> winningOutcomeIds = new ArrayList<Long>();
        if (market.getOutcomes() == null) {
            return null;
        }
        String[] possibleOutcomeCodes = new String[] { "10", "20", "30", "01", "02", "03", "00", "11", "22" };
        List<String> possibleOutcomeCodeList = Arrays.asList(possibleOutcomeCodes);

        String winningOutcomeCode = Long.toString(homeTeamScore) + Long.toString(awayTeamScore);

        if (!possibleOutcomeCodeList.contains(winningOutcomeCode)) {
            winningOutcomeCode = "OTH";
        }

        for (OutcomeResponse outcome : market.getOutcomes()) {
            if (outcome.getOutcomeCode().compareTo(winningOutcomeCode) == 0) {
                outcome.setResult("W");
                winningOutcomeIds.add(outcome.getOutcomeId());
                outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "W");
            } else {
                outcome.setResult("L");
                outcomeDao.updateOutcomeResult(workspace, outcome.getOutcomeId(), "L");
            }
        }

        return winningOutcomeIds;
    }

}
