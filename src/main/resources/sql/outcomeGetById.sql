SELECT
	id
    marketid,
    outcomecode,
    description,
    odds,
    result
FROM 
	/*=SCHEMA*/OUTCOMES 
WHERE 
	ID = :id