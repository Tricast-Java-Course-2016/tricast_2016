SELECT  
		id,
    	leagueId,
    	countryId,
    	homeTeamId,
    	awayTeamId,
    	description,
    	status
FROM
	/*=SCHEMA*/EVENTS
WHERE
	ID = :id