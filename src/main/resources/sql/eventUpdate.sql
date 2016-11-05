UPDATE 
	/*=SCHEMA*/EVENTS
SET 
    	leagueId = :leagueId,
    	countryId = :countryId,
    	homeTeamId = :homeTeamId,
    	awayTeamId = :awayTeamId,
    	description = :description,
    	status = :status
WHERE 
	ID = :id
