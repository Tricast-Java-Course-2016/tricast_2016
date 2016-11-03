UPDATE 
	/*=SCHEMA*/PERIODS
SET 
    	eventId = :eventId,
    	periodTypeId = :periodTypeId,
    	description = :description,
    	homeTeamScore = :homeTeamScore,
    	awayTeamScore = :awayTeamScore
WHERE 
	id = :id