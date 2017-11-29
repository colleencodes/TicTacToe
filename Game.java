/*
 * Colleen Stock 
 */

import java.util.Scanner;

/*
 * This class sets up the main game 
 */
public class Game 
{
	private char [] token;
	private int numOfDraw;
	private int player;
	private int moveNum;
	private int space;
	private int level;
	private boolean playersTurn; 
	Scanner scan = new Scanner(System.in);
	AI ai;
	private char playerCh;
	static enum Winner {You, Computer, Tie, Ongoing}

	//default constructor
	public Game ()
	{
		token = new char [9];
		numOfDraw = 0;
	
		token[0] = ' ';
		token[1] = ' ';
		token[2] = ' ';
		token[3] = ' ';
		token[4] = ' ';
		token[5] = ' ';
		token[6] = ' ';
		token[7] = ' ';
		token[8] = ' ';
	}	

	//overloaded constructor
	public Game (int p, char pC, int l)
	{
		token = new char [9];
		numOfDraw = 0;
		player = p;
		moveNum = 0;
		space = 0;
		level = l;
		ai = new AI(player, level, token);
		playerCh = pC;
		
		if (player == 1)
			playersTurn = true;
		else
			playersTurn = false;
			
		ai.setToken(playerCh);
	
		token[0] = ' ';
		token[1] = ' ';
		token[2] = ' ';
		token[3] = ' ';
		token[4] = ' ';
		token[5] = ' ';
		token[6] = ' ';
		token[7] = ' ';
		token[8] = ' ';

	}	
	
	//sets whether the player is X or O
	public void setPlayerCh(char c)
	{
		playerCh = c;
	}
	
	public char getPlayerCh()
	{
		return playerCh;
	}
	
	//sets the array
	public void setTokens(char [] c)
	{
		token = c;
	}
	
	public char [] getTokens()
	{
		return token;
	}
	
	//places a character into the array for the board
	//and swaps whose turn it is
	public void setToken(int num, char c)
	{
		int boardNum = num;
		char character = c;

		token[boardNum - 1] = character;
			
		playersTurn = !playersTurn;
	}
	
	//sets whether the player is first or second
	public void setPlayerTurn(int p)
	{
		player = p;
	}
	
	public int getPlayerTurn()
	{
		return player;
	}
	
	//sets the level of play
	public void setLevel(int l)
	{
		level = l;
	}

	public int getLevel()
	{
		return level;
	}
	
	//determines if it is true or false in regards to if it is the players turn
	public void setPlayersTurn(boolean t)
	{
		playersTurn = t;
	}
	
	public boolean getPlayersTurn()
	{
		return playersTurn;
	}
	
	//draws a board for the player to view
	public void drawBoard()
	{
		numOfDraw += 1;
	
		//only drawn the first time as a reference
		if (numOfDraw == 1)
		{
			System.out.println(" 1 | 2 | 3 ");
			System.out.println(" ----------");
			System.out.println(" 4 | 5 | 6 ");
			System.out.println(" ----------");
			System.out.println(" 7 | 8 | 9 ");
			System.out.println(" ");
		}
		
		//board drawn every time but the first
		else
		{
			System.out.println("   Play " + (numOfDraw - 1));
			System.out.println(" " + token[0] + " | " + token[1] + " | " + token[2]);
			System.out.println(" ----------");
			System.out.println(" " + token[3] + " | " + token[4] + " | " + token[5]);
			System.out.println(" ----------");
			System.out.println(" " + token[6] + " | " + token[7] + " | " + token[8]);
			System.out.println(" ");
		}
	}
	
	//this determines whose move it is
	public void whoseMove()
	{
		moveNum += 1;
		
		//player's turn
		if (this.getPlayersTurn())
		{
			System.out.println("Your Turn. Pick your space.");
			space = scan.nextInt();
			
			//checks for illegal moves
			while (legalMove(space) == false)
			{
				System.out.println("That is an illegal move. Try again.");
				space = scan.nextInt();
			}
			setToken(space, playerCh); 
		}
		
		//computer's turn
		else
		{
			System.out.println("It's the computer's turn. \n Thinking . . .");
			getAIMove();
		}
	}
	
	//gets the computer's move and then puts it into the array
	public void getAIMove()
	{
		space = ai.computerMove(this.copy());
		setToken(space + 1, ai.getToken());
	}
	
	//checks to see if the player is performing a 
	//legal move
	public boolean legalMove(int s)
	{
		space = s;
		
		if (space < 1 || space > 9 || token[space - 1] != ' ')
			return false;
		else
			return true;	
	}
	
	//makes a copy of the current game
	public Game copy()
	{
		Game temp = new Game(player, playerCh, level);
		temp.setTokens(token.clone());
		temp.setPlayersTurn(playersTurn);
		return temp;
	}
	
	//determines at what status the game is at: 
	//ongoing, tie, the player won, or the computer won
	public Winner finalSituation()
	{
		if (token[0] == ai.getToken() && token[1] == ai.getToken() && token[2] == ai.getToken())
			return Winner.Computer;
		else if (token[3] == ai.getToken() && token[4] == ai.getToken() && token[5] == ai.getToken())
			return Winner.Computer;
		else if (token[6] == ai.getToken() && token[7] == ai.getToken() && token[8] == ai.getToken())
			return Winner.Computer;
		else if (token[0] == ai.getToken() && token[3] == ai.getToken() && token[6] == ai.getToken())
			return Winner.Computer;
		else if (token[1] == ai.getToken() && token[4] == ai.getToken() && token[7] == ai.getToken())
			return Winner.Computer;
		else if (token[2] == ai.getToken() && token[5] == ai.getToken() && token[8] == ai.getToken())
			return Winner.Computer;
		else if (token[0] == ai.getToken() && token[4] == ai.getToken() && token[8] == ai.getToken())
			return Winner.Computer;
		else if (token[2] == ai.getToken() && token[4] == ai.getToken() && token[6] == ai.getToken())
			return Winner.Computer;
		
		else if (token[0] == playerCh && token[1] == playerCh && token[2] == playerCh)
			return Winner.You;
		else if (token[3] == playerCh && token[4] == playerCh && token[5] == playerCh)
			return Winner.You;
		else if (token[6] == playerCh && token[7] == playerCh && token[8] == playerCh)
			return Winner.You;
		else if (token[0] == playerCh && token[3] == playerCh && token[6] == playerCh)
			return Winner.You;
		else if (token[1] == playerCh && token[4] == playerCh && token[7] == playerCh)
			return Winner.You;
		else if (token[2] == playerCh && token[5] == playerCh && token[8] == playerCh)
			return Winner.You;
		else if (token[0] == playerCh && token[4] == playerCh && token[8] == playerCh)
			return Winner.You;
		else if (token[2] == playerCh && token[4] == playerCh && token[6] == playerCh)
			return Winner.You;
		
		else if (token[0] != ' ' && token[1] != ' ' && token[2] != ' ' && token[3] != ' ' && token[4] != ' ' && token[5] != ' ' && token[6] != ' ' && token[7] != ' ' && token[8] != ' ')
			return Winner.Tie;
		
		else
			return Winner.Ongoing;
			
	}
}


