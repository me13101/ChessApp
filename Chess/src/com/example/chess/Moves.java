package com.example.chess;

import java.util.ArrayList;



import java.util.concurrent.ExecutionException;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Moves {
	//mark spot true if spot on board is currently made enpassant
	//set back to false after turn if capture not taken
	static boolean[] whitepassant = new boolean[64];
	static boolean[] blackpassant = new boolean[64];
	static int enpassantMove = -1;
	static int enpassantCanCapture = -1;
	//set to true when enpassant move made
	//remember to set to false if turn changed
	static boolean enpassantCapture = false;
	static boolean captureMade = false;
	static boolean whitepromoted = false;
	static boolean blackpromoted =false;
	static boolean blackincheck = false;
	static boolean whiteincheck = false;
	static boolean dangerPotential = false;
	static int blackkingindex =60;
	static int whitekingindex=4;
	static boolean castled = false;//check for whether a move is castling or not
	static boolean plus = false, minus = false;
	static String danger = "none";
	static boolean whitewins = false;
	static boolean blackwins = false;
	static boolean WKcanCastle = true;
	static boolean BKcanCastle = true;
	static ArrayList<String> gameMoves = new ArrayList<String>();
	public static int player = 0;
	
	public static String lastpiece;
	public static int lastfirst;
	public static int lastdest;
	public static int lastp;
	
	public static ArrayList<String> getGameMoves(){
		return gameMoves;
	}

	public static void resetGames(){
		gameMoves.clear();
	}
	
	public static String isKingInCheck(char c, GridView board){
		int move = 0;
		Item item;
		if (c=='b'){
			//up
			boolean up = true;
			if (blackkingindex<=7){
				up = false;
			}
			move = blackkingindex - 8;
			while (up){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("UP: move "+move+" was a "+item.getName());
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("whiterook")){
								return "whiterook";
							}
							else if (item.getName().equals("whitequeen")){
								return "whitequeen";
							}
							else{
								up=false;
							}
						}else{
							up = false; 
						}
					}
					move = move - 8;

				}
				else{
					up = false;
				}


			}

			//down
			boolean down = true;
			if (blackkingindex>=56){
				down = false;
			}
			move = blackkingindex + 8;
			//System.out.println("DOWN MOVE: "+move);
			while (down){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("whiterook")){
								return "whiterook";
							}
							else if (item.getName().equals("whitequeen")){
								return "whitequeen";
							}
							else{
								down=false;
							}
						}else{
							down = false; 
						}
					}



					move = move + 8;

				}
				else{
					down = false;
				}


			}

			//left
			boolean left = true;
			if (blackkingindex==0||blackkingindex==8||blackkingindex==16||blackkingindex==24||blackkingindex==32||blackkingindex==40||blackkingindex==48||blackkingindex==56){
				left = false;
			}
			move = blackkingindex - 1;
			while (left){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("whiterook")){
								return "whiterook";
							}
							else if (item.getName().equals("whitequeen")){
								return "whitequeen";
							}
							else{
								left=false;
							}
						}else{
							left = false; 
						}
					}


					if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
						left = false;
					}

					move = move - 1;

				}
				else{
					left = false;
				}


			}

			//right
			boolean right = true;
			if (blackkingindex==7||blackkingindex==15||blackkingindex==23||blackkingindex==31||blackkingindex==39||blackkingindex==47||blackkingindex==55||blackkingindex==63){
				right = false;
			}
			move = blackkingindex + 1;
			while (right){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("whiterook")){
								return "whiterook";
							}
							else if (item.getName().equals("whitequeen")){
								return "whitequeen";
							}
							else{
								right=false;
							}
						}else{
							right = false; 
						}
					}


					if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
						right = false;
					}


					move = move + 1;

				}
				else{
					right = false;
				}


			}


			//up diagonal right
			boolean udr = true;
			if (blackkingindex==7||blackkingindex==15||blackkingindex==23||blackkingindex==31||blackkingindex==39||blackkingindex==47||blackkingindex==55||blackkingindex==63){
				udr = false;
			}
			move = blackkingindex - 7;
			while (udr){
				if (move>=0 && move<=63){

					item = ((Item) board.getAdapter().getItem(move));
					if (move == blackkingindex - 7){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("whitepawn")){
								return "whitepawn";
							}
						}else{
							udr = false; 
						}
					}
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("whitebishop")){
								return "whitebishop";
							}
							else if (item.getName().equals("whitequeen")){
								return "whitequeen";
							}
							else{
								udr=false;
							}
						}else{
							udr = false; 
						}
					}

					if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
						udr = false;
					}

					move = move -7;



				}
				else{
					udr = false;
				}


			}

			//up diagonal left
			boolean udl = true;
			if (blackkingindex==0||blackkingindex==8||blackkingindex==16||blackkingindex==24||blackkingindex==32||blackkingindex==40||blackkingindex==48||blackkingindex==56){
				udl = false;
			}
			move = blackkingindex - 9;
			while (udl){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					if (move == blackkingindex - 9){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("whitepawn")){
								return "whitepawn";
							}

						}else{
							udl = false; 
						}
					}
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("whitebishop")){
								return "whitebishop";
							}
							else if (item.getName().equals("whitequeen")){
								return "whitequeen";
							}
							else{
								udl=false;
							}
						}else{
							udl = false; 
						}
					}


					if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
						udl = false;
					}

					move = move - 9;


				}else{
					udl = false;
				}

			}


			//down diagonal left
			//System.out.println("START : "+start);

			boolean ull = true;
			//System.out.println("ULL START:");
			if (blackkingindex==0||blackkingindex==8||blackkingindex==16||blackkingindex==24||blackkingindex==32||blackkingindex==40||blackkingindex==48||blackkingindex==56){
				ull = false;
			}
			move = blackkingindex+7;
			while (ull){	

				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("whitebishop")){
								return "whitebishop";
							}
							else if (item.getName().equals("whitequeen")){
								return "whitequeen";
							}
							else{
								ull=false;
							}
						}else{
							ull = false; 
						}
					}
					if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
						ull = false;
					}

					move = move + 7;

				}else{
					ull = false;
				}

			}
			//down diagonal right
			//System.out.println("START : "+start);
			//System.out.println("STARTING DDR:");
			boolean ddr = true;
			if (blackkingindex==7||blackkingindex==15||blackkingindex==23||blackkingindex==31||blackkingindex==39||blackkingindex==47||blackkingindex==55||blackkingindex==63){
				ddr = false;
			}
			move = blackkingindex + 9;
			while (ddr){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("whitebishop")){
								return "whitebishop";
							}
							else if (item.getName().equals("whitequeen")){
								return "whitequeen";
							}
							else{
								ddr=false;
							}
						}else{
							ddr = false; 
						}
					}

					if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
						ddr = false;
					}



					move = move + 9;


				}
				else{
					ddr = false;
				}


			}
			return "none";
		}else{
			//up
			boolean up = true;
			if (whitekingindex<=7){
				up = false;
			}
			move = whitekingindex - 8;
			while (up){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("UP: move "+move+" was a "+item.getName());
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("blackrook")){
								return "blackrook";
							}
							else if (item.getName().equals("blackqueen")){
								return "blackqueen";
							}
							else{
								up = false;
							}
						}else{
							up = false; 
						}
					}
					move = move - 8;

				}
				else{
					up = false;
				}


			}

			//down
			boolean down = true;
			if (whitekingindex>=56){
				down = false;
			}
			move = whitekingindex + 8;
			while (down){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("DOWN MOVE: "+move + " was a "+item.getName());
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("blackrook")){
								return "blackrook";
							}
							else if (item.getName().equals("blackqueen")){
								return "blackqueen";
							}
							else{
								down = false;
							}
						}else{
							down = false; 
						}
					}



					move = move + 8;

				}
				else{
					down = false;
				}


			}

			//left
			boolean left = true;
			if (whitekingindex==0||whitekingindex==8||whitekingindex==16||whitekingindex==24||whitekingindex==32||whitekingindex==40||whitekingindex==48||whitekingindex==56){
				left = false;
			}
			move = whitekingindex - 1;
			while (left){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("blackrook")){
								return "blackrook";
							}
							else if (item.getName().equals("blackqueen")){
								return "blackqueen";
							}
							else{
								left = false;
							}
						}else{
							left = false; 
						}
					}


					if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
						left = false;
					}

					move = move - 1;

				}
				else{
					left = false;
				}


			}

			//right
			boolean right = true;
			if (whitekingindex==7||whitekingindex==15||whitekingindex==23||whitekingindex==31||whitekingindex==39||whitekingindex==47||whitekingindex==55||whitekingindex==63){
				right = false;
			}
			move = whitekingindex + 1;
			while (right){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("blackrook")){
								return "blackrook";
							}
							else if (item.getName().equals("blackqueen")){
								return "blackqueen";
							}
							else{
								right = false;
							}
						}else{
							right = false; 
						}
					}


					if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
						right = false;
					}


					move = move + 1;

				}
				else{
					right = false;
				}


			}


			//up diagonal right
			boolean udr = true;
			if (whitekingindex==7||whitekingindex==15||whitekingindex==23||whitekingindex==31||whitekingindex==39||whitekingindex==47||whitekingindex==55||whitekingindex==63){
				udr = false;
			}
			move = whitekingindex - 7;
			while (udr){
				if (move>=0 && move<=63){

					item = ((Item) board.getAdapter().getItem(move));

					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("blackbishop")){
								System.out.println("black bishop was at: "+move);
								return "blackbishop";
							}
							else if (item.getName().equals("blackqueen")){
								return "blackqueen";
							}
							else{
								udr = false;
							}
						}else{
							udr = false; 
						}
					}

					if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
						udr = false;
					}

					move = move -7;



				}
				else{
					udr = false;
				}


			}

			//up diagonal left
			boolean udl = true;
			if (whitekingindex==0||whitekingindex==8||whitekingindex==16||whitekingindex==24||whitekingindex==32||whitekingindex==40||whitekingindex==48||whitekingindex==56){
				udl = false;
			}
			move = whitekingindex - 9;
			while (udl){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("blackbishop")){

								System.out.println("black bishop was at: "+move);
								return "blackbishop";
							}
							else if (item.getName().equals("blackqueen")){
								return "blackqueen";
							}
							else{
								udl = false;
							}
						}else{
							udl = false; 
						}
					}


					if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
						udl = false;
					}

					move = move - 9;


				}else{
					udl = false;
				}

			}


			//down diagonal left
			//System.out.println("START : "+start);

			boolean ull = true;
			//System.out.println("ULL START:");
			if (whitekingindex==0||whitekingindex==8||whitekingindex==16||whitekingindex==24||whitekingindex==32||whitekingindex==40||whitekingindex==48||whitekingindex==56){
				ull = false;
			}
			move = whitekingindex+7;
			while (ull){	

				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					if (move == whitekingindex + 7){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("blackpawn")){
								return "blackpawn";
							}

						}else{
							ull = false; 
						}
					}
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("blackbishop")){

								System.out.println("black bishop was at: "+move);
								return "blackbishop";
							}
							else if (item.getName().equals("blackqueen")){
								return "blackqueen";
							}
							else{
								ull = false;
							}
						}else{
							ull = false; 
						}
					}
					if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
						ull = false;
					}

					move = move + 7;

				}else{
					ull = false;
				}

			}
			//down diagonal right
			//System.out.println("START : "+start);
			//System.out.println("STARTING DDR:");
			boolean ddr = true;
			if (whitekingindex==7||whitekingindex==15||whitekingindex==23||whitekingindex==31||whitekingindex==39||whitekingindex==47||whitekingindex==55||whitekingindex==63){
				ddr = false;
			}
			move = whitekingindex + 9;
			while (ddr){
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					if (move == whitekingindex + 9){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("blackpawn")){
								return "blackpawn";
							}
						}else{
							ddr = false; 
						}
					}
					if (!item.getName().equals("blank")){
						if (item.getName().charAt(0)!=c){
							if (item.getName().equals("blackbishop")){

								System.out.println("black bishop was at: "+move);
								return "blackbishop";
							}
							else if (item.getName().equals("blackqueen")){
								return "blackqueen";
							}
							else{
								ddr = false;
							}
						}else{
							ddr = false; 
						}
					}

					if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
						ddr = false;
					}



					move = move + 9;


				}
				else{
					ddr = false;
				}


			}
			return "none";}
	}

	//castling method that checks spaces between the king and the rook for other pieces
	public static boolean castling(int start, int dest, GridView board, char c){
		//System.out.println(dest);
		if (dest == 0){
			int temp = start-1;
			while (temp > 0){
				Item blankspace = (Item)board.getAdapter().getItem(temp);
				if (!blankspace.getName().equals("blank")){
					//System.out.println("here"+blankspace.getName());
					return false;
				}
				temp--;
			}
		}
		if (dest == 7){
			int temp = start+1;
			while (temp < 7){
				Item blankspace = (Item)board.getAdapter().getItem(temp);
				if (!blankspace.getName().equals("blank")){
					//System.out.println("here7"+blankspace.getName());
					return false;
				}
				temp++;
			}
		}
		if (dest == 56){
			//System.out.println(start);
			int temp = start-1;
			while (temp > 56){
				Item blankspace = (Item)board.getAdapter().getItem(temp);
				if (!blankspace.getName().equals("blank")){
					//System.out.println(blankspace.getName());
					return false;
				}
				temp--;
			}
		}
		if (dest == 63){
			int temp = start+1;
			while (temp < 63){
				Item blankspace = (Item)board.getAdapter().getItem(temp);
				if (!blankspace.getName().equals("blank")){
					return false;
				}
				temp++;
			}
		}
		return true;
	}

	public static boolean isValidMove(String piece, int start, int dest, GridView board, char c){
		//int player = 0;
		ArrayList<Integer> validmoves;
		if (start>dest){
			minus = true;
		}
		if (start<dest){
			plus= true;
		}
		if (piece.equals("bishop")){
			int pos;
			validmoves = getPossibleBishopMoves(start, board, c);
			if (validmoves.contains(dest)){
				Item item = (Item)((MyAdapter)board.getAdapter()).getItem(start);
				((MyAdapter)board.getAdapter()).setItem(start, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
				if(isKingInCheck(c, board).equals("none")){
					System.out.println("facking up here");
					((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
					if (c=='w'){
						gameMoves.add("whitebishop");} 
					else{
						gameMoves.add("blackbishop");
					}
					gameMoves.add(Integer.toString(start));
					gameMoves.add(Integer.toString(dest));
					player++;
					return true;}
				else{  
					String checker;
					//System.out.println("test: "+getPossibleBishopMoves(25, board, 'b'));
					if (c == 'w'){
						checker = isKingInCheck('w', board);
						//System.out.println(checker);
						if (checker.equals("blackbishop")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleBishopMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitebishop");} 
								else{
									gameMoves.add("blackbishop");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackqueen")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleQueenMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitebishop");} 
								else{
									gameMoves.add("blackbishop");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackking")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKingMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackknight")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKnightMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackrook")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleRookMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
					}
					
					
					else{
						
						
						checker = isKingInCheck('b', board);
						//System.out.println(checker);
						if (checker.equals("whitebishop")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleBishopMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whitequeen")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleQueenMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiteking")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKingMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiteknight")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKnightMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiterook")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleRookMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}

						else{
							((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
							dangerPotential = true;
							return false;}
					}
				}
				((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
				dangerPotential = true;
			}
			else{
				
				return false;
			}

		}

		else if (piece.equals("king")){
			validmoves = getPossibleKingMoves(start, board, c);
			if (validmoves.contains(dest)){
				if ((start == 4 || start == 60) && (dest == 56 || dest == 63 || dest == 0 || dest == 7)){
					castled = true;
					//System.out.println("castled in valid: "+castled);
				}
				if (c=='w'){
					gameMoves.add("whiteking");} 
				else{
					gameMoves.add("blackking");
				}
				gameMoves.add(Integer.toString(start));
				gameMoves.add(Integer.toString(dest));
				player++;
				return true;
			}
			else{
				return false;
			}
		}


		else if (piece.equals("queen")){
			int pos;
			validmoves = getPossibleQueenMoves(start, board, c);
			if (validmoves.contains(dest)){
				//System.out.println("got here1111");
				Item item = (Item)((MyAdapter)board.getAdapter()).getItem(start);
				((MyAdapter)board.getAdapter()).setItem(start, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);

				if(isKingInCheck(c, board).equals("none")){
					((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
					if (c=='w'){
						gameMoves.add("whitequeen");} 
					else{
						gameMoves.add("blackqueen");
					}
					gameMoves.add(Integer.toString(start));
					gameMoves.add(Integer.toString(dest));
					player++;
					return true;}

				else{  
					String checker;
					//System.out.println("test: "+getPossibleBishopMoves(25, board, 'b'));
					if (c == 'w'){
						checker = isKingInCheck('w', board);
						//System.out.println(checker);
						if (checker.equals("blackbishop")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleBishopMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackqueen")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleQueenMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackking")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKingMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackknight")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKnightMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackrook")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleRookMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
					}
					
					
					else{
						
						
						checker = isKingInCheck('b', board);
						//System.out.println(checker);
						if (checker.equals("whitebishop")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleBishopMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whitequeen")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleQueenMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiteking")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKingMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiteknight")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKnightMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiterook")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleRookMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}

						else{
							((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
							dangerPotential = true;
							return false;}
					}
				}
				((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
				dangerPotential = true;
			}
			else{
				return false;
			}

		}
		else if (piece.equals("rook")){
			int pos;
			validmoves = getPossibleRookMoves(start, board, c);
			if (validmoves.contains(dest)){
				Item item = (Item)((MyAdapter)board.getAdapter()).getItem(start);
				((MyAdapter)board.getAdapter()).setItem(start, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
				if(isKingInCheck(c, board).equals("none")){
					((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
					if (c=='w'){
						gameMoves.add("whiterook");} 
					else{
						gameMoves.add("blackrook");
					}
					gameMoves.add(Integer.toString(start));
					gameMoves.add(Integer.toString(dest));
					player++;
					return true;}
				else{  
					String checker;
					//System.out.println("test: "+getPossibleBishopMoves(25, board, 'b'));
					if (c == 'w'){
						checker = isKingInCheck('w', board);
						//System.out.println(checker);
						if (checker.equals("blackbishop")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleBishopMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackqueen")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleQueenMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackking")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKingMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackknight")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKnightMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackrook")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleRookMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
					}
					
					
					else{
						
						
						checker = isKingInCheck('b', board);
						//System.out.println(checker);
						if (checker.equals("whitebishop")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleBishopMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whitequeen")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleQueenMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiteking")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKingMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiteknight")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKnightMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiterook")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleRookMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}

						else{
							((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
							dangerPotential = true;
							return false;}
					}
				}
				((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
				dangerPotential = true;
			}
			else{
				return false;
			}

		}
		else if (piece.equals("knight")){
			int pos;
			validmoves = getPossibleKnightMoves(start, board, c);
			if (validmoves.contains(dest)){
				Item item = (Item)((MyAdapter)board.getAdapter()).getItem(start);
				((MyAdapter)board.getAdapter()).setItem(start, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
				if(isKingInCheck(c, board).equals("none")){
					((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
					if (c=='w'){
						gameMoves.add("whiteknight");} 
					else{
						gameMoves.add("blackknight");
					}
					gameMoves.add(Integer.toString(start));
					gameMoves.add(Integer.toString(dest));
					player++;
					return true;}
				else{  
					String checker;
					//System.out.println("test: "+getPossibleBishopMoves(25, board, 'b'));
					if (c == 'w'){
						checker = isKingInCheck('w', board);
						//System.out.println(checker);
						if (checker.equals("blackbishop")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleBishopMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackqueen")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleQueenMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackking")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKingMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackknight")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKnightMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("blackrook")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleRookMoves(pos, board, 'b').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
					}
					
					
					else{
						
						
						checker = isKingInCheck('b', board);
						//System.out.println(checker);
						if (checker.equals("whitebishop")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleBishopMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whitequeen")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleQueenMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiteking")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKingMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiteknight")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleKnightMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}
						if (checker.equals("whiterook")){
							pos = getPosition(checker, board, 0);
							//System.out.println("pos: "+pos);
							if (getPossibleRookMoves(pos, board, 'w').contains(dest)){
								if (c=='w'){
									gameMoves.add("whitequeen");} 
								else{
									gameMoves.add("blackqueen");
								}
								gameMoves.add(Integer.toString(start));
								gameMoves.add(Integer.toString(dest));
								player++;
								return true;
							}
						}

						else{
							((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
							dangerPotential = true;
							return false;}
					}
				}
				((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
				dangerPotential = true;
			}
			else{
				return false;
			}

		}
		return false;}

	public static int isValidPawnMove(String piece, int start, int dest, GridView board, char c){
		ArrayList<Integer> validmoves = new ArrayList<Integer>();
		if (piece.equals("pawn")){
			validmoves = getPossiblePawnMoves(start, board, c);
			if (validmoves.contains(dest)){
				Item item = (Item)((MyAdapter)board.getAdapter()).getItem(start);
				((MyAdapter)board.getAdapter()).setItem(start, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
				//if(isKingInCheck(c, board).equals("none")){
					((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());

					if (c=='w'&&dest==blackkingindex){
						whitewins=true;
						
					}
					else if (c=='b' && dest==whitekingindex){
						blackwins=true;
					}
					
					if (dest>=56){
						whitepromoted = true;
					}
					if (dest<=7){
						blackpromoted=true;
					}

					if (dest==enpassantMove){
						//System.out.println("MADE AN ENPASSANT DOUBLE FOWARD");
						//made enpassant double forward move
						if (c=='w'){
							whitepassant[dest]=true;
							enpassantCapture=true;
						}else{
							blackpassant[dest]=true;
							enpassantCapture=true;
						}
					}
					else{
						//did not make enpassant move

						//System.out.println("MADE NORMAL ONE FORWARD");
						enpassantCapture = false;
					}
					//see if made enpassant capture
					if (dest==enpassantCanCapture){
						//System.out.println("MADE A ENPASSANT CAPTURE");
						if (c=='w'){
							whitepassant[dest]=false;
							enpassantCapture=false;
							player++;
							return dest-8;
						}else{
							blackpassant[dest]=false;
							enpassantCapture=false;
							player++;
							return dest+8;
						}
					}else{
						//System.out.println("enpassantCanCaptre: " +enpassantCanCapture);
						//System.out.println("dest: "+dest);
						//System.out.println("DID NOT MAKE AN ENPASSANT CAPTURE");
						enpassantCanCapture = -1;
						if (c=='w'){
							whitepassant[dest]=false;
						}else{
							blackpassant[dest]=false;
						}
					}

					//System.out.println("enpassantCapture = "+enpassantCapture);
					player++;
					return dest;
				/*}
				else{
					System.out.println("ROOT OF THE PROBLEM STARTS HERE");
					System.out.println("Start: "+start);
					((MyAdapter)board.getAdapter()).setItem(start, board, item.getName(), item.getDrawable());
					return -5;}

*/
			}
			else{
				return -1;
			}
		}

		return -1;
	}


	public static boolean isSpotSafe(int spotcheck, char c){
		if (c=='w'){
			int save = whitekingindex;
			whitekingindex = spotcheck;
			checkKings();
			if (whiteincheck==true){
				whiteincheck=false;
				whitekingindex = save;
				return false;
			}
			else{
				whitekingindex = save;
				return true;
			}
		}else{
			int save = blackkingindex;
			blackkingindex = spotcheck;
			checkKings();
			if (blackincheck==true){
				blackincheck=false;
				blackkingindex = save;
				return false;
			}
			else{
				blackkingindex = save;
				return true;
			}
		}

	}



	public static ArrayList<Integer> getPossiblePawnMoves(int start, GridView board, char c){
		ArrayList<Integer> possiblemoves = new ArrayList<Integer>();
		Item item;
		int move = 0;

		if (c=='w'){
			//white pawn
			if (start>=16){
				//System.out.println("WHITE PAWN WAS ALREADY MOVED!");
				//pawn was already moved, cannot enpassant
				//remember to add in enpassant CAPTURES
				move = start + 8;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().equals("blank")){
						possiblemoves.add(move);

					}

				}

				move = start + 9;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().charAt(0)!=c&&!item.getName().equals("blank")){
						possiblemoves.add(move);
					}

				}	

				move = start + 7;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().charAt(0)!=c&&!item.getName().equals("blank")){
						possiblemoves.add(move);

					}
				}	

				if (enpassantCapture==true){
					//System.out.println("POSSIBILITY TO PASSANT CAPTURE BLACK PIECE");
					move = start + 1;
					if (move>=0 && move<=63){
						item = ((Item) board.getAdapter().getItem(move));
						//System.out.println("MOVE "+move+" WAS A "+item.getName());
						if (item.getName().equals("blackpawn")||blackpassant[move]==true){
							possiblemoves.add(move+8);
							enpassantCanCapture=move+8;
						}
					}
					move = start - 1;
					if (move>=0 && move<=63){
						item = ((Item) board.getAdapter().getItem(move));
						//System.out.println("MOVE "+move+" WAS A "+item.getName());
						if (item.getName().equals("blackpawn")||blackpassant[move]==true){
							possiblemoves.add(move+8);

							enpassantCanCapture=move+8;
						}
					}

				}


			}else{

				//	System.out.println("WHITE PAWN HAS NEVER BEEN MOVED!");
				//en passant possible
				//remember to add in enpassant CAPTURES
				move = start + 8;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().equals("blank")){
						possiblemoves.add(move);
					}

				}
				move = start + 16;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
						possiblemoves.add(move);
						enpassantMove = (move);
					}
				}

				move = start + 9;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().charAt(0)!=c&&!item.getName().equals("blank")){
						possiblemoves.add(move);
					}

				}	

				move = start + 7;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().charAt(0)!=c&&!item.getName().equals("blank")){
						possiblemoves.add(move);
					}

				}	

				if (enpassantCapture==true){
					//System.out.println("POSSIBILITY TO PASSANT CAPTURE BLACK PIECE");

					move = start + 1;
					if (move>=0 && move<=63){
						item = ((Item) board.getAdapter().getItem(move));
						//System.out.println("MOVE "+move+" WAS A "+item.getName());
						if (item.getName().equals("blackpawn")||blackpassant[move]==true){
							possiblemoves.add(move+8);

							enpassantCanCapture=move+8;
						}
					}
					move = start - 1;
					if (move>=0 && move<=63){
						item = ((Item) board.getAdapter().getItem(move));
						//System.out.println("MOVE "+move+" WAS A "+item.getName());
						if (item.getName().equals("blackpawn")||blackpassant[move]==true){
							possiblemoves.add(move+8);

							enpassantCanCapture=move+8;
						}
					}

				}
			}

		}else{
			//black pawn
			if (start<48){
				//pawn was already moved, cannot enpassant
				//remember to add in enpassant CAPTURES
				move = start - 8;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().equals("blank")){
						possiblemoves.add(move);
					}

				}

				move = start - 9;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().charAt(0)!=c&&!item.getName().equals("blank")){
						possiblemoves.add(move);
					}
				}	

				move = start - 7;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().charAt(0)!=c&&!item.getName().equals("blank")){
						possiblemoves.add(move);
					}

				}	

				if (enpassantCapture==true){
					move = start + 1;
					if (move>=0 && move<=63){
						item = ((Item) board.getAdapter().getItem(move));
						//System.out.println("MOVE "+move+" WAS A "+item.getName());
						if (item.getName().equals("whitepawn")||whitepassant[move]==true){
							possiblemoves.add(move-8);

							enpassantCanCapture=move-8;
						}
					}
					move = start - 1;
					if (move>=0 && move<=63){
						item = ((Item) board.getAdapter().getItem(move));
						//System.out.println("MOVE "+move+" WAS A "+item.getName());
						if (item.getName().equals("whitepawn")||whitepassant[move]==true){
							possiblemoves.add(move-8);

							enpassantCanCapture=move-8;
						}
					}

				}


			}else{
				//en passant possible
				//remember to add in enpassant CAPTURES
				move = start - 8;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().equals("blank")){
						possiblemoves.add(move);
					}

				}
				move = start - 16;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
						possiblemoves.add(move);
						enpassantMove = (move);
					}
				}

				move = start - 9;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().charAt(0)!=c&&!item.getName().equals("blank")){
						possiblemoves.add(move);
					}

				}	

				move = start - 7;
				if (move>=0 && move<=63){
					item = ((Item) board.getAdapter().getItem(move));
					//System.out.println("MOVE "+move+" WAS A "+item.getName());
					if (item.getName().charAt(0)!=c&&!item.getName().equals("blank")){
						possiblemoves.add(move);
					}

				}	

				if (enpassantCapture==true){
					move = start + 1;
					if (move>=0 && move<=63){
						item = ((Item) board.getAdapter().getItem(move));
						//System.out.println("MOVE "+move+" WAS A "+item.getName());
						if (item.getName().equals("whitepawn")){
							possiblemoves.add(move-8);

							enpassantCanCapture=move-8;
						}
					}
					move = start - 1;
					if (move>=0 && move<=63){
						item = ((Item) board.getAdapter().getItem(move));
						//System.out.println("MOVE "+move+" WAS A "+item.getName());
						if (item.getName().equals("whitepawn")){
							possiblemoves.add(move-8);

							enpassantCanCapture=move-8;
						}
					}

				}
			}

		}
		System.out.println("Pawn's possible moves: "+possiblemoves);
		return possiblemoves;
	}

	public static ArrayList<Integer> getPossibleKnightMoves(int start, GridView board, char c){
		ArrayList<Integer> possiblemoves = new ArrayList<Integer>();
		Item item;
		int move = 0;
		//System.out.println("START +17: "+start);
		if (start<=48||start!=7||start!=15||start!=23||start!=31||start!=39||start!=47||start!=55||start!=63){
			move = start + 17;
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("UP: move "+move+" was a "+item.getName());
				if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
			}
		}

		//System.out.println("START + 10:"+start);
		if (start<=56||
				start!=62||start!=54||start!=46||start!=38||start!=30||start!=22||start!=14||start!=6||
				start!=7||start!=15||start!=23||start!=31||start!=39||start!=47||start!=55||start!=63){
			move = start + 10;
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("UP: move "+move+" was a "+item.getName());
				if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
			}
		}

		//System.out.println("START + 6:"+start);
		if (start>=8||
				start!=62||start!=54||start!=46||start!=38||start!=30||start!=22||start!=14||start!=6||
				start!=7||start!=15||start!=23||start!=31||start!=39||start!=47||start!=55||start!=63){
			move = start - 6;
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("UP: move "+move+" was a "+item.getName());
				if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
			}
		}

		//System.out.println("START - 15:"+start);
		if (start>=16||start!=7||start!=15||start!=23||start!=31||start!=39||start!=47||start!=55||start!=63){
			move = start - 15;
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("UP: move "+move+" was a "+item.getName());
				if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
			}
		}

		//System.out.println("START + 15:"+start);
		if (start<=48||start!=0||start!=8||start!=16||start!=24||start!=32||start!=40||start!=48||start!=56){
			move = start + 15;
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("UP: move "+move+" was a "+item.getName());
				if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
			}
		}

		//System.out.println("START + 6:"+start);
		if (start<=56||start!=0||start!=8||start!=16||start!=24||start!=32||start!=40||start!=48||start!=56
				||start!=1||start!=9||start!=17||start!=25||start!=33||start!=41||start!=49||start!=57){
			move = start + 6;
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("UP: move "+move+" was a "+item.getName());
				if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
			}
		}

		//System.out.println("START - 10:"+start);
		if (start>=8||start!=0||start!=8||start!=16||start!=24||start!=32||start!=40||start!=48||start!=56
				||start!=1||start!=9||start!=17||start!=25||start!=33||start!=41||start!=49||start!=57){
			move = start - 10;
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("UP: move "+move+" was a "+item.getName());
				if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
			}
		}

		//System.out.println("START - 17:"+start);
		if (start>=16||start!=0||start!=8||start!=16||start!=24||start!=32||start!=40||start!=48||start!=56
				){
			move = start - 17;
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("UP: move "+move+" was a "+item.getName());
				if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
			}
		}

		System.out.println("Knight's possible moves: "+possiblemoves);
		return possiblemoves;
	}
	public static ArrayList<Integer> getPossibleRookMoves(int start, GridView board, char c){
		ArrayList<Integer> possiblemoves = new ArrayList<Integer>();
		Item item;
		int move = 0;
		//System.out.println("START : "+start);

		//up
		boolean up = true;
		if (start<=7){
			up = false;
		}
		move = start - 8;
		while (up){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("UP: move "+move+" was a "+item.getName());
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					up = false;
				}
				else{
					up = false;
				}


				if (move<=7){
					up = false;
				}

				move = move - 8;

			}
			else{
				up = false;
			}


		}

		//down
		boolean down = true;
		if (start>=56){
			down = false;
		}
		move = start + 8;
		//System.out.println("DOWN MOVE: "+move);
		while (down){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					down = false;
				}
				else{
					down = false;
				}


				if (move>=56){
					down = false;
				}

				move = move + 8;

			}
			else{
				down = false;
			}


		}

		//left
		boolean left = true;
		if (start==0||start==8||start==16||start==24||start==32||start==40||start==48||start==56){
			left = false;
		}
		move = start - 1;
		while (left){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					left = false;
				}
				else{
					left = false;
				}


				if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
					left = false;
				}

				move = move - 1;

			}
			else{
				left = false;
			}


		}

		//right
		boolean right = true;
		if (start==7||start==15||start==23||start==31||start==39||start==47||start==55||start==63){
			right = false;
		}
		move = start + 1;
		while (right){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("MOVE "+move+" WAS A "+item.getName());
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					right = false;
				}
				else{
					right = false;
				}


				if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
					right = false;
				}


				move = move + 1;

			}
			else{
				right = false;
			}


		}

		System.out.println("Rook's possible moves: "+possiblemoves);
		return possiblemoves;
	}

	public static void checkKings(){
		String b = isKingInCheck('b', PlayGameActivity.chessboardGridView);
		if (!b.equals("none")){
			System.out.println("Black king was IN check");
			blackincheck=true;
			danger = b; 
		}else{

			System.out.println("Black king was NOT IN check");
			blackincheck=false;
			danger="none";
		}
		String w = isKingInCheck('w', PlayGameActivity.chessboardGridView);
		if (!w.equals("none")){

			System.out.println("White king was IN check");
			whiteincheck=true;
			danger = w;
		}else{

			System.out.println("White king was NOT IN check");
			whiteincheck=false;
			danger="none";
		}

	}

	public static ArrayList<Integer> getPossibleKingMoves(int start, GridView board, char c){ArrayList<Integer> possiblemoves = new ArrayList<Integer>();
	Item item;
	int move = 0;
	//up diagonal right
	move = start - 7;
	if (move>=0 && move<=63){
		if (isSpotSafe(move, c)==true){
			item = ((Item) board.getAdapter().getItem(move));
			if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
				possiblemoves.add(move);
			}
		}
	}

	//up diagonal left
	move = start - 9;
	if (move>=0 && move<=63){
		if (isSpotSafe(move,c)==true){
			item = ((Item) board.getAdapter().getItem(move));
			if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
				possiblemoves.add(move);
			}
		}
	}

	//down diagonal right
	move = start + 9;
	if (move>=0 && move<=63){
		if (isSpotSafe(move,c)==true){
			item = ((Item) board.getAdapter().getItem(move));
			//System.out.println("MOVE "+move+" WAS A "+item.getName());
			if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
				possiblemoves.add(move);
			}

		}
	}

	//down diagonal left
	move = start + 7;
	if (move>=0 && move<=63){
		if (isSpotSafe(move,c)==true){
			item = ((Item) board.getAdapter().getItem(move));
			if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
				possiblemoves.add(move);
			}
		}
	}

	//up
	move = start - 8;
	if (move>=0 && move<=63){
		if (isSpotSafe(move,c)==true){
			item = ((Item) board.getAdapter().getItem(move));
			if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
				possiblemoves.add(move);
			}
		}
	}

	//down
	move = start + 8;
	if (move>=0 && move<=63){
		if (isSpotSafe(move,c)==true){
			item = ((Item) board.getAdapter().getItem(move));
			//System.out.println("MOVE "+move+" WAS A "+item.getName());
			if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
				possiblemoves.add(move);
			}
		}
	}

	//left 
	move = start-1;
	if (move>=0 && move<=63){
		if (isSpotSafe(move,c)==true){
			item = ((Item) board.getAdapter().getItem(move));
			if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
				possiblemoves.add(move);
			}
		}
	}

	//right
	move = start+1;
	if (move>=0 && move<=63){
		if (isSpotSafe(move,c)==true){
			item = ((Item) board.getAdapter().getItem(move));
			if (item.getName().equals("blank")||item.getName().charAt(0)!=c){
				possiblemoves.add(move);
			}
		}

	}
	//check for castling move by start position and destination being a castle in original spots
	boolean castle;
	if (start == 4 || start == 60){
		Item it0 = (Item)board.getAdapter().getItem(0);
		Item it7 = (Item)board.getAdapter().getItem(7);
		Item it56 = (Item)board.getAdapter().getItem(56);
		Item it63 = (Item)board.getAdapter().getItem(63);
		if (c == 'w'){
			if (it0.getName().equals("whiterook")){
				castle = castling(start, 0, board, c);
				//System.out.println(castle);
				if (castle == true){
					possiblemoves.add(0);
				}
			}
			if (it7.getName().equals("whiterook")){
				castle = castling(start, 7, board, c);
				//System.out.println(castle);
				if (castle == true){
					possiblemoves.add(7);
				}
			}
		}
		if (c == 'b'){
			//System.out.println(it56.getName());
			if (it56.getName().equals("blackrook")){
				//System.out.println("got here");
				castle = castling(start, 56, board, c);
				//System.out.println(castle);
				if (castle == true){
					possiblemoves.add(56);
				}
			}
			if (it63.getName().equals("blackrook")){
				castle = castling(start, 63, board, c);
				//System.out.println(castle);
				if (castle == true){
					possiblemoves.add(63);
				}
			}
		}
	}
	//System.out.println("POSSIBLE KING MOVES: "+possiblemoves);

	System.out.println("King's possible moves: "+possiblemoves);
	return possiblemoves;}

	public static ArrayList<Integer> getPossibleQueenMoves(int start, GridView board, char c){
		ArrayList<Integer> possiblemoves = new ArrayList<Integer>();
		Item item;
		int move = 0;


		//up
		boolean up = true;
		if (start<=7){
			up = false;
		}
		move = start - 8;
		while (up){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("UP: move "+move+" was a "+item.getName());
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					up = false;
				}
				else{
					up = false;
				}


				if (move<=7){
					up = false;
				}

				move = move - 8;

			}
			else{
				up = false;
			}


		}

		//down
		boolean down = true;
		if (start>=56){
			down = false;
		}
		move = start + 8;
		//System.out.println("DOWN MOVE: "+move);
		while (down){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					down = false;
				}
				else{
					down = false;
				}


				if (move>=56){
					down = false;
				}

				move = move + 8;

			}
			else{
				down = false;
			}


		}

		//left
		boolean left = true;
		if (start==0||start==8||start==16||start==24||start==32||start==40||start==48||start==56){
			left = false;
		}
		move = start - 1;
		while (left){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					left = false;
				}
				else{
					left = false;
				}


				if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
					left = false;
				}

				move = move - 1;

			}
			else{
				left = false;
			}


		}

		//right
		boolean right = true;
		if (start==7||start==15||start==23||start==31||start==39||start==47||start==55||start==63){
			right = false;
		}
		move = start + 1;
		while (right){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				//System.out.println("MOVE "+move+" WAS A "+item.getName());
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					right = false;
				}
				else{
					right = false;
				}


				if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
					right = false;
				}


				move = move + 1;

			}
			else{
				right = false;
			}


		}


		//up diagonal right
		boolean udr = true;
		if (start==7||start==15||start==23||start==31||start==39||start==47||start==55||start==63){
			udr = false;
		}
		move = start - 7;
		while (udr){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					udr = false;
				}
				else{
					udr = false;
				}


				if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
					udr = false;
				}

				move = move -7;



			}
			else{
				udr = false;
			}


		}

		//up diagonal left
		//System.out.println("START : "+start);
		boolean udl = true;
		if (start==0||start==8||start==16||start==24||start==32||start==40||start==48||start==56){
			udl = false;
		}
		move = start - 9;
		while (udl){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					udl = false;
				}
				else{
					//System.out.println("WAS MY OWN COLOR ");
					udl = false;
				}


				if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
					udl = false;
				}

				move = move - 9;


			}else{
				udl = false;
			}

		}


		//down diagonal left
		//System.out.println("START : "+start);

		boolean ull = true;
		//System.out.println("ULL START:");
		if (start==0||start==8||start==16||start==24||start==32||start==40||start==48||start==56){
			ull = false;
		}
		move = start+7;
		while (ull){	

			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					ull = false;
				}
				else{
					ull = false;
				}

				if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
					ull = false;
				}

				move = move + 7;

			}else{
				ull = false;
			}

		}
		//down diagonal right
		//System.out.println("START : "+start);
		//System.out.println("STARTING DDR:");
		boolean ddr = true;
		if (start==7||start==15||start==23||start==31||start==39||start==47||start==55||start==63){
			ddr = false;
		}
		move = start + 9;
		while (ddr){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					ddr = false;
				}
				else{
					ddr = false;
				}

				if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
					ddr = false;
				}



				move = move + 9;


			}
			else{
				ddr = false;
			}


		}

		//System.out.println("QUEEN'S MOVES: "+possiblemoves);


		System.out.println("Queens possible moves: "+possiblemoves);
		return possiblemoves;
	}


	public static ArrayList<Integer> getPossibleBishopMoves(int start, GridView board, char c){
		ArrayList<Integer> possiblemoves = new ArrayList<Integer>();
		Item item;
		int move = 0;
		//up diagonal right
		boolean udr = true;
		if (start==7||start==15||start==23||start==31||start==39||start==47||start==55||start==63){
			udr = false;
		}
		move = start - 7;
		while (udr){
			if (move>=0 && move<=63){
				//check to see if spot taken by your color or opponents
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					udr = false;
				}
				else{
					udr = false;
				}


				if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
					udr = false;
				}

				move = move -7;



			}
			else{
				udr = false;
			}


		}

		//up diagonal left
		//System.out.println("START : "+start);
		boolean udl = true;
		if (start==0||start==8||start==16||start==24||start==32||start==40||start==48||start==56){
			udl = false;
		}
		move = start - 9;
		while (udl){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					udl = false;
				}
				else{
					//System.out.println("WAS MY OWN COLOR ");
					udl = false;
				}


				if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
					udl = false;
				}

				move = move - 9;


			}else{
				udl = false;
			}

		}


		//down diagonal left
		//System.out.println("START : "+start);

		boolean ull = true;
		//System.out.println("ULL START:");
		if (start==0||start==8||start==16||start==24||start==32||start==40||start==48||start==56){
			ull = false;
		}
		move = start+7;
		while (ull){	

			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					ull = false;
				}
				else{
					ull = false;
				}

				if (move==0||move==8||move==16||move==24||move==32||move==40||move==48||move==56){
					ull = false;
				}

				move = move + 7;

			}else{
				ull = false;
			}

		}
		//down diagonal right
		//System.out.println("START : "+start);
		//System.out.println("STARTING DDR:");
		boolean ddr = true;
		if (start==7||start==15||start==23||start==31||start==39||start==47||start==55||start==63){
			ddr = false;
		}
		move = start + 9;
		while (ddr){
			if (move>=0 && move<=63){
				item = ((Item) board.getAdapter().getItem(move));
				if (item.getName().equals("blank")){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
				}
				else if (item.getName().charAt(0)!=c){
					//System.out.println("ADDING "+move);
					possiblemoves.add(move);
					ddr = false;
				}
				else{
					ddr = false;
				}

				if (move==7||move==15||move==23||move==31||move==39||move==47||move==55||move==63){
					ddr = false;
				}



				move = move + 9;


			}
			else{
				ddr = false;
			}


		}




		//System.out.println("POSSIBLE MOVES: "+ possiblemoves);

		System.out.println("Bishops' possible moves: "+possiblemoves);
		return possiblemoves;}


	static int i = 0;
	static Integer imageid = 0;
	static Item piece = null;
	static int firstposition = 0;

	public static int onItemClick(View v, int position) throws InterruptedException, ExecutionException 
	{
		//PlayGameActivity.save(true, gameMoves, "test");


		ImageView picture;
		picture = (ImageView)v.getTag(R.id.picture);
		if (i == 0){
			
			imageid = (int)PlayGameActivity.chessboardGridView.getAdapter().getItemId(position);
			piece = (Item) PlayGameActivity.chessboardGridView.getAdapter().getItem(position);
			if (player%2==0){
				if (piece.getName().charAt(0)!='w'){
					return -23;
				}
			}else{
				if (piece.getName().charAt(0)!='b'){
					return -23;
				}
			}
			
			if(piece.getName().equals("blank")){
				System.out.println("I WAS AN EMPTY SPACE");
				i = 0;
				return 1;
			}else{
				i = 1;
				firstposition = position;
				if (position<=7||(15<position&&position<=23)||(31<position&&position<=39)||(47<position&&position<=55)){
					if (position % 2 == 0){
						picture.setBackgroundResource(R.drawable.lightwood_highlighted);}
					else{
						picture.setBackgroundResource(R.drawable.wood_highlighted);
					}
				}else{
					if (position % 2 != 0){
						picture.setBackgroundResource(R.drawable.lightwood_highlighted);}
					else{

						picture.setBackgroundResource(R.drawable.wood_highlighted);
					}
				}
				return 0;
				//picture.setImageResource(0);
			}

		}
		else{
			if (firstposition==position){
				if (position<=7||(15<position&&position<=23)||(31<position&&position<=39)||(47<position&&position<=55)){
					if (position % 2 == 0){
						picture.setBackgroundResource(R.drawable.lightwood);}
					else{
						picture.setBackgroundResource(R.drawable.wood);
					}
				}else{
					if (position % 2 != 0){
						picture.setBackgroundResource(R.drawable.lightwood);}
					else{

						picture.setBackgroundResource(R.drawable.wood);
					}
				}
				i=0;
				return 0;
			}

			if(piece.getName().equals("whitepawn")){
				//	System.out.println("I WAS A WHITE PAWN");
				int x = Moves.isValidPawnMove("pawn", firstposition, position, PlayGameActivity.chessboardGridView, 'w');
				if (x!=-1){
					if (x==-5){
						System.out.println("returning -5 from onItemClick");
						return -5;
					}
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					picture.setImageResource(imageid);
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(x, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					picture.setImageResource(imageid);
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whitepawn", imageid);

					PlayGameActivity.chessboardGridView.invalidateViews();
					i = 0;

					lastpiece = "whitepawn";
					lastfirst = firstposition;
					lastdest = position;
					lastp = x;
					if (getPossiblePawnMoves(position, PlayGameActivity.chessboardGridView, 'w').contains(blackkingindex)){
						danger = "pawn";
						return 5;
					}
					if (whitepromoted==true){
						return 2;
					}
					checkKings();

					if (position == blackkingindex){
						whitewins=true;
					}



					return 0;}
				else{
					System.out.println("INVALID MOVE");
					return 1;
				}
			}
			else if(piece.getName().equals("whitequeen")){

				System.out.println("I WAS A WHITE QUEEN");
				if (Moves.isValidMove("queen", firstposition, position, PlayGameActivity.chessboardGridView, 'w')){
					System.out.println("got here");
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					picture.setImageResource(imageid);
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whitequeen", imageid);

					PlayGameActivity.chessboardGridView.invalidateViews();
					i = 0;
					lastpiece = "whitequeen";
					lastfirst = firstposition;
					lastdest = position;
					lastp=-1;
					if (isValidMove("queen", position, blackkingindex, PlayGameActivity.chessboardGridView, 'w')){
						danger = "queen";
						return 5;
					}
					checkKings();
					if (position == blackkingindex){
						whitewins=true;
					}
					return 0;}
				else{
					System.out.println("INVALID MOVE");
					return 1;
				}

			}
			else if(piece.getName().equals("whiteking")){
				plus = false;
				minus = false;

				System.out.println("I WAS A WHITE KING");
				if (Moves.isValidMove("king", firstposition, position, PlayGameActivity.chessboardGridView, 'w')){

					//check if the move is a castling move
					if (castled == false){
						((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
						PlayGameActivity.chessboardGridView.invalidateViews();
						picture.setImageResource(imageid);
						((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whiteking", imageid);
						PlayGameActivity.chessboardGridView.invalidateViews();
						whitekingindex = position;
						i = 0;
						lastpiece = "whiteking";
						lastfirst = firstposition;
						lastdest = position;
						lastp = -1;
						checkKings();
						if (position == blackkingindex){
							whitewins=true;
						}
						WKcanCastle = false;
						return 0;
					}
					//if is a castling move, the pieces are swapped rather than replaced
					else if (castled == true && WKcanCastle == true){
						if (minus == true){
							((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition-1, PlayGameActivity.chessboardGridView,"whiterook", R.drawable.whiterook);
							PlayGameActivity.chessboardGridView.invalidateViews();
						}
						if (plus == true){
							((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition+1, PlayGameActivity.chessboardGridView,"whiterook", R.drawable.whiterook);
							PlayGameActivity.chessboardGridView.invalidateViews();
						}
						picture.setImageResource(imageid);
						((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whiteking", imageid);
						PlayGameActivity.chessboardGridView.invalidateViews();
						((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);

						PlayGameActivity.chessboardGridView.invalidateViews();
						i = 0;
						castled = false;
						whitekingindex = position;
						if (isValidMove("king", position, blackkingindex, PlayGameActivity.chessboardGridView, 'w')){
							danger = "king";
							return 5;
						}
						checkKings();
						if (position == blackkingindex){
							whitewins=true;
						}
						WKcanCastle = false;
						lastpiece = "whiteking";
						lastfirst = firstposition;
						lastdest = position;
						lastp = -1;
						return 0;
					}
				}
				else{
					System.out.println("INVALID MOVE");
					return 1;
					//alertDialog.show();
				}}
			else if(piece.getName().equals("whiterook")){
				System.out.println("I WAS A WHITE ROOK");
				if (Moves.isValidMove("rook", firstposition, position, PlayGameActivity.chessboardGridView, 'w')){
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					PlayGameActivity.chessboardGridView.invalidateViews();
					picture.setImageResource(imageid);
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whiterook", imageid);
					PlayGameActivity.chessboardGridView.invalidateViews();
					i = 0;
					lastpiece = "whiterook";
					lastfirst = firstposition;
					lastdest = position;
					lastp = -1;
					if (isValidMove("rook", position, blackkingindex, PlayGameActivity.chessboardGridView, 'w')){
						danger = "rook";
						return 5;
					}
					checkKings();
					if (position == blackkingindex){
						whitewins=true;
					}
					return 0;}
				else{
					System.out.println("NOT A VALID MOVE!");
					return 1;
				}
			}
			else if(piece.getName().equals("whitebishop")){
				System.out.println("I WAS A WHITE BISHOP");
				if (Moves.isValidMove("bishop", firstposition, position, PlayGameActivity.chessboardGridView, 'w')){
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					PlayGameActivity.chessboardGridView.invalidateViews();
					picture.setImageResource(imageid);
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whitebishop", imageid);
					PlayGameActivity.chessboardGridView.invalidateViews();
					i = 0;
					lastpiece = "whitebishop";
					lastfirst = firstposition;
					lastdest = position;
					lastp = -1;
					if (isValidMove("bishop", position, blackkingindex, PlayGameActivity.chessboardGridView, 'w')){
						danger = "bishop";
						return 5;
					}
					checkKings();
					if (position == blackkingindex){
						whitewins=true;
					}
					return 0;}
				else{
					System.out.println("INVALID MOVE");
					return 1;
					//alertDialog.show();
				}
			}
			else if(piece.getName().equals("whiteknight")){
				if (Moves.isValidMove("knight", firstposition, position, PlayGameActivity.chessboardGridView, 'w')){
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					PlayGameActivity.chessboardGridView.invalidateViews();
					picture.setImageResource(imageid);
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whiteknight", imageid);
					PlayGameActivity.chessboardGridView.invalidateViews();
					i = 0;
					lastpiece = "whiteknight";
					lastfirst = firstposition;
					lastdest = position;
					lastp = -1;
					if (isValidMove("knight", position, blackkingindex, PlayGameActivity.chessboardGridView, 'w')){
						danger = "knight";
						return 5;
					}
					checkKings();
					if (position == blackkingindex){
						whitewins=true;
					}
					return 0;}
				else{
					System.out.println("INVALID MOVE");
					return 1;
					//alertDialog.show();
				}
			}
			else if(piece.getName().equals("blackpawn")){
				System.out.println("I WAS A BLACK PAWN");
				int x = Moves.isValidPawnMove("pawn", firstposition, position, PlayGameActivity.chessboardGridView, 'b');
				if (x!=-1){
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					picture.setImageResource(imageid);
					//System.out.println("1");
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(x, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					//System.out.println("2");
					picture.setImageResource(imageid);
					//System.out.println("3");
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"blackpawn", imageid);
					//System.out.println("4");
					PlayGameActivity.chessboardGridView.invalidateViews();
					//System.out.println("5");
					i = 0;
					lastpiece = "blackpawn";
					lastfirst = firstposition;
					lastdest = position;
					lastp = x;
					if (getPossiblePawnMoves(position, PlayGameActivity.chessboardGridView, 'b').contains(whitekingindex)){
						danger = "pawn";
						return 6;
					}

					if (blackpromoted==true){
						return 3;
					}
					checkKings();

					if (position == whitekingindex){
						blackwins=true;
					}
					return 0;}
				else{
					System.out.println("INVALID MOVE");
					return 1;
				}
			}
			else if(piece.getName().equals("blackqueen")){

				System.out.println("I WAS A BALCK QUEEN");
				if (Moves.isValidMove("queen", firstposition, position, PlayGameActivity.chessboardGridView, 'b')){
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					PlayGameActivity.chessboardGridView.invalidateViews();
					picture.setImageResource(imageid);
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"blackqueen", imageid);
					PlayGameActivity.chessboardGridView.invalidateViews();
					lastpiece = "blackpqueen";
					lastfirst = firstposition;
					lastdest = position;
					lastp = -1;
					i = 0;
					if (isValidMove("queen", position, whitekingindex, PlayGameActivity.chessboardGridView, 'b')){
						danger = "queen";
						return 6;
					}
					checkKings();
					if (position == whitekingindex){
						blackwins=true;
					}
					return 0;}
				else{
					System.out.println("NOT A VALID MOVE!");
					return 1;
				}

			}
			else if(piece.getName().equals("blackking")){
				plus = false;
				minus = false;

				//System.out.println("I WAS A BLACK KING");
				if (Moves.isValidMove("king", firstposition, position, PlayGameActivity.chessboardGridView, 'b')){
					if (castled == false){
						((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
						PlayGameActivity.chessboardGridView.invalidateViews();
						picture.setImageResource(imageid);
						((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"blackking", imageid);
						PlayGameActivity.chessboardGridView.invalidateViews();

						blackkingindex=position;
						lastpiece = "blackking";
						lastfirst = firstposition;
						lastdest = position;
						lastp = -1;
						checkKings();
						i = 0;
						if (position == whitekingindex){
							blackwins=true;
						}
						BKcanCastle = false;
						return 0;}
					else if (castled == true && BKcanCastle == true){

						if (minus == true){
							((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition-1, PlayGameActivity.chessboardGridView,"blackrook", R.drawable.blackrook);
							//picture.setImageResource(imageid);
							PlayGameActivity.chessboardGridView.invalidateViews();
							minus = false;
						}
						if (plus == true){	

							((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition+1, PlayGameActivity.chessboardGridView,"blackrook", R.drawable.blackrook);
							//picture.setImageResource(imageid);
							PlayGameActivity.chessboardGridView.invalidateViews();
							plus = false;
						}
						picture.setImageResource(imageid);
						((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"blackking", imageid);
						PlayGameActivity.chessboardGridView.invalidateViews();
						((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
						PlayGameActivity.chessboardGridView.invalidateViews();
						i = 0;
						castled = false;
						blackkingindex=position;
						if (isValidMove("king", position, whitekingindex, PlayGameActivity.chessboardGridView, 'b')){
							danger = "king";
							return 6;
						}
						checkKings();
						if (position == whitekingindex){
							blackwins=true;
						}
						BKcanCastle = false;
						lastpiece = "blackking";
						lastfirst = firstposition;
						lastdest = position;
						lastp = -1;
						return 0;
					}}
				else{
					System.out.println("NOT A VALID MOVE!");
					return 1;
				}}
			else if(piece.getName().equals("blackrook")){
				System.out.println("I WAS A BLACK ROOK");
				if (Moves.isValidMove("rook", firstposition, position, PlayGameActivity.chessboardGridView, 'b')){
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					picture.setImageResource(imageid);
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"blackrook", imageid);
					PlayGameActivity.chessboardGridView.invalidateViews();
					lastpiece = "blackrook";
					lastfirst = firstposition;
					lastdest = position;
					lastp = -1;
					i = 0;
					if (isValidMove("rook", position, whitekingindex, PlayGameActivity.chessboardGridView, 'b')){
						danger = "rook";
						return 6;
					}
					checkKings();
					if (position == whitekingindex){
						blackwins=true;
					}
					return 0;}
				else{
					System.out.println("NOT A VALID MOVE!");
					return 1;
				}
			}
			else if(piece.getName().equals("blackbishop")){
				System.out.println("I WAS A BLACK BISHOP");
				if (Moves.isValidMove("bishop", firstposition, position, PlayGameActivity.chessboardGridView, 'b')){
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					PlayGameActivity.chessboardGridView.invalidateViews();

					picture.setImageResource(imageid);
					PlayGameActivity.chessboardGridView.invalidateViews();
					lastpiece = "blackbishop";
					lastfirst = firstposition;
					lastdest = position;
					lastp = -1;
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"blackbishop", imageid);
					i = 0;
					PlayGameActivity.chessboardGridView.invalidateViews();
					System.out.println("WHITE KING INDEX: "+whitekingindex);
					if (isValidMove("bishop", position, whitekingindex, PlayGameActivity.chessboardGridView, 'b')){
						danger = "bishop";
						return 6;
					}
					checkKings();
					if (position == whitekingindex){
						blackwins=true;
					}
					return 0;}
				else{
					System.out.println("NOT A VALID MOVE!");
					return 1;
				}
			}
			else if(piece.getName().equals("blackknight")){
				if (Moves.isValidMove("knight", firstposition, position, PlayGameActivity.chessboardGridView, 'b')){
					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(firstposition, PlayGameActivity.chessboardGridView,"blank", R.drawable.blank);
					PlayGameActivity.chessboardGridView.invalidateViews();

					picture.setImageResource(imageid);
					PlayGameActivity.chessboardGridView.invalidateViews();

					((MyAdapter) PlayGameActivity.chessboardGridView.getAdapter()).setItem(position, PlayGameActivity.chessboardGridView,"whiteknight", imageid);

					PlayGameActivity.chessboardGridView.invalidateViews();
					lastpiece = "blackknight";
					lastfirst = firstposition;
					lastdest = position;
					lastp = -1;
					i = 0;
					if (isValidMove("knight", position, whitekingindex, PlayGameActivity.chessboardGridView, 'b')){
						danger = "knight";
						return 6;
					}
					checkKings();
					if (position == whitekingindex){
						blackwins=true;
					}
					return 0;}
				else{
					System.out.println("INVALID MOVE");
					return 1;
					//alertDialog.show();
				}
			}

			else{
				i = 1; 
			}
		}
		return 1;

	}

	
	public static int getPosition(String name, GridView board, int start){
		Item pos;
		for (; start < 64; start++){
			pos = (Item)board.getAdapter().getItem(start);
			if (pos.getName().equals(name)){
				return start;
			}
		}
		return -1;
	}
	
	public static int getPosition2(char c, GridView board, int start){
		Item pos;
		for (; start < 64; start++){

			pos = (Item)board.getAdapter().getItem(start);
			if (!pos.getName().equals("blank")){
				if (pos.getName().charAt(0)==c){
					return start;
				}
			}
		}
		return -1;
	}
	
}
