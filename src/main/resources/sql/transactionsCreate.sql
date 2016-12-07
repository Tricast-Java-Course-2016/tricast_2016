INSERT INTO 
	/*=SCHEMA*/TRANSACTIONS(
    	id,
    	accountid, 
    	betid,
		createddate,
		description,
		amount
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_TRANSACTIONS'),
		 :accountid,
		 :betid,
		 CURRENT_TIMESTAMP,
		 :description,
		 :amount
		)