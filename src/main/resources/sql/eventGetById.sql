SELECT  
		id,
    	leagueId,
    	countryId,
    	homeTeamId,
    	awayTeamId,
    	description,
    	status
FROM
	/*=SCHEMA*/EVENT
WHERE
	ID = :id