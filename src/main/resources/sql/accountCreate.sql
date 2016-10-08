INSERT INTO 
	/*=SCHEMA*/ACCOUNTS(
    	accountTypeId,
    	username, 
    	password, 
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
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_ACCOUNTS')
		, :accountTypeId
		, :userName
		, :password
		, :firstName
		, :lastName
		, :DOB
		, :address
		, :emailAddress
		, :phoneNumber
		, :PIN
		, :bankAccountNumber
		, :bankCardNumber
		, :createdDate
		)