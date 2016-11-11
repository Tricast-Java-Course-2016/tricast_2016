INSERT INTO 
	/*=SCHEMA*/ACCOUNTS(
		id,
    	accountTypeId,
    	username, 
    	password, 
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
    	)
VALUES (
		NEXTVAL('/*=SCHEMA*/SEQ_ACCOUNTS')
		, :accountTypeId
		, :userName
		, :password
		, :firstName
		, :lastName
		, :dob
		, :address
		, :emailAddress
		, :phoneNumber
		, :pin
		, :bankAccountNumber
		, :bankCardNumber
		, CURRENT_TIMESTAMP
		)