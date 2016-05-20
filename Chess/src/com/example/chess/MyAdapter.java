package com.example.chess;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;



public class MyAdapter extends BaseAdapter
{
	private List<Item> items = new ArrayList<Item>();
	private LayoutInflater inflater;
	

	public MyAdapter(Context context)
	{
		inflater = LayoutInflater.from(context);
		items.add(new Item("whiterook",  R.drawable.whiterook));
		items.add(new Item("whiteknight",   R.drawable.whiteknight));	
		items.add(new Item("whitebishop", R.drawable.whitebishop));
		items.add(new Item("whitequeen",R.drawable.whitequeen));
		items.add(new Item("whiteking",R.drawable.whiteking));
		items.add(new Item("whitebishop", R.drawable.whitebishop));
		items.add(new Item("whiteknight", R.drawable.whiteknight));
		items.add(new Item("whiterook", R.drawable.whiterook));
		items.add(new Item("whitepawn",  R.drawable.whitepawn));
		items.add(new Item("whitepawn",R.drawable.whitepawn));
		items.add(new Item("whitepawn", R.drawable.whitepawn));
		items.add(new Item("whitepawn",R.drawable.whitepawn));
		items.add(new Item("whitepawn", R.drawable.whitepawn));
		items.add(new Item("whitepawn",  R.drawable.whitepawn));
		items.add(new Item("whitepawn",R.drawable.whitepawn));
		items.add(new Item("whitepawn",R.drawable.whitepawn));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank", R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank", R.drawable.blank));
		items.add(new Item("blank", R.drawable.blank));
		items.add(new Item("blank", R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank", R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank", R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blank",R.drawable.blank));
		items.add(new Item("blackpawn",  R.drawable.blackpawn));
		items.add(new Item("blackpawn",R.drawable.blackpawn));
		items.add(new Item("blackpawn", R.drawable.blackpawn));
		items.add(new Item("blackpawn",R.drawable.blackpawn));
		items.add(new Item("blackpawn", R.drawable.blackpawn));
		items.add(new Item("blackpawn",  R.drawable.blackpawn));
		items.add(new Item("blackpawn",R.drawable.blackpawn));
		items.add(new Item("blackpawn",R.drawable.blackpawn));
		items.add(new Item("blackrook",  R.drawable.blackrook));

		items.add(new Item("blackknight",   R.drawable.blackknight));	
		items.add(new Item("blackbishop", R.drawable.blackbishop));
		items.add(new Item("blackqueen",R.drawable.blackqueen));
		items.add(new Item("blackking",R.drawable.blackking));
		items.add(new Item("blackbishop", R.drawable.blackbishop));
		items.add(new Item("blackknight", R.drawable.blackknight));
		items.add(new Item("blackrook", R.drawable.blackrook));
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public boolean isEnabled(int position) {

		return true;

	}

	@Override
	public Object getItem(int i)
	{
		return items.get(i);
	}

	public void setItem(int i, GridView board, String name, int drawable)
	{
		Item item = new Item(name, drawable);
		items.set(i, item);
		this.notifyDataSetChanged();
		board.invalidateViews();
		
	}

	@Override
	public long getItemId(int i)
	{
		return items.get(i).drawableId;
	}

	@SuppressLint("ViewTag")
	@Override
	public View getView(int i, View view, ViewGroup viewGroup)
	{	
		View v = view;
		ImageView picture;
		
		if(v == null)
		{
			v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
			v.setTag(R.id.picture, v.findViewById(R.id.picture));

		}



		picture = (ImageView)v.getTag(R.id.picture);
		Item item = (Item)getItem(i);

		if (i<=7||(15<i&&i<=23)||(31<i&&i<=39)||(47<i&&i<=55)){
			if (i % 2 == 0){
				picture.setBackgroundResource(R.drawable.lightwood);}
			else{
				picture.setBackgroundResource(R.drawable.wood);
			}
		}else{
			if (i % 2 != 0){
				picture.setBackgroundResource(R.drawable.lightwood);}
			else{

				picture.setBackgroundResource(R.drawable.wood);
			}
		}
		picture.setImageResource(item.drawableId);


		return v;
	}

	
	
}
