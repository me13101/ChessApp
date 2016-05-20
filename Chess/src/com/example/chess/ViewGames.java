package com.example.chess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ViewGames extends Activity {
	 static MyGameList myList;
	 ListView listView;
	 Button _mainMenu;
	 TreeSet<Game> tree;
	 public static int save = -1;
		public static final String GAME_ID = "songId";
		public static final String GAME_NAME = "songName";
		public static final String GAME_MOVES = "gameMoves";
		public static final int ADD_GAME_CODE = 1;
		ArrayList<String> vmoves;
		public static final String GAMES_FILE = "games.dat";
		
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_games);

		_mainMenu = (Button) findViewById(R.id.btn_menu);
		_mainMenu.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(ViewGames.this, MainActivity.class);
				startActivity(intent);
			}
		});
		try{
			myList = MyGameList.getInstance(this);
			if (save==0){
				save = -1;
				Intent intent = new Intent(this, AddGame.class);
				ArrayList<String> moves = getIntent().getExtras().getStringArrayList("pmoves");
				vmoves = moves;
				Bundle mBundle = new Bundle();
				mBundle.putStringArrayList("moves", moves);
				System.out.println("MOVES FROM ONCREATE VIEW GAMES: "+moves.size());
				intent.putExtras(mBundle);
				startActivityForResult(intent, ADD_GAME_CODE);
			}
			System.out.println("after load2");
			
			System.out.println("myList: ");
			for (int i = 0; i < myList.getGames().size(); i++){
				System.out.println("GAME: "+myList.getGames().get(i).toString());
			}
			
			//PlayGameActivity.games = load(this, "/data/set.sav");
			//System.out.println("after load");
		} catch(Exception e1) {
			Toast.makeText(this, 
					"Could not load songs from file", 
					Toast.LENGTH_LONG).show();
		
			
		}
		listView = (ListView)findViewById(R.id.game_list);
		registerForContextMenu(listView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
				//Intent mIntent = new Intent(ViewGames.this, RecordedGameActivity.class);
				//Bundle extras = mIntent.getExtras();
				ArrayList<String> moves = myList.getGames().get(position).getMoves();
				if (myList.getGames().get(position).getMoves()== null){
					System.out.println("MOVES LIST WAS NULL!!!!!!");
				}else{
					System.out.println("WAS NOT NULL!");
				}
				
				Intent mIntent = new Intent(ViewGames.this, RecordedGameActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putStringArrayList("moves", moves);
				mIntent.putExtras(mBundle);
				
				//extras.putStringArrayList("moves", moves);  
				startActivity(mIntent);
			}
		});
		handleIntent(getIntent());
	}

	private void handleIntent(Intent intent) {
	
	    		showGameList();
	    
	}
	
	
	private void showGameList() {
		listView.setAdapter(
				new ArrayAdapter<Game>(this, R.layout.game,myList.getGames()));

	}
	
	public void addGame(View view) {
		System.out.println("IN VIEWGAMES ADDGAME MTHOD");
		Intent intent = new Intent(this, AddGame.class);
		Bundle mBundle = new Bundle();
		/*if (moves==null){
			System.out.println("in VIEWGAMES moves wAS NULL");
		}
		mBundle.putStringArrayList("vmoves", moves);*/
		intent.putExtras(mBundle);
		startActivityForResult(intent, ADD_GAME_CODE);
		
	}
	
	public void update(View view) {
		listView.setAdapter(
				new ArrayAdapter<Game>(this, R.layout.game,PlayGameActivity.games));
	}
	

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// ... INSERT CODE HERE to get added song data, update list and redisplay
		if (resultCode != RESULT_OK) {
			return;
		}

		Bundle bundle = intent.getExtras();
		if (bundle == null) {
			return;
		}else{
			System.out.println("IN ONACTIVITYRESULT METHOD");
		String name = bundle.getString(AddGame.GAME_NAME);
		ArrayList<String> moves = bundle.getStringArrayList(AddGame.GAME_MOVES);
		if (moves==null){
			System.out.println("MOVES IN ONACTIVITYRESULT WAS NULL");
		}
		if (requestCode == ADD_GAME_CODE) {
			
			myList.add(name, vmoves);
		}
		listView.setAdapter(
			new ArrayAdapter<Game>(this, R.layout.game,myList.getGames()));
	}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_games, menu);
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
