package com.connectfourai.main;

public class MainDriver {

	public static void main(String[] args) {

		int row = 8;
		int col = 8;
		String winner = new String();

		// Player three = new RandomAI("r", row, col);
		Player one = new TurnBasedAI("Robot", row, col);
		Player two = new TurnBasedAI("Intel", row, col);
		Player three = new ManualPlayer("Darrell", row, col);
		Player four = new ManualPlayer("Jared", row, col);

		// 2 Player Game
		// Game game = new Game(three, four, row, col);

		// VS COMP
		// Game game = new Game(three, one, row, col);

		// AI VS AI
		Game game = new Game(one, two, row, col);

		winner = game.play();
		System.out.println(winner);
		System.out.println(game.toString());
	}
}
