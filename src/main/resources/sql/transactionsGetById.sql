SELECT  
    	id,
    	accountid, 
    	betid,
		createdDate,
		description,
		amount
FROM
	/*=SCHEMA*/TRANSACTIONS
WHERE 
	id = :id