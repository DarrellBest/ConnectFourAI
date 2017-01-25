package com.connectfourai.main;

import java.util.ArrayList;

public class TurnBasedAI extends Player {

	private int row;
	private int col;
	private String name;
	private int turnCount;

	public TurnBasedAI(String name, int row, int col) {
		super(name, row, col);
		this.row = row;
		this.col = col;
		this.name = name;
		this.turnCount = 0;
	}

	@Override
	public boolean move() {
		boolean retVal = false;
		boolean moveTaken = false;
		this.turnCount++;

		// move in the bottom center if not taken
		if (turnCount <= 10) {
			moveTaken = bottomCenter();
			if (moveTaken) {
				System.out.println(this.name + " Took bottom center.");
				return retVal = isWinner();
			}
		}

		// check if you can win
		// if so, then play winning move
		moveTaken = playWinningMove();
		if (moveTaken)
			return retVal = isWinner();

		// check if opp can win. if so, then block
		String oppName = getOppName();
		if (oppName.length() > 0)
			moveTaken = playBlockingMove(oppName);
		if (moveTaken) {
			return retVal = isWinner();
		}

		// check if my opp can win after making a move
		// if so dont move there
		moveTaken = preventSetUp(oppName);
		if (moveTaken)
			return retVal = isWinner();

		// Random move if no other move matters
		randomMove();
		// System.out.println("Random move taken avoiding edges");
		retVal = isWinner();
		return retVal;
	}

	private boolean preventSetUp(String oppName) {
		boolean retVal = false;
		int index = -1;
		int playableIndex;
		boolean temp = false;
		ArrayList<ArrayList<String>> outer = this.getOuter();
		ArrayList<String> currentCol;
		ArrayList<String> playableZones = new ArrayList<String>();

		for (int i = 0; i < col; i++) {
			playableZones.add(Integer.toString(i));
		}

		// Find all non playable zones
		for (int i = 0; i < col; i++) {
			currentCol = outer.get(i);

			// seeing if there is an empty position recursively
			index = currentCol.indexOf("X");
			// if an index is found to play on then attempt to win
			if (index != -1) {
				currentCol.set(index, this.getName());
				temp = canOppWin(oppName);
				currentCol.set(index, "X");
			} else {
				playableZones.remove(Integer.toBinaryString(i));
				System.out.println(this.name + " detected full column at "
						+ (i + 1));
			}
			if (temp) {
				playableZones.remove(Integer.toBinaryString(i));
				System.out.println(this.name
						+ " detected loosing move at column " + (i + 1));
			}
		}

		// if there remains any safe or non-full areas left to play, then do so
		// randomly
		while (playableZones.size() > 0 && !retVal) {
			System.out.println(playableZones.toString());
			playableIndex = Integer.parseInt(playableZones.remove(this
					.getRandom(playableZones.size())));
			currentCol = outer.get(playableIndex);
			index = currentCol.indexOf("X");
			if (index != -1) {
				currentCol.set(index, this.name);
				retVal = true;
			}
		}
		return retVal;
	}

	private boolean playBlockingMove(String oppName) {
		boolean retVal = false;
		int index = -1;
		boolean temp = false;
		ArrayList<ArrayList<String>> outer = this.getOuter();
		ArrayList<String> currentCol;

		for (int i = 0; i < col; i++) {

			currentCol = outer.get(i);

			// seeing if there is an empty position recursively
			index = currentCol.indexOf("X");
			// if an index is found to play on then attempt to win
			if (index != -1) {
				currentCol.set(index, oppName);
				temp = canOppWin(oppName);

				// if a win condition is found then return
				if (temp) {
					currentCol.set(index, this.name);
					System.out.println(this.name + " Blocked " + oppName
							+ " at column " + (i + 1) + ".");
					return retVal = true;
				} else
					// undo action
					currentCol.set(index, "X");
			}
		}
		return retVal;
	}

	private boolean playWinningMove() {
		boolean retVal = false;
		int index = -1;
		boolean temp = false;
		ArrayList<ArrayList<String>> outer = this.getOuter();
		ArrayList<String> currentCol;

		for (int i = 0; i < col; i++) {

			currentCol = outer.get(i);

			// seeing if there is an empty position recursively
			index = currentCol.indexOf("X");
			// if an index is found to play on then attempt to win
			if (index != -1) {
				currentCol.set(index, this.getName());
				temp = isWinner();

				// if a win condition is found then return
				if (temp) {
					retVal = true;
					return retVal;
				} else
					// undo action
					currentCol.set(index, "X");
			}
		}
		return retVal;
	}

	private boolean bottomCenter() {
		boolean retVal = false;
		ArrayList<String> centerCol = getOuter().get(col / 2);
		if (centerCol.get(0).compareTo("X") == 0) {
			centerCol.set(0, name);
			retVal = true;
		}
		return retVal;
	}

	private String getOppName() {
		String retVal = "";
		for (ArrayList<String> list : this.getOuter()) {
			for (String val : list) {
				// find enemy name
				if (val.compareTo(name) != 0 && val.compareTo("X") != 0) {
					retVal = val;
				}
			}
		}
		return retVal;
	}

	private void randomMove() {
		ArrayList<ArrayList<String>> outer = this.getOuter();
		int colCount = 0;
		int rand = 0;
		int index = -1;
		int maxRandomizations = 100;

		// Don't play on edges
		while (rand == 0 || rand == col - 1)
			rand = getRandom(col);

		while (!outer.get(rand).contains("X") && colCount < maxRandomizations) {
			while (rand == 0 || rand == col - 1)
				rand = getRandom(col);
			colCount++;
		}

		index = outer.get(rand).indexOf("X");

		if (index != -1) {
			System.out.println(this.name + " Played random inner move.");
			outer.get(rand).set(index, name);
		} else {
			colCount = 0;
			while (!outer.get(rand).contains("X")
					&& colCount < maxRandomizations) {
				rand = getRandom(col);
				colCount++;
			}
			index = outer.get(rand).indexOf("X");
			if (index != -1)
				System.out.println(this.name + " Played random outer move.");
			outer.get(rand).set(index, name);
		}

	}

	private boolean canOppWin(String oppName) {
		boolean retVal = false;
		boolean condA = false;
		ArrayList<ArrayList<String>> outer = this.getOuter();
		ArrayList<String> temp = new ArrayList<String>();
		ArrayList<String> posOne = new ArrayList<String>();
		ArrayList<String> posTwo = new ArrayList<String>();
		ArrayList<String> posThree = new ArrayList<String>();
		ArrayList<String> posFour = new ArrayList<String>();

		for (int i = 0; i < col && !retVal; i++) {
			temp = outer.get(i);
			for (int j = 0; j < row && !retVal; j++) {
				condA = false;
				// Condition A
				if (j + 3 < row) {
					condA = true;
					// check up (takes care of down case)
					if (temp.get(j).compareTo(oppName) == 0)
						if (temp.get(j + 1).compareTo(oppName) == 0)
							if (temp.get(j + 2).compareTo(oppName) == 0)
								if (temp.get(j + 3).compareTo(oppName) == 0)
									return retVal = true;
				}

				// Condition B
				if (i + 3 < col) {
					// check right
					posOne = outer.get(i);
					posTwo = outer.get(i + 1);
					posThree = outer.get(i + 2);
					posFour = outer.get(i + 3);

					// opp win to the right
					if (posOne.get(j).compareTo(oppName) == 0)
						if (posTwo.get(j).compareTo(oppName) == 0)
							if (posThree.get(j).compareTo(oppName) == 0)
								if (posFour.get(j).compareTo(oppName) == 0)
									retVal = true;

					// Condition C == CondA & CondB
					if (condA) {
						// check diagonally up right
						if (posOne.get(j).compareTo(oppName) == 0)
							if (posTwo.get(j + 1).compareTo(oppName) == 0)
								if (posThree.get(j + 2).compareTo(oppName) == 0)
									if (posFour.get(j + 3).compareTo(oppName) == 0)
										retVal = true;
					}

					// dont check the first 3 columns
					// (segfault)
					if (condA && i > 2) {
						posOne = outer.get(i);
						posTwo = outer.get(i - 1);
						posThree = outer.get(i - 2);
						posFour = outer.get(i - 3);

						// check diagonally up left
						if (posOne.get(j).compareTo(oppName) == 0)
							if (posTwo.get(j + 1).compareTo(oppName) == 0)
								if (posThree.get(j + 2).compareTo(oppName) == 0)
									if (posFour.get(j + 3).compareTo(oppName) == 0)
										retVal = true;
					}
				}
			}
		}
		return retVal;
	}
}
