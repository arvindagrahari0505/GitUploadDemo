package com.capgemini.payment.beans;

import java.math.BigDecimal;

public class Wallet {
	
	private String walletId;
	private BigDecimal balance;

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((walletId == null) ? 0 : walletId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wallet other = (Wallet) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (walletId == null) {
			if (other.walletId != null)
				return false;
		} else if (!walletId.equals(other.walletId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Wallet balance : " + balance;
	}

}
