SELECT  
    	betid,
    	outcomeid, 
    	odds
FROM
	/*=SCHEMA*/BETDATA
WHERE 
	betid = :betid AND
	outcomeid = :outcomeid