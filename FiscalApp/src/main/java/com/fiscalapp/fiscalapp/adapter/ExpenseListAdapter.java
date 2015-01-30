package com.fiscalapp.fiscalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiscalapp.fiscalapp.R;
import com.fiscalapp.fiscalapp.model.Expense;

import java.util.List;

/**
 * Created by kinwa91 on 2014-03-13.
 */
public class ExpenseListAdapter  extends ArrayAdapter<Expense> {

    Context mContext;

    public ExpenseListAdapter(Context context, List<Expense> expenses) {
        super(context, 0 ,expenses);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Expense item = getItem(position);
        //Without ViewHolder for demo purpose
        View view = convertView;
        if (view == null) {
            LayoutInflater li =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.transaction_listview_item, parent, false);
            ViewHolder holder = new ViewHolder();

            holder.icon = (ImageView) view.findViewById(R.id.transaction_listview_category_icon);
            holder.category = (TextView) view.findViewById(R.id.transaction_list_category_textview);
            holder.info = (TextView) view.findViewById(R.id.transaction_list_info_textview);
            holder.amount = (TextView) view.findViewById(R.id.transaction_list_amuont_textview);
            holder.position = position;
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.info.setText(item.getInfo());
        holder.amount.setText(item.getAmount().toString());
        return view;
    }


    @Override
    public int getViewTypeCount() {
        return 1;
    }

    private class ViewHolder {
        ImageView icon;
        TextView category;
        TextView info;
        TextView amount;
        int position;
    }

}

