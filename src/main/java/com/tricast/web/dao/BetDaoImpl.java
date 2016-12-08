package com.tricast.web.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tricast.beans.Bet;
import com.tricast.beans.BetForSettlement;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;

public class BetDaoImpl implements BetDao {

    private static final Logger log = LogManager.getLogger(BetDaoImpl.class);
    private static final SqlManager sqlManager = SqlManager.getInstance();

    @Override
    @JdbcTransaction
    public List<Bet> getAll(Workspace workspace) throws SQLException, IOException {

        List<Bet> result = new ArrayList<Bet>();

        String sql = sqlManager.get("betsGetAll.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(buildBet(rs));
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public List<BetForSettlement> getBetsForSettlement(Workspace workspace, long outcomeId)
            throws SQLException, IOException {

        List<BetForSettlement> result = new ArrayList<BetForSettlement>();
        ResultSet rs = null;

        String sql = sqlManager.get("betsGetForSettlement.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {

            ps.setLong(1, outcomeId);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(buildBetForSettlement(rs));
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public Bet getById(Workspace workspace, long id) throws SQLException, IOException {

        Bet result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("betsGetById.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = buildBet(rs);
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public Long create(Workspace workspace, Bet newItem) throws SQLException, IOException {

        Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("betsCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, newItem.getAccountId());
            ps.setLong(i++, newItem.getBetTypeId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public Long update(Workspace workspace, Bet updateItem) throws SQLException, IOException {

        Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("betsUpdate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setLong(i++, updateItem.getAccountId());
            ps.setLong(i++, updateItem.getBetTypeId());
            ps.setLong(i++, updateItem.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public boolean deleteById(Workspace workspace, long betId) throws SQLException, IOException {

        boolean result = false;

        String sql = sqlManager.get("betsDelete.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {

            int i = 1;
            ps.setLong(i++, betId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = true;
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        }
        return result;
    }

    private Bet buildBet(ResultSet rs) throws SQLException {
        Bet bet = new Bet();
        int i = 1;
        bet.setId(rs.getLong(i++));
        bet.setAccountId(rs.getLong(i++));
        bet.setBetTypeId(rs.getLong(i++));
        return bet;
    }

    private BetForSettlement buildBetForSettlement(ResultSet rs) throws SQLException {
        BetForSettlement bet = new BetForSettlement();
        int i = 1;
        bet.setBetId(rs.getLong(i++));
        bet.setAccountId(rs.getLong(i++));
        bet.setAmount(rs.getDouble(i++));
        bet.setOdds(rs.getDouble(i++));
        return bet;
    }
}
