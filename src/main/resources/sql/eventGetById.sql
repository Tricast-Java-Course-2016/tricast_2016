SELECT  
		id,
    	leagueId,
    	countryId,
    	homeTeamId,
    	awayTeamId,
    	description,
    	status,
    	starttime
FROM
	/*=SCHEMA*/EVENTS
WHERE
	ID = :id