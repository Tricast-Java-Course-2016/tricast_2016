UPDATE 
	/*=SCHEMA*/TRANSACTIONS
SET 
    accountid = :accountid,
    betid = :betid,
	createddate = :createddate,
	description = :description,
	amount = :amount
WHERE 
	ID = :id
