package com.fiscalapp.fiscalapp.drawer;

/**
 * Created by kinwa91 on 2014-03-11.
 */
public interface NavDrawerItem {
    public int getId();
    public String getLabel();
    public int getType();
    public boolean isEnabled();
    public boolean updateActionBarTitle();
}
