INSERT INTO 
	/*=SCHEMA*/PERIODS(
    	id,
    	eventId,
    	periodTypeId,
    	description,
    	homeTeamScore,
    	awayTeamScore
    	)
VALUES (
		  NEXTVAL('/*=SCHEMA*/SEQ_PERIODS'),
		 :eventId,
		 :periodTypeId,
		 :description,
		 :homeTeamScore,
		 :awayTeamScore
		)