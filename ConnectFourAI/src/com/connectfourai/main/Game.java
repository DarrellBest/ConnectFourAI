package com.connectfourai.main;

import java.util.ArrayList;

public class Game {

	private Player one;
	private Player two;
	private int turnCount;
	private int row;
	private int col;
	private ArrayList<ArrayList<String>> outer;
	private ArrayList<String> inner;
	private String whoWon;
	private String winCondition;

	public Game(Player one, Player two, int row, int col) {
		this.winCondition = new String();
		this.whoWon = new String();
		this.turnCount = 1;
		this.one = one;
		this.two = two;
		this.row = row;
		this.col = col;

		outer = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < col; i++) {
			inner = new ArrayList<String>();
			outer.add(inner);
			for (int j = 0; j < row; j++) {
				outer.get(i).add("X");
			}
		}
		one.setOuter(outer);
		two.setOuter(outer);
	}

	public String play() {
		String retVal = "";
		boolean winner = false;
		int maxMoves = row * col;

		for (int i = 0; i < maxMoves && !winner; i++) {
			int playerToMove = i % 2;
			this.turnCount++;
			System.out.println(this.toString());

			if (playerToMove == 0) {
				if (!winner) {
					winner = one.move();
					if (winner)
						this.whoWon = one.getName();
				}
			}
			if (playerToMove == 1) {
				if (!winner) {
					winner = two.move();
					if (winner)
						this.whoWon = two.getName();
				}
			}
		}

		// If no winner set return value to TIE
		if (this.whoWon.length() == 0) {
			retVal = "Tie";
		} else {
			retVal = this.whoWon + " won!";
		}
		return retVal;
	}

	@Override
	public String toString() {
		return one.toString();
	}
}
