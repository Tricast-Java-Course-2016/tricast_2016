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
		 :createddate,
		 :description,
		 :amount
		)