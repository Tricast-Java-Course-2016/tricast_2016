INSERT INTO 
	/*=SCHEMA*/COUNTRIES(
    	id,
    	description
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_COUNTRIES'),
		 :description
		)