SELECT
	id,
	description,
	outcomecode,
	odds
FROM 
	/*=SCHEMA*/OUTCOMES
WHERE 
	marketid = :id