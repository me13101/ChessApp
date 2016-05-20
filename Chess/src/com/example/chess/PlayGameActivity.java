package com.example.chess;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

public class PlayGameActivity extends Activity {
	int i = 0;

	
	static int pos = 1;
	Integer imageid = 0;
	Item piece = null;
	int firstposition = 0;
	AlertDialog alertDialog;
	AlertDialog promotion;
	AlertDialog incheck;
	AlertDialog drawd;
	AlertDialog gameover;
	boolean gameon = true;
	public static final int ADD_GAME_CODE = 1;
	AlertDialog danger;
	public static GridView chessboardGridView;
	public static Button _ai, draw, resign, undo;
public static ArrayList<String> gameMoves; 
	public static ArrayList<Game> games;
	public void cancelDialog()
	{
		alertDialog.cancel();
	}
	public void cancelDialog3()
	{
		danger.cancel();
	}
	public void cancelDialog4()
	{
		gameover.cancel();
	}
	public void cancelDialog2()
	{
		promotion.cancel();
	}

	public static void resetGameMoves(){
	gameMoves.clear();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_game);
		gameMoves = new ArrayList<String>();
		games = new ArrayList<Game>();
		chessboardGridView = 
				(GridView)findViewById(R.id.chessboard);
		chessboardGridView.setAdapter(new MyAdapter(this));
		chessboardGridView.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> parent, final View v, final int position, long id) 
			{

				startProgress(v, position);
			}
		});	


		_ai = (Button)findViewById(R.id.button3);
		draw = (Button)findViewById(R.id.button1);
		
		draw.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Draw(v, chessboardGridView);

			}
		});
		_ai.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AutoMove(v, chessboardGridView);

			}
		});
undo = (Button)findViewById(R.id.button4);
undo.setOnClickListener(new View.OnClickListener() {

	@Override
	public void onClick(View v) {
		System.out.println("UNDO CLICKED");
		undo(v, chessboardGridView);

	}
});


resign = (Button)findViewById(R.id.button2);
		
		resign.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				resign(v, chessboardGridView);

			}
		});

	}

	public void undo(View view, GridView board){
		if (Moves.lastp!=-1){
			System.out.println("lastp!=-1");
			int piece = RecordedGameActivity.getDrawableInt(Moves.lastpiece);
			((MyAdapter) board.getAdapter()).setItem(Moves.lastfirst, board,"blank", R.drawable.blank);
			board.invalidateViews();
			((MyAdapter) board.getAdapter()).setItem(Moves.lastp, board,"blank", R.drawable.blank);
			board.invalidateViews();
			((MyAdapter) board.getAdapter()).setItem(Moves.lastdest, board,Moves.lastpiece, piece);
			board.invalidateViews();
			PlayGameActivity.chessboardGridView.invalidateViews();
			
			
		}else{
			System.out.println("dfasdf");
			int piece = RecordedGameActivity.getDrawableInt(Moves.lastpiece);
			((MyAdapter) board.getAdapter()).setItem(Moves.lastfirst, board,"blank", R.drawable.blank);
			board.invalidateViews();
			((MyAdapter) board.getAdapter()).setItem(Moves.lastdest, board,Moves.lastpiece, piece);
			board.invalidateViews();
			
		}
	}
	
	
	public void resign(View view, GridView board) {
		gameon=false;
		AlertDialog.Builder gameOverBuilder = new AlertDialog.Builder(
				getBaseContext());
		gameOverBuilder.setTitle("Game Over");
		if (Moves.player%2==0){
		gameOverBuilder.setMessage("Opponent Resigned, Black Wins!");}
		else{
			gameOverBuilder.setMessage("Opponent Resigned, White Wins!");
		}
		gameOverBuilder.setPositiveButton("Okay",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				Moves.resetGames();
				gameover.cancel();
				Intent n = new Intent(PlayGameActivity.this, MainActivity.class);
				startActivity(n);
			}
		});
		gameover = gameOverBuilder.create();
		gameover.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		gameover.show();
		
		Intent n = new Intent(PlayGameActivity.this, MainActivity.class);
		startActivity(n);
	}
	
	public void AutoMove(View view, GridView board) {
		try{
		
		ImageView picture;
		picture = (ImageView)view.getTag(R.id.picture);
		int start;
		int move;
		int startSearch = 0;
		boolean done = false;
		System.out.println("got here from the AI button");
		//ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
		int player = Moves.player;
		if (player % 2 == 0){
			while(done == false && startSearch < 64){
				start = Moves.getPosition2('w', board, startSearch);
				Item test = (Item)((MyAdapter)board.getAdapter()).getItem(start); 
				//System.out.println(test.getName());
				if (test.getName().equals("whitepawn")){
					//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("whitepawn")){
					//move = Moves.isValidPawnMove("pawn", start, start+8, board, 'w');
					//System.out.println("test");
					/*
				if (move > 0){
					imageid = (int)board.getAdapter().getItemId(start);
					((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
					((MyAdapter) board.getAdapter()).setItem(start+8, board,"whitepawn", imageid);
					done = true;
				}
				else{
					startSearch++;
				}*/
					ArrayList<Integer> possiblemoves = Moves.getPossiblePawnMoves(start, board, 'w');
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"whitepawn", imageid);
						Moves.gameMoves.add("whitepawn");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
						done = true;
					}
					else{ 
						startSearch++;
						continue;
					}

				}
				if (test.getName().equals("whiterook")){
					//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("rook")){
					System.out.println("got here");
					ArrayList<Integer> possiblemoves = Moves.getPossibleRookMoves(start, board, 'w');
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"whiterook", imageid);
						Moves.gameMoves.add("whiterook");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
						done = true;
					}
					else{
						startSearch++;
						continue;
					}
				}
				if (test.getName().equals("whiteknight")){
					//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("whiteknight")){
					System.out.println("got here");
					ArrayList<Integer> possiblemoves = Moves.getPossibleKnightMoves(start, board, 'w');
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"whiteknight", imageid);
						Moves.gameMoves.add("whiteknight");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
						done = true;
					}
					else{
						startSearch++;
						continue;
					}
				}
				if (test.getName().equals("whitebishop")){
					//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("whitebishop")){
					ArrayList<Integer> possiblemoves = Moves.getPossibleBishopMoves(start, board, 'w');
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"whitebishop", imageid);
						Moves.gameMoves.add("whitebishop");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
						done = true;
					}
					else{
						startSearch++;
						continue;
					}
				}
				if (test.getName().equals("whitequeen")){
				//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("whitequeen")){
					ArrayList<Integer> possiblemoves = Moves.getPossibleQueenMoves(start, board, 'w');
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"whiteQueen", imageid);
						Moves.gameMoves.add("whiteQueen");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
						done = true;
					}
					else{
						startSearch++;
						continue;
					}
				}
				if (test.getName().equals("whiteking")){
				//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("whiteking")){
					ArrayList<Integer> possiblemoves = Moves.getPossibleKingMoves(start, board, 'w');
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"whiteKing", imageid);
						Moves.gameMoves.add("whiteKing");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
					 	done = true;
					}
				}
				else{
					startSearch++;
					continue;
				}
			}
		}
		else{
			while(done == false && startSearch < 64){
				start = Moves.getPosition2('b', board, startSearch);
				Item test = (Item)((MyAdapter)board.getAdapter()).getItem(start); 
				//System.out.println(test.getName());
				if (test.getName().equals("blackpawn")){
					//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("whitepawn")){
					//move = Moves.isValidPawnMove("pawn", start, start+8, board, 'w');
					//System.out.println("test");
					/*
				if (move > 0){
					imageid = (int)board.getAdapter().getItemId(start);
					((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
					((MyAdapter) board.getAdapter()).setItem(start+8, board,"whitepawn", imageid);
					done = true;
				}
				else{
					startSearch++;
				}*/
					ArrayList<Integer> possiblemoves = Moves.getPossiblePawnMoves(start, board, 'b');
					System.out.println(possiblemoves);
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"blackpawn", imageid);
						Moves.gameMoves.add("blackpawn");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
						done = true;
					}
					else{ 
						startSearch++;
						continue;
					}

				}
				if (test.getName().equals("blackrook")){
					//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("rook")){
					System.out.println("got here");
					ArrayList<Integer> possiblemoves = Moves.getPossibleRookMoves(start, board, 'b');
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"blackrook", imageid);
						Moves.gameMoves.add("blackrook");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
						done = true;
					}
					else{
						startSearch++;
						continue;
					}
				}
				if (test.getName().equals("blackknight")){
					//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("blackknight")){
					System.out.println("got here");
					ArrayList<Integer> possiblemoves = Moves.getPossibleKnightMoves(start, board, 'b');
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"blackknight", imageid);
						Moves.gameMoves.add("blackknight");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
						done = true;
					}
					else{
						startSearch++;
						continue;
					}
				}
				if (test.getName().equals("blackbishop")){
					//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("blackbishop")){
					ArrayList<Integer> possiblemoves = Moves.getPossibleBishopMoves(start, board, 'b');
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"blackbishop", imageid);
						Moves.gameMoves.add("blackbishop");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
						done = true;
					}
					else{
						startSearch++;
						continue;
					}
				}
				if (test.getName().equals("blackqueen")){
				//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("blackqueen")){
					ArrayList<Integer> possiblemoves = Moves.getPossibleQueenMoves(start, board, 'b');
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"blackQueen", imageid);
						Moves.gameMoves.add("blackQueen");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
						done = true;
					}
					else{
						startSearch++;
						continue;
					}
				}
				if (test.getName().equals("blackking")){
				//if (((Item)((MyAdapter)board.getAdapter()).getItem(start)).equals("blackking")){
					ArrayList<Integer> possiblemoves = Moves.getPossibleKingMoves(start, board, 'b');
					if (possiblemoves.size()!=0){
						int dest = possiblemoves.get((int)Math.random());
						imageid = (int)board.getAdapter().getItemId(start);
						((MyAdapter) board.getAdapter()).setItem(start, board,"blank", R.drawable.blank);
						((MyAdapter) board.getAdapter()).setItem(dest, board,"blackKing", imageid);
						Moves.gameMoves.add("blackKing");
						Moves.gameMoves.add(Integer.toString(start));
						Moves.gameMoves.add(Integer.toString(dest));
						Moves.player++;
						done = true;
					}
				}
				else{
					startSearch++;
					continue;
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	public void cancelDraw(){
		drawd.cancel();
	}
	
	public void Draw(View view, GridView board) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getBaseContext());
		alertDialogBuilder.setTitle("Opponent Wishes to Resign");
		alertDialogBuilder
		.setMessage("Accept Resignation?")
		.setCancelable(false)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				Moves.resetGames();
				cancelDraw();
				Intent n = new Intent(PlayGameActivity.this, MainActivity.class);
				startActivity(n);
			}
		})
		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				cancelDraw();
			}
		});
		
		drawd = alertDialogBuilder.create();
		drawd.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		drawd.show();
		
		
	}
	
	public void startProgress(final View v, final int position) {
		// do something long
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(
							getBaseContext());
					alertDialogBuilder1.setTitle("King in check");

					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							getBaseContext());
					alertDialogBuilder.setTitle("Invalid Move");
					alertDialogBuilder
					.setMessage("Cannot move piece there!")
					.setCancelable(false)
					.setPositiveButton("Okay",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							cancelDialog();
						}
					});

					AlertDialog.Builder gameOverBuilder = new AlertDialog.Builder(
							getBaseContext());
					gameOverBuilder.setTitle("Game Over");
					gameOverBuilder
					.setMessage("Cannot move piece there!")
					.setCancelable(false)
					.setPositiveButton("Save",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							cancelDialog4();
							System.out.println("SIZE FROM IN PLAYGAMEACTIITY: "+Moves.getGameMoves().size());
							addGame2(Moves.getGameMoves());
						}
					})
					.setNegativeButton("Main Menu",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							cancelDialog4();
							Intent intent = new Intent(PlayGameActivity.this, MainActivity.class);
							startActivity(intent);
						}
					});

					AlertDialog.Builder dangerBuilder = new AlertDialog.Builder(
							getBaseContext());
					dangerBuilder.setTitle("Invalid Move");
					dangerBuilder
					.setMessage("Your king will be in check!")
					.setCancelable(false)
					.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							cancelDialog3();
						}
					});
					danger = dangerBuilder.create();
					danger.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
					incheck = alertDialogBuilder1.create();
					incheck.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
					alertDialog = alertDialogBuilder.create();
					alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
					AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(
							getBaseContext());
					alertDialogBuilder2.setTitle("Promoted! \nSelect piece:");

						int b = Moves.onItemClick(v,position);
						
						
						if (Moves.whitewins==true){
							Moves.whitewins=false;
							System.out.println("changing whitewins back to: "+Moves.whitewins);
							gameon=false;
							gameOverBuilder.setMessage("White wins!");
							gameover = gameOverBuilder.create();
							gameover.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
							gameover.show();
						}
						if (Moves.blackwins==true){
							gameon=false;
							Moves.blackwins=false;
							//System.out.println("changing blackwins back to: "+Moves.blackwins);
							gameOverBuilder.setMessage("Black wins!");
							gameover = gameOverBuilder.create();
							gameover.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
							gameover.show();
						}
						if (Moves.blackincheck==true){
							Moves.blackincheck = false;
							incheck.setMessage("Black king in check by "+Moves.danger);
							incheck.show();

						}
						if(Moves.whiteincheck==true){
							Moves.whiteincheck = false;
							incheck.setMessage("White king in check by "+Moves.danger);
							incheck.show();

						}

						if(b==1){
							alertDialog.show();
						}

						if (b==-5){
							System.out.println("went here in pga b==-5");
							Moves.dangerPotential=false;
							Moves.i=0;
							danger.show();
						}
						if(b==-23){
							alertDialog.show();
						}
						if(b==2){
							Moves.whitepromoted=false;
							Moves.blackpromoted=false;
							alertDialogBuilder2.setItems(new CharSequence[]
									{"Queen", "Knight", "Bishop", "Rook"},
									new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									// The 'which' argument contains the index position
									// of the selected item
									switch (which) {
									case 0:
										((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whitequeen", R.drawable.whitequeen);
										chessboardGridView.invalidateViews();
										Moves.getGameMoves().add("whitepawnpromo");
										Moves.getGameMoves().add(Integer.toString(position));
										Moves.getGameMoves().add("whitequeen");
										break;
									case 1:
										((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whiteknight", R.drawable.whiteknight);
										chessboardGridView.invalidateViews();
										Moves.getGameMoves().add("whitepawnpromo");
										Moves.getGameMoves().add(Integer.toString(position));
										Moves.getGameMoves().add("whiteknight");
										break;
									case 2:
										((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whitebishop", R.drawable.whitebishop);
										chessboardGridView.invalidateViews();
										Moves.getGameMoves().add("whitepawnpromo");
										Moves.getGameMoves().add(Integer.toString(position));
										Moves.getGameMoves().add("whitebishop");
										break;
									case 3:
										((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whiterook", R.drawable.whiterook);
										chessboardGridView.invalidateViews();
										Moves.getGameMoves().add("whitepawnpromo");
										Moves.getGameMoves().add(Integer.toString(position));
										Moves.getGameMoves().add("whiterook");
										break;
									}
								}
							});


							promotion = alertDialogBuilder2.create();
							promotion.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

							promotion.show();


						}
						if(b==3){
							System.out.println("returned 3");
							Moves.whitepromoted=false;
							Moves.blackpromoted=false;
							alertDialogBuilder2.setItems(new CharSequence[]
									{"Queen", "Knight", "Bishop", "Rook"},
									new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									// The 'which' argument contains the index position
									// of the selected item
									switch (which) {
									case 0:
										((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"blackqueen", R.drawable.blackqueen);
										chessboardGridView.invalidateViews();
										Moves.getGameMoves().add("blackpawnpromo");
										Moves.getGameMoves().add(Integer.toString(position));
										Moves.getGameMoves().add("blackqueen");
										break;
									case 1:
										((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"blackknight", R.drawable.blackknight);
										chessboardGridView.invalidateViews();
										Moves.getGameMoves().add("blackpawnpromo");
												Moves.getGameMoves().add(Integer.toString(position));
												Moves.getGameMoves().add("blackknight");
												
										break;
									case 2:
										((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"blackbishop", R.drawable.blackbishop);
										chessboardGridView.invalidateViews();
										Moves.getGameMoves().add("blackpawnpromo");
										Moves.getGameMoves().add(Integer.toString(position));
										Moves.getGameMoves().add("blackbishop");
									
										break;
									case 3:
										((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"blackrook", R.drawable.blackrook);
										chessboardGridView.invalidateViews();
										Moves.getGameMoves().add("blackpawnpromo");
										Moves.getGameMoves().add(Integer.toString(position));
										Moves.getGameMoves().add("blackrook");
									
										break;
									}
								}
							});


							promotion = alertDialogBuilder2.create();
							promotion.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

							promotion.show();
						}
						if(b==5){

							incheck.setMessage("Black king in check by "+Moves.danger);
							incheck.show();
						}
						else if (b==6){

							incheck.setMessage("White king in check by "+Moves.danger);
							incheck.show();
					
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void addGame(View view) {
		Intent intent = new Intent(this, AddGame.class);
		startActivityForResult(intent, ADD_GAME_CODE);
	
	}
	public void addGame2(ArrayList<String> moves) {
		ViewGames.save=0;
		System.out.println("FROM ADDGAME2: " + moves.size());
		Intent intent = new Intent(this, ViewGames.class);
		Bundle mBundle = new Bundle();
		mBundle.putStringArrayList("pmoves", moves);
		intent.putExtras(mBundle);
		startActivity(intent);
		//System.out.println("CAME BACK AFTER FINISH");
		//System.out.println("GAME NAME WAS: "+games.get(0).getName());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_game, menu);
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


