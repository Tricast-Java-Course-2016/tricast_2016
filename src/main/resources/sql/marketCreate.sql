INSERT INTO 
	/*=SCHEMA*/MARKETS(
	id,
	periodId,
	marketTypeId,
	description,
	eventid
	) 
VALUES 
	(
		NEXTVAL('/*=SCHEMA*/SEQ_MARKETS')
     , :periodId
     , :marketTypeId
     , :description
     , :eventid
	)
