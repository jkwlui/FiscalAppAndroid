package com.fiscalapp.fiscalapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiscalapp.fiscalapp.R;
import com.fiscalapp.fiscalapp.model.Category;

import java.util.ArrayList;

/**
 * Created by kinwa91 on 2014-03-11.
 */

public class CategoryListAdapter extends ArrayAdapter<Category> {

    Context mContext;
    Typeface typeface;


    public CategoryListAdapter(Context context, int resource, ArrayList<Category> categories) {
        super(context, resource, categories);
        mContext = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater vi;
        vi = LayoutInflater.from(mContext);
        v = vi.inflate(R.layout.categories_listview_items, null);
        Category c = getItem(position);



        if (c != null) {

            TextView name = (TextView) v.findViewById(R.id.categories_listview_items_name);
            ImageView icon = (ImageView) v.findViewById(R.id.categories_listview_items_icon);

            if (name != null) {

                name.setTypeface(typeface);
                name.setText(c.getName());
            }
            if (icon != null) icon.setImageResource(mContext.getResources()
                    .getIdentifier(c.getIconFileName(), "drawable", mContext.getPackageName()));

        }

        return v;
    }

    @Override
    public long getItemId(int position) {
        Category c = getItem(position);
        return c.getId();
    }


}

