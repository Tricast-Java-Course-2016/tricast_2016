package com.tricast.web.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.tricast.beans.Event;
import com.tricast.database.SqlManager;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;

public class EventDaoImpl implements EventDao {

	private static final SqlManager sqlManager = SqlManager.getInstance();
	private static final org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getLogger(EventDao.class);


	@Override
	public List<Event> getAll(Workspace workspace) throws SQLException, IOException {
		List<Event> result = new ArrayList<Event>();

		String sql = sqlManager.get("eventGetAll.sql");
		try (PreparedStatement ps = workspace.getPreparedStatement(sql);ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				result.add(buildEvent(rs));
			}
		} catch(SQLException ex) {
	    	logger.error(ex,ex);
	    	throw ex;
	    }
	    return result;
	}

	private Event buildEvent(ResultSet rs) throws SQLException {
		Event event = new Event();
		int i = 1;

		event.setId(rs.getLong(i++));
		event.setLeagueId(rs.getLong(i++));
		event.setCountryId(rs.getLong(i++));
		event.setHomeTeamId(rs.getLong(i++));
		event.setAwayTeamId(rs.getLong(i++));
		event.setDescription(rs.getString(i++));
		event.setStatus(rs.getString(i++));
        Timestamp timestamp = rs.getTimestamp(i++);
        event.setStartTime(new Date(timestamp.getTime()));
		return event;
	}

	@Override
	@JdbcTransaction
	public Event getById(Workspace workspace, long id) throws SQLException, IOException {
		Event result = null;
		ResultSet rs = null;

		String sql = sqlManager.get("eventGetById.sql");

		try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {
			   ps.setLong(1, id);
	            rs = ps.executeQuery();

	            if (rs.next()) {
	                result = buildEvent(rs);
	            }

	        } catch (SQLException ex) {
	            logger.error(ex, ex);
	            throw ex;
	        } finally {
	            rs.close();
	        }
	        return result;
	    }


	@Override
	@JdbcTransaction
	public Long create(Workspace workspace, Event newItem) throws SQLException, IOException {
		Long result = null;
        ResultSet rs = null;

        String sql = sqlManager.get("eventCreate.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setLong(i++, newItem.getLeagueId());
            ps.setLong(i++, newItem.getCountryId());
            ps.setLong(i++, newItem.getHomeTeamId());
            ps.setLong(i++, newItem.getAwayTeamId());
            ps.setString(i++, newItem.getDescription());
            ps.setString(i++, newItem.getStatus());
            ps.setTimestamp(i++, new Timestamp(newItem.getStartTime().getTime()));
            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getLong(1);
                }
            }

        } catch (SQLException ex) {
            logger.error(ex, ex);
            throw ex;
        } finally {
            rs.close();
        }
        return result;
	}

	@Override
	@JdbcTransaction
	public Long update(Workspace workspace, Event updateItem) throws SQLException, IOException {

		 Long result = null;
	        ResultSet rs = null;

	        String sql = sqlManager.get("eventUpdate.sql");

	        try (PreparedStatement ps = workspace.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            int i = 1;
	            ps.setLong(i++, updateItem.getLeagueId());
	            ps.setLong(i++, updateItem.getCountryId());
	            ps.setLong(i++, updateItem.getHomeTeamId());
	            ps.setLong(i++, updateItem.getAwayTeamId());
	            ps.setString(i++,updateItem.getDescription());
	            ps.setString(i++,updateItem.getStatus());
	            ps.setLong(i++, updateItem.getId());
            ps.setTimestamp(i++, new Timestamp(updateItem.getStartTime().getTime()));
	            int rows = ps.executeUpdate();
	            if (rows > 0) {
	                rs = ps.getGeneratedKeys();
	                if (rs.next()) {
	                    result = rs.getLong(1);
	                }
	            }
	        } catch (SQLException ex) {
	            logger.error(ex, ex);
	            throw ex;
	        } finally {
	            rs.close();
	        }
	        return result;
	}

	@Override
	@JdbcTransaction
	public boolean deleteById(Workspace workspace, long id) throws SQLException, IOException {
		boolean result = false;

        String sql = sqlManager.get("eventDelete.sql");

        try (PreparedStatement ps = workspace.getPreparedStatement(sql)) {

            int i = 1;
            ps.setLong(i++, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = true;
            }

        } catch (SQLException ex) {
            logger.error(ex, ex);
            throw ex;
        }
        return result;
	}

}
