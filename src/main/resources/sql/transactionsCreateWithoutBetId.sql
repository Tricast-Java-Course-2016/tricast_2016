INSERT INTO 
	/*=SCHEMA*/TRANSACTIONS(
    	id,
    	accountid, 
		createdDate,
		description,
		amount
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_TRANSACTIONS'),
		 :accountid,
		 :createdDate,
		 :description,
		 :amount
		)