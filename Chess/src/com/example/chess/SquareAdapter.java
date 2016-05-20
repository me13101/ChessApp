package com.example.chess;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.content.Context;

public class SquareAdapter extends BaseAdapter{
	 private Context mContext;

	    public SquareAdapter(Context c) {
	        mContext = c;
	    }

	    public int getCount() {
	        return mThumbIds.length;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {
	            // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	           
	        
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        imageView.setImageResource(mThumbIds[position]);
	        return imageView;
	    }

	    // references to our images
	    private Integer[] mThumbIds = {
	            R.drawable.blackrook, R.drawable.blackknight,	
	            R.drawable.blackbishop,
	            R.drawable.blackqueen,
	           R.drawable.blackking,
	           R.drawable.blackbishop,
	           R.drawable.blackknight,	
	           R.drawable.blackrook,
	           
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	           R.drawable.blank,
	    };
}