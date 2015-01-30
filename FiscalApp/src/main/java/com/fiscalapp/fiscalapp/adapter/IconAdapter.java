package com.fiscalapp.fiscalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fiscalapp.fiscalapp.R;


public class IconAdapter extends android.widget.BaseAdapter {
	
	public Context mContext;
    public int minId = 1;
    public int maxId = 252;

    public IconAdapter(Context context) {
        mContext = context;
    }
    
	@Override
	public int getCount() {
		return this.maxId - this.minId + 1;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        position = position + 1;
/*        ImageView imageView;
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);*/

        LayoutInflater vi = LayoutInflater.from(mContext);
        View v = vi.inflate(R.layout.grid_item, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.grid_imageview);


        imageView.setImageResource(mContext.getResources()
                .getIdentifier("cat_icon_"+Integer.toString(position), "drawable", mContext.getPackageName()));
        return v;
	}


}
