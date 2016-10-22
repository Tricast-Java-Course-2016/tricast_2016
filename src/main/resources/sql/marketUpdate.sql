UPDATE 
	/*=SCHEMA*/MARKETS
SET 
	id= :id,
	periodId = :periodId,
	marketTypeId = :marketTypeId,
	description = :description
WHERE 
	id = :id