INSERT INTO 
	/*=SCHEMA*/LEAGUES(
    	id,
    	description
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_LEAGUES'),
		 :description
		)