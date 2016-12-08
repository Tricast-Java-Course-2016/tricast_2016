SELECT 	
	m.id,
	m.periodId,
	m.marketTypeId,
	m.eventid,
	o.id,
	o.outcomecode,
	o.odds
FROM
	/*=SCHEMA*/MARKETS M
	INNER JOIN /*=SCHEMA*/OUTCOMES O ON (O.MARKETID = M.ID) 
WHERE
	m.periodId = :periodId