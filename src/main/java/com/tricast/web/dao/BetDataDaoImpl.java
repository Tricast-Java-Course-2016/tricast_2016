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

import com.tricast.beans.BetData;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;

public class BetDataDaoImpl implements BetDataDao {

    private static final Logger log = LogManager.getLogger(BetDataDaoImpl.class);
    private static final SqlManager sqlManager = SqlManager.getInstance();

    @Override
    @JdbcTransaction
    public List<BetData> getAll(Workspace workspace) throws SQLException, IOException {

        List<BetData> result = new ArrayList<BetData>();

        String sql = sqlManager.get("betDataGetAll.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(buildBetData(rs));
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        }
        return result;
    }

    private BetData buildBetData(ResultSet rs) throws SQLException {
        BetData betData = new BetData();
        int i = 1;
        betData.setBetId(rs.getLong(i++));
        betData.setOutcomeId(rs.getLong(i++));
        betData.setOdds(rs.getLong(i++));
        return betData;
    }

    @Override
    @JdbcTransaction
    public BetData getById(Workspace workspace, long betId, long outcomeId) throws SQLException, IOException {

        BetData result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("betDataGetById.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
            int i = 1;
            ps.setLong(i++, betId);
            ps.setLong(i++, outcomeId);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = buildBetData(rs);
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
    public Long[] create(Workspace workspace, BetData newItem) throws SQLException, IOException {

        ResultSet rs = null;
        Long[] result = null;

        String sql = sqlManager.get("betDataCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, newItem.getBetId());
            ps.setLong(i++, newItem.getOutcomeId());
            ps.setDouble(i++, newItem.getOdds());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = new Long[2];
                    result[0] = rs.getLong(1);
                    result[1] = rs.getLong(2);
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
    public Long[] update(Workspace workspace, BetData updateItem) throws SQLException, IOException {

        Long[] result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("betDataUpdate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setDouble(i++, updateItem.getOdds());
            ps.setLong(i++, updateItem.getBetId());
            ps.setLong(i++, updateItem.getOutcomeId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = new Long[2];
                    result[0] = rs.getLong(1);
                    result[1] = rs.getLong(2);
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
    public boolean deleteById(Workspace workspace, long betId, long outcomeId) throws SQLException, IOException {

        boolean result = false;

        String sql = sqlManager.get("betDataDelete.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {

            int i = 1;
            ps.setLong(i++, betId);
            ps.setLong(i++, outcomeId);
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

}
