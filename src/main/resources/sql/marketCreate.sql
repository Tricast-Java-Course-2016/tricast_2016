INSERT INTO 
	/*=SCHEMA*/MARKETS(
	id,
	periodId,
	marketTypeId,
	description
	) 
VALUES 
	(
		NEXTVAL('/*=SCHEMA*/SEQ_MARKETS')
     , :id
     , :periodId
     , :marketTypeId
     , :description
	)
