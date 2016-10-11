UPDATE 
	/*=SCHEMA*/LEAGUE
SET 
    	leagueId = :leagueid,
    	countryId = :countryid,
    	homeTeamId = :hometeamid,
    	awayTeamId = :awayteamid,
    	description = :description,
    	status = :status
WHERE 
	ID = :id
