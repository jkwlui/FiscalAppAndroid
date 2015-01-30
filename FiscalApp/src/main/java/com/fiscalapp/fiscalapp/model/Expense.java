package com.fiscalapp.fiscalapp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by kinwa91 on 2014-03-13.
 */
public class Expense extends Transaction implements Comparable<Expense> {
    private Calendar date;
    private Place place;

    public Expense() {}
    public Expense(int id, String info, int category, BigDecimal amount, Calendar date, Place place) {
        super(id, info, category, amount);
        this.date = date;
        this.place = place;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public static BigDecimal totalAmount(ArrayList<Expense> expenses) {
        BigDecimal total = new BigDecimal(0.0);
        if(expenses!=null) {
            for (Expense e : expenses) {
                total = total.add(e.getAmount());
            }
        }
        return total;
    }


    @Override
    public int compareTo(Expense o) {
        if (getDate() == null || o.getDate() == null)
            return 0;
        return o.getDate().compareTo(getDate());
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
