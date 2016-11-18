package com.tricast.web.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.inject.Inject;
import com.tricast.beans.Country;
import com.tricast.beans.Event;
import com.tricast.beans.League;
import com.tricast.beans.Period;
import com.tricast.beans.Team;
import com.tricast.database.Workspace;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.CountryDao;
import com.tricast.web.dao.EventDao;
import com.tricast.web.dao.LeagueDao;
import com.tricast.web.dao.PeriodDao;
import com.tricast.web.dao.TeamDao;
import com.tricast.web.response.EventResponse;

public class EventManagerImpl implements EventManager {

	private final EventDao eventDao;
	private final LeagueDao leagueDao;
	private final CountryDao countryDao;
	private final TeamDao teamDao;
	private final PeriodDao periodDao;
    private long i;

	@Inject
	public EventManagerImpl(EventDao eventDao, LeagueDao leagueDao, CountryDao countryDao, TeamDao teamDao, PeriodDao periodDao) {
		this.eventDao = eventDao;
		this.leagueDao = leagueDao;
		this.countryDao = countryDao;
		this.teamDao = teamDao;
		this.periodDao = periodDao;
	}

	@Override
    @JdbcTransaction
	public List<EventResponse> getAll(Workspace workspace) throws SQLException, IOException {

		List<League> leagues = leagueDao.getAll(workspace);
		List<Country> countries = countryDao.getAll(workspace);
		List<Team> teams = teamDao.getAll(workspace);
		List<Event> events = eventDao.getAll(workspace);
		List<Period> periods = periodDao.getAll(workspace);

        HashMap<Long, String> resultMap = new HashMap<Long, String>();
        for (Period period : periods) {
            resultMap.put(period.getId(), period.getPeriodResult());
        }

        i = 1;
        HashMap<Long, Long> periodIdMap = new HashMap<Long, Long>();
        for (Period period : periods) {
            periodIdMap.put(i, period.getId());
            i++;
        }

		//convert Lists to HashMaps for easier id-description access
		HashMap<Long, String> periodMap = new HashMap<Long, String>();
		for (Period period : periods) {
            periodMap.put(period.getId(), period.getPeriodTypeDescription());
            // System.out.println(periodMap.get(period.getId()));
		}

		HashMap<Long, String> leaguesMap = new HashMap<Long, String>();
		for (League league : leagues) {
			leaguesMap.put(league.getId(), league.getDescription());
		}

		HashMap<Long, String> countriesMap = new HashMap<Long, String>();
		for (Country country : countries) {
			countriesMap.put(country.getId(), country.getDescription());
		}

		HashMap<Long, String> teamsMap = new HashMap<Long, String>();
		for (Team team : teams) {
			teamsMap.put(team.getId(), team.getDescription());
		}

		HashMap<Long, String> eventsMap = new HashMap<Long, String>();
		for (Event event : events) {
			eventsMap.put(event.getId(), event.getDescription());
		}

		List<EventResponse> responses = new ArrayList<EventResponse>();

		for (Event event : events) {
            for (Period period : periods) {
                if (period.getEventId() == event.getId()) {
                    EventResponse newResponse = new EventResponse();
                    newResponse.setId(event.getId());
                    newResponse.setLeague(leaguesMap.get(event.getLeagueId()));
                    newResponse.setCountry(countriesMap.get(event.getCountryId()));
                    newResponse.setHomeTeam(teamsMap.get(event.getHomeTeamId()));
                    newResponse.setAwayTeam(teamsMap.get(event.getAwayTeamId()));
                    newResponse.setDescription(event.getDescription());
                    newResponse.setPeriod(periodMap.get(period.getId()));
                    newResponse.setStatus(event.getStatus());
                    newResponse.setPeriodId(period.getId());
                    newResponse.setResult(resultMap.get(period.getId()));
                    newResponse.setStartTime(event.getStartTime());
                    responses.add(newResponse);
                }
            }
		}
		return responses;
	}


	@Override
    @JdbcTransaction
	public List<EventResponse> getAllOpen(Workspace workspace) throws SQLException, IOException {

	List<League> leagues = leagueDao.getAll(workspace);
	List<Country> countries = countryDao.getAll(workspace);
	List<Team> teams = teamDao.getAll(workspace);
	List<Event> events = eventDao.getAll(workspace);

	//convert Lists to HashMaps for easier id-description access
	HashMap<Long, String> leaguesMap = new HashMap<Long, String>();
	for (League league : leagues) {
		leaguesMap.put(league.getId(), league.getDescription());
	}

	HashMap<Long, String> countriesMap = new HashMap<Long, String>();
	for (Country country : countries) {
		countriesMap.put(country.getId(), country.getDescription());
	}

	HashMap<Long, String> teamsMap = new HashMap<Long, String>();
	for (Team team : teams) {
		teamsMap.put(team.getId(), team.getDescription());
	}

	HashMap<Long, String> eventsMap = new HashMap<Long, String>();
	for (Event event : events) {
		eventsMap.put(event.getId(), event.getDescription());
	}

	List<EventResponse> responses = new ArrayList<EventResponse>();

	for (Event event : events) {

		if(event.getStatus().trim().compareTo("OPEN") == 0){
			EventResponse newResponse = new EventResponse();
			newResponse.setId(event.getId());
			newResponse.setLeague(leaguesMap.get(event.getLeagueId()));
			newResponse.setCountry(countriesMap.get(event.getCountryId()));
			newResponse.setHomeTeam(teamsMap.get(event.getHomeTeamId()));
			newResponse.setAwayTeam(teamsMap.get(event.getAwayTeamId()));
			newResponse.setDescription(event.getDescription());
			newResponse.setStatus(event.getStatus());
			newResponse.setStartTime(event.getStartTime());
			responses.add(newResponse);
		}

	}

	return responses;
	}

	@Override
    @JdbcTransaction
	public Event getById(Workspace workspace, long id) throws SQLException, IOException {
		Event event =  eventDao.getById(workspace, id);
		return event;
	}

	@JdbcTransaction
	@Override
	public Event create(Workspace workspace, Event newEvent) throws SQLException, IOException {
		Long id = eventDao.create(workspace, newEvent);
		if (id != null) {
			return eventDao.getById(workspace, id);
		} else {
			return null;
		}
	}

	@JdbcTransaction
	@Override
	public Event update(Workspace workspace, Event updateEvent) throws SQLException, IOException {
		Long id = eventDao.update(workspace, updateEvent);
        if (id != null) {
            return eventDao.getById(workspace, id);
        } else {
            return null;
        }
	}

	@JdbcTransaction
	@Override
	public boolean deleteById(Workspace workspace, long Id) throws SQLException, IOException {
		return eventDao.deleteById(workspace, Id);
	}

}
