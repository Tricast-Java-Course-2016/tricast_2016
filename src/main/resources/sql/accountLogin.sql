SELECT 
	id,
    accountTypeId,
    username, 
    firstName, 
    lastName,
    dob,
    address,
    emailAddress,
    phoneNumber,
    pin,
    bankAccountNumber,
    bankCardNumber,
    createdDate
FROM 
	/*=SCHEMA*/ACCOUNTS 
WHERE 
	USERNAME = :userName
	AND PASSWORD = :password