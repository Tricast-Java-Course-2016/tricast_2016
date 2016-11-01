package com.tricast.web.request;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionRequest {

    private String password;
    private String bankAccountNumber;
    private String bankCardNumber;
    private BigDecimal amount;
    private Date cardExpiration;
    private String accountHolderName;

    private long accountId;

}
