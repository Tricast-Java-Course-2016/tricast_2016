INSERT INTO 
	/*=SCHEMA*/PERIOD(
    	id,
    	eventId,
    	periodTypeId,
    	description,
    	homeTeamScore,
    	awayTeamScore
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_PERIODTYPE'),
		 :eventid,
		 :peridtypeid,
		 :description,
		 :hometeamscore,
		 :awayteamscore
		)