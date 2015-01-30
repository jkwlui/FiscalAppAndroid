package com.fiscalapp.fiscalapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.fiscalapp.fiscalapp.model.Category;
import com.fiscalapp.fiscalapp.model.Expense;
import com.fiscalapp.fiscalapp.model.Place;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by kinwa91 on 2014-03-11.
 */
public class TransactionDatabaseExchanger {

    private TransactionDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public TransactionDatabaseExchanger(Context context) {
        this.dbHelper = TransactionDatabaseHelper.getInstance(context);
    }

    public void open() throws SQLiteException {
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public int createTransaction(String amount, int category, String info, String location, Calendar date, String type) {
        ContentValues values = new ContentValues();
        values.put(TransactionDatabaseHelper.EXPENSES_AMOUNT, amount);
        values.put(TransactionDatabaseHelper.EXPENSES_TYPE, type);
        values.put(TransactionDatabaseHelper.EXPENSES_CATEGORY, category);
        values.put(TransactionDatabaseHelper.EXPENSES_INFO, info);
        values.put(TransactionDatabaseHelper.EXPENSES_LOCATION, location);

        if (date != null) {
            date.set(Calendar.HOUR, 0);
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MILLISECOND, 0);
            long milliSec = date.getTimeInMillis();
            values.put(TransactionDatabaseHelper.EXPENSES_DATE, milliSec);
        }
        long insertId = db.insert(TransactionDatabaseHelper.EXPENSES_TABLE_NAME, null,
                values);

        return (int) insertId;
    }

    public ArrayList<Expense> getMonthExpenses(int year, int month) {
        ArrayList<Expense> expenses = new ArrayList<Expense>();
        Cursor cursor = null;

        Calendar startMonth = new GregorianCalendar();
        startMonth.set(year, month, 0, 0, 0, 0);
        startMonth.set(Calendar.MILLISECOND, 0);

        Calendar endMonth = new GregorianCalendar();
        endMonth.set(year, month + 1, 0, 0, 0, 0);
        endMonth.set(Calendar.MILLISECOND, 0);

        try {
            String whereClause = TransactionDatabaseHelper.EXPENSES_TYPE + "='" + Category.TYPE_EXPENSES + "' AND "
                    + TransactionDatabaseHelper.EXPENSES_DATE + " >= " + startMonth.getTimeInMillis() + " AND "
                    + TransactionDatabaseHelper.EXPENSES_DATE + " < " + endMonth.getTimeInMillis();
            cursor = db.query(TransactionDatabaseHelper.EXPENSES_TABLE_NAME, null, whereClause
                    ,null,null,null,null,null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_ID));
                    int category = cursor.getInt(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_CATEGORY));
                    String info = cursor.getString(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_INFO));
                    long date = cursor.getLong(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_DATE));
                    String type  = cursor.getString(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_TYPE));
                    Calendar cal = GregorianCalendar.getInstance();
                    cal.setTimeInMillis(date);
                    String amount = cursor.getString(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_AMOUNT));
                    BigDecimal decimalAmount = new BigDecimal(amount);
                    Place place = new Place();

                    expenses.add(new Expense(id, info, category, decimalAmount, cal, place));
                } while (cursor.moveToNext());
                cursor.close();
            }
        }
        return expenses;

    }

    public Expense getExpense(int expenseId) {
        Cursor cursor = null;
        try {
            cursor = db.query(TransactionDatabaseHelper.EXPENSES_TABLE_NAME, null,
                    TransactionDatabaseHelper.EXPENSES_ID + " = " + Integer.toString(expenseId), null,null,null, null, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if(cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_ID));
                int category = cursor.getInt(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_CATEGORY));
                String info = cursor.getString(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_INFO));
                long date = cursor.getLong(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_DATE));
                String type  = cursor.getString(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_TYPE));
                Calendar cal = GregorianCalendar.getInstance();
                cal.setTimeInMillis(date);
                String amount = cursor.getString(cursor.getColumnIndex(TransactionDatabaseHelper.EXPENSES_AMOUNT));
                BigDecimal decimalAmount = new BigDecimal(amount);
                Place place = new Place();


                return new Expense(id, info, category, decimalAmount, cal, place);
            } else {
                return new Expense();
            }
        }
    }


    public void updateTransaction(int editId, String amount, int selectedCategoryId, String description, String placeName, Calendar date, String type) {


        ContentValues values = new ContentValues();

        values.put(TransactionDatabaseHelper.EXPENSES_AMOUNT, amount);
        values.put(TransactionDatabaseHelper.EXPENSES_TYPE, type);
        values.put(TransactionDatabaseHelper.EXPENSES_CATEGORY, selectedCategoryId);
        values.put(TransactionDatabaseHelper.EXPENSES_INFO, description);
        values.put(TransactionDatabaseHelper.EXPENSES_LOCATION, placeName);

        if (date != null) {
            date.set(Calendar.HOUR, 0);
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MILLISECOND, 0);
            long milliSec = date.getTimeInMillis();
            values.put(TransactionDatabaseHelper.EXPENSES_DATE, milliSec);
        }

        try {
            db.update(TransactionDatabaseHelper.EXPENSES_TABLE_NAME, values,
                    TransactionDatabaseHelper.EXPENSES_ID + " = " + Integer.toString(editId), null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }

    public void deleteTransaction(int id) {
        try {
            db.delete(TransactionDatabaseHelper.EXPENSES_TABLE_NAME,
                    TransactionDatabaseHelper.EXPENSES_ID + " = " + Integer.toString(id),null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Category> getAllCategories() {
        return getCategoriesByType(null);
    }

    public ArrayList<Category> getCategoriesByType(String requestType) {
        ArrayList<Category> categories = new ArrayList<Category>();
        Cursor cursor = null;
        String whereClause = null;
        if (requestType != null) {
            whereClause = TransactionDatabaseHelper.CATEGORIES_TYPE + "= '" + requestType + "'";
        }
        try {
            cursor = db.query(TransactionDatabaseHelper.CATEGORIES_TABLE_NAME,
                    null, whereClause, null, null, null, TransactionDatabaseHelper.CATEGORIES_ID + " DESC", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {

            if(cursor.moveToFirst()) {
                do {

                    int id = cursor.getInt(cursor.getColumnIndex(TransactionDatabaseHelper.CATEGORIES_ID));
                    int icon = cursor.getInt(cursor.getColumnIndex(TransactionDatabaseHelper.CATEGORIES_ICON));
                    String name = cursor.getString(cursor.getColumnIndex(TransactionDatabaseHelper.CATEGORIES_NAME));
                    String type = cursor.getString(cursor.getColumnIndex(TransactionDatabaseHelper.CATEGORIES_TYPE));

                    categories.add(new Category(id, icon, name, type));

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return categories;
    }

    public Category getCategory(int catId) {
        Cursor cursor = null;
        try {
            cursor = db.query(TransactionDatabaseHelper.CATEGORIES_TABLE_NAME, null,
                    TransactionDatabaseHelper.CATEGORIES_ID + " = " + Integer.toString(catId), null,null,null, null, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if(cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(TransactionDatabaseHelper.CATEGORIES_ID));
                int icon = cursor.getInt(cursor.getColumnIndex(TransactionDatabaseHelper.CATEGORIES_ICON));
                String name = cursor.getString(cursor.getColumnIndex(TransactionDatabaseHelper.CATEGORIES_NAME));
                String type = cursor.getString(cursor.getColumnIndex(TransactionDatabaseHelper.CATEGORIES_TYPE));

                return new Category(id, icon, name, type);
            } else {
                return new Category();
            }
        }
    }

    public int createCategory(String name, int icon, String type) {
        long id = 0;
        ContentValues values = new ContentValues();
        values.put(TransactionDatabaseHelper.CATEGORIES_NAME, name);
        values.put(TransactionDatabaseHelper.CATEGORIES_ICON, icon);
        values.put(TransactionDatabaseHelper.CATEGORIES_TYPE, type);
        try {
            id = db.insert(TransactionDatabaseHelper.CATEGORIES_TABLE_NAME, null, values);
        } catch (SQLiteException e){
            e.printStackTrace();
        }
        return (int) id;
    }

    public void updateCategory(int id, String name, int icon, String type) {
        ContentValues values = new ContentValues();
        values.put(TransactionDatabaseHelper.CATEGORIES_NAME, name);
        values.put(TransactionDatabaseHelper.CATEGORIES_ICON, icon);
        values.put(TransactionDatabaseHelper.CATEGORIES_TYPE, type);
        try {
            db.update(TransactionDatabaseHelper.CATEGORIES_TABLE_NAME, values,
                    TransactionDatabaseHelper.CATEGORIES_ID + " = " + Integer.toString(id), null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }
    public void deleteCategory(int id) {
        try {
           db.delete(TransactionDatabaseHelper.CATEGORIES_TABLE_NAME,
                    TransactionDatabaseHelper.CATEGORIES_ID + " = " + Integer.toString(id),null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }


}
