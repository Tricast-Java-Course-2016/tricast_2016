package com.tricast.beans;

public class BetForSettlement {

	private long betId;
    private long accountId;
    private double amount;
	private double odds;

    public long getBetId() {
        return betId;
    }

    public void setBetId(long betId) {
        this.betId = betId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (accountId ^ (accountId >>> 32));
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (int) (betId ^ (betId >>> 32));
        temp = Double.doubleToLongBits(odds);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BetForSettlement other = (BetForSettlement) obj;
        if (accountId != other.accountId) {
            return false;
        }
        if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        if (betId != other.betId) {
            return false;
        }
        if (Double.doubleToLongBits(odds) != Double.doubleToLongBits(other.odds)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BetForSettlement [betId=" + betId + ", accountId=" + accountId + ", amount=" + amount + ", odds=" + odds
                + "]";
    }


}
