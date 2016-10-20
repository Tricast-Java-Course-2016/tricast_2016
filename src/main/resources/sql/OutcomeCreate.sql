INSERT INTO 
	/*=SCHEMA*/OUTCOMES(
	id,
    marketid,
    outcomecode,
    description,
    odds,
    result
	) 
VALUES 
	(
		NEXTVAL('/*=SCHEMA*/SEQ_OUTCOMES')
     , :marketid
     , :outcomecode
     , :description
     , :odds
     , :result
	)
