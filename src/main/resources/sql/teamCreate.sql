INSERT INTO 
	/*=SCHEMA*/TEAMS(
    	id,
    	description
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_TEAMS'),
		 :teamDescription
		)