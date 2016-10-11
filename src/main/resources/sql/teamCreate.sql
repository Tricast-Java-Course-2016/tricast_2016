INSERT INTO 
	/*=SCHEMA*/TEAM(
    	id,
    	description
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_TEAM'),
		 :teamDescription
		)