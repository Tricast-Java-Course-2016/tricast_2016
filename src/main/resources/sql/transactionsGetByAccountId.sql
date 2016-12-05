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
	accountid = :accountid