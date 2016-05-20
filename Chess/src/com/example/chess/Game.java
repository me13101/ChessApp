package com.example.chess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.Date;

import android.content.Context;
import android.text.format.DateFormat;

public class Game implements Serializable {
	private static final long serialVersionUID = 1L;
	public int id;
	static String fileName = "data"+File.separator+"set.sav";
	public static ArrayList<Game> Games = new ArrayList<Game>();
	String name;
	String date;
	ArrayList<String> moves;

	public Game(String name, ArrayList<String> moves, int id){
		this.name = name;
		this.date = getDateTime();
		this.moves = moves;
		this.id = id;
	}
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	public String toString(){
		return name+" - "+date;
	}
	
	public boolean addGame(Game g){
		Games.add(g);
		return true;
	}
	public String getName(){
		return name;
	}
	public ArrayList<String> getMoves(){
		return moves;
	}
	
	public ArrayList<Game> getGameList(){
		return Games;
	}
	public static void saveGames(){
		
	
		//FileOutputStream fOut = openFileOutput("file name here");
		
//		try {
//			File theDir = new File("data"+File.separator);
//			//File theDir = new File(context.getFilesDir(), );
//			// if the directory does not exist, create it
//			if (!theDir.exists()) {
//
//				boolean result = false;
//
//				try{
//					theDir.mkdir();
//					result = true;
//				} catch(SecurityException se){
//					//handle it
//					//System.out.println("SADF");
//				}        
//				if(result) {    
//					
//					FileOutputStream fileOut = new FileOutputStream("data"+File.separator+"set.sav");
//					ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//					objectOut.writeObject(Games);
//					objectOut.close();}
//			}
//			else{
//				System.out.println("got here");
//				FileOutputStream fileOut = new FileOutputStream("data"+File.separator+"set.sav");
//				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//				objectOut.writeObject(Games);
//				objectOut.close();}
//
//		}
//		catch(Exception e1) {
//			System.out.println("File could not be read or found.");
//			e1.printStackTrace();
//		}

	}
}
