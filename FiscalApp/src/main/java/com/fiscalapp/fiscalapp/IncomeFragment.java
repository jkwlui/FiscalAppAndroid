package com.fiscalapp.fiscalapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kinwa91 on 2014-03-11.
 */
public class IncomeFragment extends Fragment{

    public IncomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_income, container, false);

        return rootView;
    }


}
