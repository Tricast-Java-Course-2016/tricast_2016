UPDATE 
	/*=SCHEMA*/ACCOUNTS 
SET 
    password = :password, 
    firstName = :firstName, 
    lastName = :lastName,
    dob = :dob,
    address = :address,
    emailAddress = :emailAddress, 
    phoneNumber = :phoneNumber,
    pin = :pin,
    bankAccountNumber = :bankAccountNumber,
    bankCardNumber = :bankCardNumber
WHERE 
	ID = :id
