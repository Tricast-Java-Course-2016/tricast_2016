UPDATE 
	/*=SCHEMA*/BETDATA
SET 
    odds = :odds
WHERE 
	betid = :betid AND
	outcomeid = :outcomeid