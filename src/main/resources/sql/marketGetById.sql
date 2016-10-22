SELECT
	id,
	periodId,
	marketTypeId,
	description
FROM 
	/*=SCHEMA*/MARKETS 
WHERE 
	ID = :id