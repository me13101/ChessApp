package com.example.chess;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

public interface GameList {
	void setContext(Context ctx);
	void load() throws IOException;
	void store() throws IOException;
	ArrayList<Game> getGames();
	Game add(String name, ArrayList<String> moves);
	int getPos(Game g);
}