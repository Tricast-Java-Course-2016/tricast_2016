UPDATE 
	/*=SCHEMA*/EVENTS
SET 
    	leagueId = :leagueId,
    	countryId = :countryId,
    	homeTeamId = :homeTeamId,
    	awayTeamId = :awayTeamId,
    	description = :description,
    	status = :status,
    	starttime = :starttime
WHERE 
	ID = :id
