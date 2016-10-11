INSERT INTO 
	/*=SCHEMA*/EVENT(
    	id,
    	leagueId,
    	countryId,
    	homeTeamId,
    	awayTeamId,
    	description,
    	status
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_EVENT'),
		 :id,
    	 :leagueId,
    	 :countryId,
    	 :homeTeamId,
    	 :awayTeamId,
    	 :description,
    	 :status
		)