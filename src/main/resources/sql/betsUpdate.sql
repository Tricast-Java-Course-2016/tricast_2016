UPDATE 
	/*=SCHEMA*/BETS
SET 
    accountid = :accountid,
    bettypeid = :bettypeid
WHERE 
	ID = :id
