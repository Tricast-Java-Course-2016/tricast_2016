SELECT 
	id,
    accountTypeId,
    username, 
    firstName, 
    lastName,
    DOB,
    address,
    emailAddress,
    phoneNumber,
    PIN,
    bankAccountNumber,
    bankCardNumber,
    createdDate
FROM 
	/*=SCHEMA*/ACCOUNTS 
WHERE 
	USERNAME = :userName
	AND PASSWORD = :password