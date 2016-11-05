INSERT INTO 
	/*=SCHEMA*/EVENTS(
    	id,
    	leagueId,
    	countryId,
    	homeTeamId,
    	awayTeamId,
    	description,
    	status
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_EVENTS'),
    	 :leagueId,
    	 :countryId,
    	 :homeTeamId,
    	 :awayTeamId,
    	 :description,
    	 :status
		)