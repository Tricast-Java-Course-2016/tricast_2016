UPDATE 
	/*=SCHEMA*/ACCOUNTS 
SET 
    password = :password, 
    firstName = :firstName, 
    lastName = :lastName,
    DOB = :DOB,
    address = :address,
    emailAddress = :emailAddress, 
    phoneNumber = :phoneNumber,
    PIN = :PIN,
    bankAccountNumber = :bankAccountNumber,
    bankCardNumber = :bankCardNumber,
WHERE 
	ID = :id
