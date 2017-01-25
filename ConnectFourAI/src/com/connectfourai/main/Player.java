package com.connectfourai.main;

import java.util.ArrayList;
import java.util.Random;

public class Player {

	private ArrayList<ArrayList<String>> outer;
	private String name;
	private Random random;
	private int row;
	private int col;

	public Player(String name, int row, int col) {
		this.name = name;
		this.random = new Random();
		this.row = row;
		this.col = col;
		this.outer = null;
	}

	public int getCol() {
		return this.col;
	}

	public int getRow() {
		return this.row;
	}

	public ArrayList<ArrayList<String>> getOuter() {
		return this.outer;
	}

	public int getRandom(int rand) {
		return random.nextInt(rand);
	}

	public void setOuter(ArrayList<ArrayList<String>> outer) {
		this.outer = outer;
	}

	public boolean move() {
		boolean retVal = false;
		int colCount = 0;
		int temp = getRandom(col);

		while (outer.get(temp).get(row - 1).compareTo("X") != 0
				&& colCount < col) {
			temp = random.nextInt(col);
			colCount++;
		}

		if (colCount < col)
			for (int i = 0; i < row; i++)
				if (outer.get(temp).get(i).compareTo("X") == 0) {
					outer.get(temp).set(i, this.name);
					break;
				}

		retVal = isWinner();

		return retVal;
	}

	// return a string win condition if winner, else returns ""
	public boolean isWinner() {
		boolean retVal = false;
		boolean condA = false;
		ArrayList<String> temp = new ArrayList<String>();
		ArrayList<String> posOne = new ArrayList<String>();
		ArrayList<String> posTwo = new ArrayList<String>();
		ArrayList<String> posThree = new ArrayList<String>();
		ArrayList<String> posFour = new ArrayList<String>();

		for (int i = 0; i < col && !retVal; i++) {
			temp = outer.get(i);
			for (int j = 0; j < row && !retVal; j++) {
				// Condition A
				if (j + 3 < row) {
					condA = true;
					// check up (takes care of down case)
					if (temp.get(j).compareTo(name) == 0)
						if (temp.get(j + 1).compareTo(name) == 0)
							if (temp.get(j + 2).compareTo(name) == 0)
								if (temp.get(j + 3).compareTo(name) == 0)
									retVal = true;
				}

				// Condition B
				if (i + 3 < col) {
					// check right
					posOne = outer.get(i);
					posTwo = outer.get(i + 1);
					posThree = outer.get(i + 2);
					posFour = outer.get(i + 3);

					if (posOne.get(j).compareTo(name) == 0)
						if (posTwo.get(j).compareTo(name) == 0)
							if (posThree.get(j).compareTo(name) == 0)
								if (posFour.get(j).compareTo(name) == 0)
									retVal = true;

					// Condition C == CondA & CondB
					if (condA) {
						// check diagonally up right
						if (posOne.get(j).compareTo(name) == 0)
							if (posTwo.get(j + 1).compareTo(name) == 0)
								if (posThree.get(j + 2).compareTo(name) == 0)
									if (posFour.get(j + 3).compareTo(name) == 0)
										retVal = true;
					}

					// don't check the first 3 columns (segfault)
					if (condA && i > 2) {
						posOne = outer.get(i);
						posTwo = outer.get(i - 1);
						posThree = outer.get(i - 2);
						posFour = outer.get(i - 3);

						// check diagonally up left
						if (posOne.get(j).compareTo(name) == 0)
							if (posTwo.get(j + 1).compareTo(name) == 0)
								if (posThree.get(j + 2).compareTo(name) == 0)
									if (posFour.get(j + 3).compareTo(name) == 0)
										retVal = true;
					}
				}
				condA = false;
			}
		}
		return retVal;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		String retVal = "";
		int length = 0;

		for (int i = 0; i < row; i++) {
			for (ArrayList<String> list : outer) {
				retVal = retVal.concat("| " + list.get(row - 1 - i).charAt(0)
						+ " ");
			}
			retVal = retVal.concat("|\n");
			if (i == 0)
				length = retVal.length();
		}

		// Print top dashes
		retVal = "\n".concat(retVal);
		for (int i = 0; i < length; i++)
			retVal = "-".concat(retVal);

		// print bottom dashes
		for (int i = 0; i < length; i++)
			retVal = retVal.concat("-");
		retVal = retVal.concat("\n");

		for (int i = 0; i < col; i++) {
			retVal = retVal.concat("| " + (i + 1) + " ");
		}
		retVal = retVal.concat("|\n");

		return retVal.replaceAll("X", " ");
	}
}
