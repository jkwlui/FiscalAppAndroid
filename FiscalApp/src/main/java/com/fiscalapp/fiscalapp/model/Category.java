package com.fiscalapp.fiscalapp.model;

/**
 * Created by kinwa91 on 2014-03-11.
 */
public class Category {

    public int id;
    public int icon;
    public String name;
    public String type;

    public static String TYPE_EXPENSES = "expenses";
    public static String TYPE_INCOME = "income";
    public static String TYPE_BILLS = "bills";

    public Category() {}

    public Category(int id, int icon, String name, String type) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public String getIconFileName() {
        return "cat_icon_" + Integer.toString(icon);
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static int getTypeEnum(String type) {
        int num = 0;
        if (type != null) {
            if (type.equals(TYPE_EXPENSES)) {
                num = 0;
            } else if (type.equals(TYPE_INCOME)) {
                num = 1;
            } else if (type.equals(TYPE_BILLS)) {
                num =  2;
            } else {
                num =  0;
            }
        } else {
            num = 0;
        }
        return num;
    }

    public static String getTypeString(int num) {
        String type = "";
        switch(num){
            case 0:
                type = TYPE_EXPENSES;
                break;
            case 1:
                type = TYPE_INCOME;
                break;
            case 2:
                type = TYPE_BILLS;
                break;
            default:
                type = TYPE_EXPENSES;
                break;

        }
        return type;
    }


}
