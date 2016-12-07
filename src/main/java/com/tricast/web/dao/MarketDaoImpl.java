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

import com.tricast.beans.Market;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.response.MarketResponse;

public class MarketDaoImpl implements MarketDao {
	 private static final Logger log = LogManager.getLogger(MarketDaoImpl.class);
	 private static final SqlManager sqlManager = SqlManager.getInstance();
	
	@Override
	@JdbcTransaction
	public List<Market> getAll(Workspace workspace) throws SQLException, IOException {
		List<Market> result = new ArrayList<Market>();
		
		String sql = sqlManager.get("marketGetAll.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(buildMarket(rs));
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        }
        return result;
	}

	

	@Override
	@JdbcTransaction
	public Market getById(Workspace workspace, long id) throws SQLException, IOException {
		Market result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("marketGetById.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = buildMarket(rs);
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
	public Long create(Workspace workspace, Market newItem) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("marketCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, newItem.getId());
            ps.setLong(i++, newItem.getPeriodId());
            ps.setLong(i++, newItem.getMarketTypeId());
            ps.setString(i++, newItem.getDescription());
            
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
	public Long update(Workspace workspace, Market updateItem) throws SQLException, IOException {
		
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("marketUpdate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, updateItem.getId());
            ps.setLong(i++, updateItem.getPeriodId());
            ps.setLong(i++, updateItem.getMarketTypeId());
            ps.setString(i++, updateItem.getDescription());
            
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

	        String sql = sqlManager.get("marketDelete.sql");

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
	
	private Market buildMarket(ResultSet rs) throws SQLException {
        Market market = new Market();
        int i = 1;
        market.setId(rs.getLong(i++));
        market.setPeriodId(rs.getLong(i++));
        market.setMarketTypeId(rs.getLong(i++));
        market.setDescription(rs.getString(i++));

        return market;
    }
	
	@Override
	@JdbcTransaction
	public List<MarketResponse> getDetailsByEventId(Workspace workspace, long eventId) throws SQLException, IOException {
		List<MarketResponse> result = new ArrayList<MarketResponse>();
		ResultSet rs = null;
		
		String sql = sqlManager.get("marketGetDetailsByEventId.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
            ps.setLong(1, eventId);
            rs = ps.executeQuery();

            while (rs.next()) {
            	
            	MarketResponse market = new MarketResponse();
                int i = 1;
                market.setMarketId(rs.getLong(i++));
                market.setPeriodId(rs.getLong(i++));
                market.setPeriodDescription(rs.getString(i++));
                market.setEventId(rs.getLong(i++));
                market.setMarketTypeId(rs.getLong(i++));
                market.setMarketTypeDescription(rs.getString(i++));           	
            	
                result.add(market);
            }

        } catch (SQLException ex) {
            log.error(ex, ex);
            throw ex;
        }
        return result;
	}

	

}
