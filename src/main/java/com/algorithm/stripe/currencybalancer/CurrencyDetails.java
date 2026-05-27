package com.algorithm.stripe.currencybalancer;

public class CurrencyDetails {
    private CurrencyName name;
    private int value;

    public CurrencyDetails(CurrencyName name, int value) {
        this.name = name;
        this.value = value;
    }

    public CurrencyName getName() {
        return name;
    }

    public void setName(CurrencyName name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CurrencyDetails{" +
                "currencyName='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
