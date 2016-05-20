package com.example.chess;

public class Item
{
	private String name;
	final int drawableId;

	Item(String name, int drawableId)
	{
		this.name = name;
		this.drawableId = drawableId;
	}

	public String getName(){
		return name;
	}
	
	public int getDrawable(){
		return drawableId;
	}
	
	
}