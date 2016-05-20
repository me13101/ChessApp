package com.example.chess;

//import MySongList;
//import Song;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class MyGameList implements GameList{
	private static MyGameList gameList=null;
	private ArrayList<Game> games;
	 TreeSet<Game> tree;
	private int maxId;
	private Context context;
	public static final String GAMES_FILE = "games.dat";
	  File root = android.os.Environment.getExternalStorageDirectory(); 
	private MyGameList() {
		games = new ArrayList<Game>();
		maxId = -1;
	}

	public static MyGameList getInstance(Context ctx) 
			throws IOException {
		if (gameList == null) {
			gameList = new MyGameList();
			gameList.context = ctx;
			gameList.load();
		}
		return gameList;
	}

	@SuppressWarnings("unchecked")
	public void load()
    {		  
			System.out.println("in MyGameList load method");
			File directoryName = new File(root.getAbsolutePath()	,"data");
		    directoryName.mkdirs();
		    File outputFile = new File(directoryName, GAMES_FILE);
		    FileInputStream fis;
		    try {
		       // fis = new FileInputStream(outputFile);
		    	fis = context.openFileInput(GAMES_FILE); 
		    	ObjectInputStream ois = new ObjectInputStream(fis);
		            games = (ArrayList<Game>) ois.readObject();
		            //System.out.println("GOT GAMES BACK FROM "+GAMES_FILE);
		            System.out.println("NUMBER OF GAMES: "+games.size());
		            ois.close();
		    }
		    catch(Exception e){
		    	System.out.println("CAUGHT EXCEPTION e");
		    	e.printStackTrace();
		    }
		   
		
}
	public void setContext(Context ctx) {
		// TO BE FILLED IN
		context = ctx;
	}

	public void delete(){
		games.clear();
		try {
			store();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	public void store() throws IOException {
	System.out.println("IN WRITE!");
	File directoryName = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),File.separator+"data");
    directoryName.mkdirs();
    File outputFile = new File(directoryName, GAMES_FILE);
    FileOutputStream fos;

    try {
    
        fos = context.openFileOutput(GAMES_FILE,Context.MODE_PRIVATE);

        ObjectOutputStream out = new ObjectOutputStream(fos);

        out.writeObject(games);
        fos.flush();
        fos.close();
    	System.out.println("finished write to "+GAMES_FILE);}
    catch (Exception e){
    	e.printStackTrace();
    }
	
	}

	public void setGames(ArrayList<Game> g) {
		// TODO Auto-generated method stub
		this.games=g;
	}
	
	public ArrayList<Game> getGames() {
		// TODO Auto-generated method stub
		return games;
	}

	@Override
	public Game add(String name, ArrayList<String> moves) {
		if (name == null || moves == null) {
			throw new IllegalArgumentException("Name and moves are mandatory");
		}
	
		maxId++;
		Game game = new Game(name, moves, maxId);
		System.out.println("FROM MYGAMESLIST ADD: "+moves.size());
		
		tree = new TreeSet<Game>(new MyComp());
		tree.addAll(games);
		tree.add(game);
		ArrayList<Game> temp = new ArrayList<Game>();
		Iterator<Game> i = tree.iterator();
		while (i.hasNext()){
			Game u = i.next();
			temp.add(u);
		}
		games = temp;
		PlayGameActivity.gameMoves.clear();
		

		try {
			store();  // write through
		} catch (IOException e) {
			Toast.makeText(context, 
					"Could not store songs to file", 
					Toast.LENGTH_LONG).show();

		}



		return game;}
	
	@Override
	public int getPos(Game g) {
		if (games.size() == 0) {
			return -1;
		}
		for (int i = 0; i <games.size(); i++){
			if (games.get(i).getName().equals(g.getName())){
				return i;
			}
		}
		return 0;
	}

	public void sortByName(){
		tree = new TreeSet<Game>(new MyComp());
		tree.addAll(games);
		ArrayList<Game> temp = new ArrayList<Game>();
		Iterator<Game> i = tree.iterator();
		while (i.hasNext()){
			Game u = i.next();
			temp.add(u);
		}
		games = temp;
	}
	private class MyComp implements Comparator<Game>, Serializable{
		 
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		@Override
		public int compare(Game lhs, Game rhs) {
			// TODO Auto-generated method stub
			int c = lhs.getName().compareTo(rhs.getName());
			return c;		
			}
	     
	}


}
