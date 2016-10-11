INSERT INTO 
	/*=SCHEMA*/LEAGUE(
    	id,
    	description
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_LEAGUE'),
		 :teamDescription
		)