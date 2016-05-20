package com.example.chess;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class RecordedGameActivity extends Activity{


	Button _forward;
	Button _delete;
	ArrayList<String> gameMoves;
	AlertDialog finish;
	int spot=0;
	int finishspot = 0;
	public static GridView recorded_board;

	public void cancelDialog(){
		finish.cancel();
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recorded_game);
		gameMoves = new ArrayList<String>();
		recorded_board = 
				(GridView)findViewById(R.id.recorded_chessboard);
		recorded_board.setAdapter(new MyAdapter(this));
		_forward = (Button) findViewById(R.id.btn_forward);
		final ArrayList<String> moves = getIntent().getExtras().getStringArrayList("moves");
		finishspot = moves.size()-1;
		if (moves!=null){
			System.out.println("GOT BACK GAME LIST!");
		}



		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getBaseContext());
		alertDialogBuilder.setTitle("Game finished");
		alertDialogBuilder
		.setCancelable(false)
		.setNeutralButton("View Games",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				cancelDialog();
				Intent intent = new Intent(RecordedGameActivity.this, ViewGames.class);
				startActivity(intent);
			}
		});
		finish = alertDialogBuilder.create();
		finish.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

	
		
		_forward.setOnClickListener(new View.OnClickListener(){



			@Override
			public void onClick(View v) {
				
				String start = ""; 
				String stop = "";
				String pawnsthird = null;
			
				for (int  i = spot; i<moves.size()-1;){
					if (i>=moves.size()-1||spot>=moves.size()-1){
						finish.show();
						return;
					}
					System.out.println("start of move for: "+moves.get(spot));
					if (moves.get(spot).equals("whitepawnpromo")||moves.get(spot).equals("blackpawnpromo")){
						String promoted = moves.get(spot+2);
						int piece = getDrawableInt(promoted);
						((MyAdapter) recorded_board.getAdapter()).setItem(Integer.parseInt(moves.get(spot+1)), recorded_board,"blank", piece);
						recorded_board.invalidateViews();
						spot = spot+3;
						return;
					}
					
					
					start = moves.get(spot+1);
					System.out.println(moves.get(spot+1));
					stop = moves.get(spot+2);

					System.out.println(moves.get(spot+2));
					if (spot+3>=moves.size()){
						finish.show();
						return;
					}
					if (Character.isDigit(moves.get(spot+3).charAt(0))){
						System.out.println("here");
						pawnsthird = moves.get(spot+3);
						System.out.println(moves.get(spot+3));
					}
					else{
						System.out.println("here2");
						pawnsthird = null;
					}

					if (pawnsthird==null){
						System.out.println("here3");
						((MyAdapter) recorded_board.getAdapter()).setItem(Integer.parseInt(moves.get(spot+1)), recorded_board,"blank", R.drawable.blank);
						recorded_board.invalidateViews();
						int piece = getDrawableInt(moves.get(spot));
						((MyAdapter) recorded_board.getAdapter()).setItem(Integer.parseInt(moves.get(spot+2)), recorded_board,moves.get(spot), piece );
						recorded_board.invalidateViews();
						spot = spot + 3;
						System.out.println("1GOING TO SPOT: "+spot);
						if (spot>=moves.size()){
							finish.show();
						}
						return;
					}else{
						((MyAdapter) recorded_board.getAdapter()).setItem(Integer.parseInt(moves.get(spot+1)), recorded_board,"blank", R.drawable.blank);
						recorded_board.invalidateViews();
						((MyAdapter) recorded_board.getAdapter()).setItem(Integer.parseInt(moves.get(spot+2)), recorded_board,"blank", R.drawable.blank);
						recorded_board.invalidateViews();
						int piece = getDrawableInt(moves.get(spot));
						((MyAdapter) recorded_board.getAdapter()).setItem(Integer.parseInt(moves.get(spot+3)), recorded_board,moves.get(spot), piece);
						recorded_board.invalidateViews();
						System.out.println("here5");
						spot = spot + 4;

						System.out.println("2GOING TO SPOT: "+spot);
						if (spot>=moves.size()){
							finish.show();
						}
						return;
					}


				}
			}

		});
		
	/*	_backward.setOnClickListener(new View.OnClickListener(){



			@Override
			public void onClick(View v) {
				System.out.println("IN ONCLIKC BACKWARD");
				String start = ""; 
				String stop = "";
				String pawnsthird = null;
				System.out.println("SPOT: "+spot+" MOVES SIZE: "+moves.size());
				if (spot-3>=0){
					finish.show();
					return;
				}
				else {
					if (!Character.isDigit(moves.get(spot-3).charAt(0))){
						start = moves.get(spot-2);
						stop = moves.get(spot-1);
						((MyAdapter) recorded_board.getAdapter()).setItem(Integer.parseInt(stop), recorded_board,"blank", R.drawable.blank);
						recorded_board.invalidateViews();
						int piece = getDrawableInt(moves.get(spot));
						((MyAdapter) recorded_board.getAdapter()).setItem(Integer.parseInt(start), recorded_board,moves.get(spot), piece );
						recorded_board.invalidateViews();
						spot = spot - 3;
						return;
					}
					else{
						start = moves.get(spot-2);
						stop = moves.get(spot-1);
						pawnsthird = moves.get(spot-3);
						((MyAdapter) recorded_board.getAdapter()).setItem(Integer.parseInt(stop), recorded_board,"blank", R.drawable.blank);
						recorded_board.invalidateViews();
						((MyAdapter) recorded_board.getAdapter()).setItem(Integer.parseInt(stop), recorded_board,"blank", R.drawable.blank);
						recorded_board.invalidateViews();
						int piece = getDrawableInt(moves.get(spot));
						((MyAdapter) recorded_board.getAdapter()).setItem(Integer.parseInt(start), recorded_board,moves.get(spot), piece );
						recorded_board.invalidateViews();
						spot = spot - 4;
						return;
					}
				}
			
			}
		});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static int getDrawableInt(String piece){
		if (piece.equals("whitepawn")){
			return R.drawable.whitepawn;
		}
		if (piece.equals("blackpawn")){
			return R.drawable.blackpawn;
		}
		if (piece.equals("whitebishop")){
			return R.drawable.whitebishop;
		}
		if (piece.equals("blackbishop")){
			return R.drawable.blackbishop;
		}
		if (piece.equals("whiterook")){
			return R.drawable.whiterook;
		}
		if (piece.equals("blackrook")){
			return R.drawable.blackrook;
		}
		if (piece.equals("whiteknight")){
			return R.drawable.whiteknight;
		}
		if (piece.equals("blackknight")){
			return R.drawable.blackknight;
		}
		if (piece.equals("whiteking")){
			return R.drawable.whiteking;
		}
		if (piece.equals("blackking")){
			return R.drawable.blackking;
		}
		if (piece.equals("whitequeen")){
			return R.drawable.whitequeen;
		}
		if (piece.equals("blackqueen")){
			return R.drawable.blackqueen;
		}

		return -1;
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
