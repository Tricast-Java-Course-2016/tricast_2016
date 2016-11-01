package com.tricast.web.response;

import java.sql.Date;

public class TransactionHistoryResponse {
    private long accountId;
    private long transactionId;
    private long transactionBetid;
    private Date transactionCreatedDate;
    private String transactionDescription;
    private double transactionAmount;
}
