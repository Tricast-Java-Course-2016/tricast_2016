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

import com.tricast.beans.Outcome;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;

public class OutcomeDaoImpl implements OutcomeDao {
    private static final Logger log = LogManager.getLogger(AccountDaoImpl.class);
    private static final SqlManager sqlManager = SqlManager.getInstance();

    @Override
    public List<Outcome> getAll(Workspace workspace) throws SQLException, IOException {
        List<Outcome> result = new ArrayList<Outcome>();

        String sql = sqlManager.get("OutcomeGetAll.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(buildOutcome(rs));
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        }
        return result;
    }

    @Override
    @JdbcTransaction
    public Outcome getById(Workspace workspace, long id) throws SQLException, IOException {
        Outcome result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("outcomeGetById.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = buildOutcome(rs);
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
    public Long create(Workspace workspace, Outcome newItem) throws SQLException, IOException {
        Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("OutcomeCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, newItem.getMarketid());
            ps.setString(i++, newItem.getOutcomecode());
            ps.setString(i++, newItem.getDescription());
            ps.setDouble(i++, newItem.getOdds());
            ps.setString(i++, newItem.getResult());
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
    public Long update(Workspace workspace, Outcome updateItem) throws SQLException, IOException {

        Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("outcomeupdate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setLong(i++, updateItem.getMarketid());
            ps.setString(i++, updateItem.getOutcomecode());
            ps.setString(i++, updateItem.getDescription());
            ps.setDouble(i++, updateItem.getOdds());
            ps.setString(i++, String.valueOf(updateItem.getResult()));
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
    public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
        boolean result = false;

        String sql = sqlManager.get("outcomedelete.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {

            int i = 1;
            ps.setLong(i++, Id);

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

    private Outcome buildOutcome(ResultSet rs) throws SQLException {
        Outcome outcome = new Outcome();
        int i = 1;
        outcome.setId(rs.getLong(i++));
        outcome.setMarketid(rs.getLong(i++));
        outcome.setOutcomecode(rs.getString(i++));
        outcome.setDescription(rs.getString(i++));
        outcome.setOdds(rs.getDouble(i++));
        String result = rs.getString(i++);
        if (result != null && result.length() > 0) {
            outcome.setResult(result);
        }
        return outcome;
    }

}
