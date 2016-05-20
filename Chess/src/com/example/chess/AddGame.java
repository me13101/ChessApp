package com.example.chess;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddGame extends Activity {
	public static final String GAME_ID = "songId";
	public static final String GAME_NAME = "songName";
	public static final String GAME_MOVES = "gameMoves";
	public static final String GAMES_FILE = "games.dat";
	EditText gameName;
	ArrayList<String> moves;
	int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_game);
		
		// navigating up with app con
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		gameName = (EditText)findViewById(R.id.game_name);

		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) { // add song called to show a song
			gameName.setText(bundle.getString(GAME_NAME));
			id = bundle.getInt(GAME_ID);
		
		}

	}
	
	public void save2(View view){
		System.out.println("GOT INTO SAVE2");
		String name = gameName.getText().toString();
		
		// name and artist are mandatory
		if (name == null || name.length() == 0) {
	
			return;
			// does not quit activity, just returns from method
		}/*
		Game game = new Game(name, PlayGameActivity.gameMoves);
		System.out.println("Created new game");
		ViewGames.myList.add(name, PlayGameActivity.gameMoves);
		//System.out.println("games size = "+PlayGameActivity.games.size());
		System.out.println("Added game to games list");
		PlayGameActivity.gameMoves.clear();
		write();
		Intent intent = new Intent(this, ViewGames.class);
		startActivity(intent);*/
		
		Bundle bundle = new Bundle();
		bundle.putString(GAME_NAME,name);
		Intent intent = new Intent();
		intent.putExtras(bundle);
		Moves.resetGames();
		setResult(RESULT_OK, intent);
		//System.out.println("FINISHED SAVE2 METHOD");
		finish();
	}
	
	
	
	public void write(){
		
		System.out.println("IN WRITE!");
		File directoryName = new File(Environment.getExternalStorageDirectory(),"data");
	    directoryName.mkdirs();
	    File outputFile = new File(directoryName, GAMES_FILE);
	    FileOutputStream fos;

	    try {

	        fos = new FileOutputStream(outputFile);

	        ObjectOutputStream out = new ObjectOutputStream(fos);

	        out.writeObject(PlayGameActivity.games);
	        fos.flush();
	        fos.close();}
	    catch (Exception e){
	    	e.printStackTrace();
	    }
		
		/*
		
		ObjectOutput out = null;

	    try {
	    	final File dir = new File(this.getFilesDir() + "data");
	    	dir.mkdirs(); //create folders where write files
			final File file = new File(dir, GAMES_FILE);
	        out = new ObjectOutputStream(new FileOutputStream(file));
	        out.writeObject(PlayGameActivity.games);
	        out.close();
	        
	    } catch (FileNotFoundException e) {
	    e.printStackTrace();
			
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    System.out.println("FINISHED WRITE");
	    return;*/
	}
	
	public void save(View view) {
		// gather all data
		String name = gameName.getText().toString();
		
		// name and artist are mandatory
		if (name == null || name.length() == 0) {
	
			return;   // does not quit activity, just returns from method
		}
		
		// make Bundle
		Bundle bundle = new Bundle();
		bundle.putString(GAME_NAME,name);
		bundle.putInt(GAME_ID, id);
		
		// send back to caller
		Intent intent = new Intent();
		intent.putExtras(bundle);
		
		setResult(RESULT_OK,intent);
		finish();  // IMPORTANT!! Otherwise, control won't go back to caller
	}
	
	// called when the user taps the Cancel button
	public void cancel(View view) {
		setResult(RESULT_CANCELED);
		finish();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_game, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
