UPDATE 
	/*=SCHEMA*/OUTCOMES 
SET 
    marketid = :marketid,
    outcomecode = :outcomecode,
    description = :description,
    odds = :odds,
    result = :result,
WHERE 
	id = :id