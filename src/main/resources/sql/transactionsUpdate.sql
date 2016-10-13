UPDATE 
	/*=SCHEMA*/TRANSACTIONS
SET 
    accountid = :accountid,
    betid = :betid,
	createdDate = :createdDate,
	description = :description,
	amount = :amount
WHERE 
	ID = :id
