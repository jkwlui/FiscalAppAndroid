package com.fiscalapp.fiscalapp.model;

import java.math.BigDecimal;

/**
 * Created by kinwa91 on 2014-03-13.
 */
public class Transaction {

    private int id;
    private String info;
    private int category;
    private BigDecimal amount;

    public Transaction () {}
    public Transaction (int id, String info, int category, BigDecimal amount) {
        this.id = id;
        this.category = category;
        this.info = info;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
