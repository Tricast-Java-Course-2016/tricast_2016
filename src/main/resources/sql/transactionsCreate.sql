INSERT INTO 
	/*=SCHEMA*/TRANSACTIONS(
    	id,
    	accountid, 
    	betid,
		createdDate,
		description,
		amount
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_TRANSACTIONS'),
		 :accountid,
		 :betid,
		 :createdDate,
		 :description,
		 :amount
		)