package com.sasajankovic.domain.entities;

import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;

@Getter
public class Money implements Comparable<Money> {
    public static final Money ZERO = Money.create(BigDecimal.ZERO);
    private BigDecimal amount;
    private String currency;

    private Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money create(@NonNull BigDecimal amount) {
        return new Money(
                amount, "$"); // use hardcoded currency as multi currency is not a requirement
    }

    public Money add(Money money) {
        if (!money.currency.equals(currency))
            throw new UnsupportedOperationException(
                    "Adding money with different currencies is not supported");

        return new Money(money.getAmount().add(amount), currency);
    }

    public BigDecimal get() {
        return amount;
    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Money)) return false;
        Money money = (Money) obj;
        return amount.equals(money.getAmount()) && currency.equals(money.getCurrency());
    }

    @Override
    public String toString() {
        return amount.toString().concat(currency);
    }

    @Override
    public int compareTo(Money money) {
        if (!currency.equals(money.getCurrency()))
            throw new UnsupportedOperationException(
                    "Comparing money with different currencies is not supported");

        return amount.compareTo(money.getAmount());
    }
}
