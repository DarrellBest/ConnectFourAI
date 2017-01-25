package com.connectfourai.main;

import java.util.ArrayList;
import java.util.Scanner;

public class ManualPlayer extends Player {

	private String name;
	private int row;
	private int col;
	private ArrayList<ArrayList<String>> outer;
	private Scanner userInput;

	public ManualPlayer(String name, int row, int col) {
		super(name, row, col);
		this.name = name;
		this.row = row;
		this.col = col;
		this.outer = this.getOuter();
	}

	@Override
	public boolean move() {
		boolean retVal = false;
		outer = this.getOuter();
		userInput = new Scanner(System.in);

		// Get user input

		int temp = -1;
		try {
			temp = Integer.parseInt(userInput.next());
		} catch (NumberFormatException e) {
			System.out.println("Please enter int from 1 to " + col);
		}

		// convert column # to index #
		temp--;

		if (temp >= 0 && temp < col) {
			if (outer.get(temp).contains("X")) {
				int index = outer.get(temp).indexOf("X");
				outer.get(temp).set(index, this.name);
			} else {
				move();
			}
		} else {
			move();
		}
		retVal = this.isWinner();
		return retVal;
	}
}
