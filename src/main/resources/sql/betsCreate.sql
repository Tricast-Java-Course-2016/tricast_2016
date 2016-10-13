INSERT INTO 
	/*=SCHEMA*/BETS(
    	id,
    	accountid, 
    	bettypeid
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_BETS'),
		 :accountid,
		 :bettypeid
		)