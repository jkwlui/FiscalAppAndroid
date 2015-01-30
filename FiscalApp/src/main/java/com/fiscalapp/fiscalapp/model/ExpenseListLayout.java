package com.fiscalapp.fiscalapp.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.fiscalapp.fiscalapp.adapter.ExpenseListAdapter;

/**
 * Created by kinwa91 on 2014-03-13.
 */
public class ExpenseListLayout extends LinearLayout implements View.OnClickListener {

    private ExpenseListAdapter adapter;
    private View.OnClickListener mListener;
    private View view;

    public ExpenseListLayout(Context context) {
        super(context);
    }
    public ExpenseListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpenseListLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(ExpenseListAdapter list) {
        this.adapter = list;
        setOrientation(VERTICAL);

        //Popolute list
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                view = adapter.getView(i, null, null);
                this.addView(view);
            }
        }
    }

    public void setListener(View.OnClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {

    }

}
