UPDATE 
	/*=SCHEMA*/BETDATA
SET 
    odds = :odds
WHERE 
	betid = :betid,
	outcomeid = :outcomeid