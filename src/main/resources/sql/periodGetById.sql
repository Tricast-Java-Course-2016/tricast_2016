SELECT
	id,
    eventId,
    periodTypeId,
    description,
    homeTeamScore,
    awayTeamScore
FROM 
	/*=SCHEMA*/PERIODS 
WHERE 
	ID = :id