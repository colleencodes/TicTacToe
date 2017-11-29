/*
 * Colleen Stock
 * Dr. Wetklow
 * Advanced Programming Topics
 * 26 January 2010
 * TicTacToe Program
 */

import java.util.Scanner;

/*
 * This program plays the game TicTacToe with 
 * the computer versus a human
 */
public class TicTacToe 
{
	public static void main (String [] args)
	{
		Scanner scan = new Scanner(System.in);
		int playerNum = 0;
		char pChar = ' ';
		int level = 0;
		
		//sets up and checks the parameters for the game
		System.out.println("Do you want to go first or second? (1 or 2)");
		playerNum = scan.nextInt();
		
		System.out.println("Do you want to be X's or O's? \n Please enter a capital X or O");
		pChar = scan.next().charAt(0);


		System.out.println("What level do you want to play? 0 - 8");
		level = scan.nextInt();
		
		while (level < 0 || level > 8)
		{
			System.out.println("That level is not possible. Please enter a different level.");
			level = scan.nextInt();
		}
		
		//creates a game
		Game game = new Game(playerNum, pChar, level);

		//allows the game to be played as long as there is no winner or tie
		while (game.finalSituation() == Game.Winner.Ongoing)
		{
			game.drawBoard();
			game.whoseMove();
		}
		

		
		//prints the outcome of the game
		if (game.finalSituation() == Game.Winner.Computer)
			System.out.println("The winner of this game is the computer.");
		else if (game.finalSituation() == Game.Winner.You)
			System.out.println("The winner of this game is You!");
		else
			System.out.println("The winner of this game is no one. Thanks for playing.");
		game.drawBoard();
	}
}

