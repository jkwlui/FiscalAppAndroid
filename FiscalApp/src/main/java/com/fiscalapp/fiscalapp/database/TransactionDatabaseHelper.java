package com.fiscalapp.fiscalapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fiscalapp.fiscalapp.model.Category;

/**
 * Created by kinwa91 on 2014-03-11.
 */
public class TransactionDatabaseHelper extends SQLiteOpenHelper {

    private static TransactionDatabaseHelper sInstance;


    // Database Name, Version
    private static final String DATABASE_NAME = "transactionDatabase";
    private static final int DATABASE_VERSION = 13;

    ////////////////////////////////////////////////////////////////////////
    //
    // CATEGORIES TABLE
    //
    ////////////////////////////////////////////////////////////////////////

    // Table Name
    public static final String CATEGORIES_TABLE_NAME = "categories";

    // Fields
    public static final String CATEGORIES_ID = "id";
    public static final String CATEGORIES_NAME = "name";
    public static final String CATEGORIES_ICON = "icon";
    public static final String CATEGORIES_TYPE = "type";

    // Create Table Query

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE "
            + CATEGORIES_TABLE_NAME + "("
            + CATEGORIES_ID + " INTEGER PRIMARY KEY,"
            + CATEGORIES_NAME + " TEXT,"
            + CATEGORIES_ICON + " INTEGER,"
            + CATEGORIES_TYPE + " TEXT"
            + ")";

    ////////////////////////////////////////////////////////////////////////
    //
    // TRANSACTIONS TABLE
    //
    ////////////////////////////////////////////////////////////////////////


    // Table Names
    public static final String EXPENSES_TABLE_NAME = "transactions";

    // Fields
    public static final String EXPENSES_ID = "id";
    public static final String EXPENSES_CATEGORY = "category";
    public static final String EXPENSES_INFO = "info";
    public static final String EXPENSES_DATE = "date";
    public static final String EXPENSES_LOCATION = "location";
    public static final String EXPENSES_AMOUNT = "amount";
    public static final String EXPENSES_TYPE = "type";


    private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE "
            + EXPENSES_TABLE_NAME + "("
            + EXPENSES_ID + " INTEGER PRIMARY KEY,"
            + EXPENSES_TYPE + " TEXT,"
            + EXPENSES_AMOUNT + " TEXT,"
            + EXPENSES_CATEGORY + " INTEGER,"
            + EXPENSES_INFO + " TEXT,"
            + EXPENSES_LOCATION + " TEXT,"
            + EXPENSES_DATE + " INTEGER" + ")";

    public static TransactionDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TransactionDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public TransactionDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String[] INSERT_DEFAULT_CATEGORIES = {
                // Food and Drinks
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Food and Drinks', '" + Category.TYPE_EXPENSES + "',  228)",
                // Groceries
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Groceries','" + Category.TYPE_EXPENSES + "',  25)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Transportation','" + Category.TYPE_EXPENSES + "',  164)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Entertainment','" + Category.TYPE_EXPENSES + "',  203)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Sports', '" + Category.TYPE_EXPENSES + "',  227)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Lovers and Friends','" + Category.TYPE_EXPENSES + "',  78)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Gadgets','" + Category.TYPE_EXPENSES + "',  39)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Education','" + Category.TYPE_EXPENSES + "',  224)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Investments','" + Category.TYPE_EXPENSES + "',  6)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Other', '" + Category.TYPE_EXPENSES + "', 0)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Salary','" + Category.TYPE_INCOME + "',  101)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Investments','" + Category.TYPE_INCOME + "',  171)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Loan','" + Category.TYPE_INCOME + "',  52)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Gifts','" + Category.TYPE_INCOME + "',  122)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Prizes','" + Category.TYPE_INCOME + "',  173)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Other','" + Category.TYPE_INCOME + "',  0)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Credit Cards','" + Category.TYPE_BILLS + "',  44)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Water','" + Category.TYPE_BILLS + "',  101)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Power','" + Category.TYPE_BILLS + "',  13)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Natural Gas','" + Category.TYPE_BILLS + "',  64)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Internet','" + Category.TYPE_BILLS + "',  74)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Cable','" + Category.TYPE_BILLS + "',  175)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Home Phone','" + Category.TYPE_BILLS + "',  75)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Mobile Phone','" + Category.TYPE_BILLS + "',  111)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Mortgage','" + Category.TYPE_BILLS + "',  54)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Car Payments','" + Category.TYPE_BILLS + "',  24)",
                "INSERT INTO "
                        + CATEGORIES_TABLE_NAME + " ("
                        + CATEGORIES_NAME + ", "
                        + CATEGORIES_TYPE + ", "
                        + CATEGORIES_ICON + ") " + "VALUES ("
                        + "'Other','" + Category.TYPE_BILLS + "',  0)",
        };
        try {
            db.execSQL(CREATE_TABLE_TRANSACTION);
            db.execSQL(CREATE_TABLE_CATEGORIES);
            for (int i=INSERT_DEFAULT_CATEGORIES.length-1; i>=0; i--) {
                db.execSQL(INSERT_DEFAULT_CATEGORIES[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME);
        onCreate(db);
    }

}
