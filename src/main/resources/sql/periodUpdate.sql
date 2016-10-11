UPDATE 
	/*=SCHEMA*/PERIOD
SET 
    	eventId = :eventid,
    	periodTypeId = :peridtype,
    	description = :description,
    	homeTeamScore = :hometeamscore,
    	awayTeamScore = :awayteamscore
WHERE 
	ID = :id