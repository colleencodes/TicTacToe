/*
 * Colleen Stock 
 */

/*
 * This class sets up the AI to play against the 
 * other play and helps it to determine where to play
 */
public class AI 
{
	int comp;
	int turn; 
	char compCh = ' ';
	
	//default constructor
	public AI ()
	{
		comp = 1;
	}
	
	//overloaded constructor
	public AI(int p, int l, char [] t)
	{
		turn = p;	
		if (turn == 1)
			comp = 2;
		else
			comp = 1;
	}
	
	//assigns the token that the player did not pick to the computer
	public void setToken(char p)
	{
		if (p == 'X')
			compCh = 'O';
		else
			compCh = 'X';
	}
	
	public char getToken()
	{
		return compCh;
	}
	
	//looks through an array for the first available space
	//if no spaces are available, returns -1
	private int nextPossibleMove(char [] t, int e)
	{
		for (int i = e; i < 9; i++)
		{
			if (t[i] == ' ')
				return i; 
		}
		
		return -1;
	}
	
	//calls for where the computer will play
	public int computerMove(Game g)
	{
		return bestMove(g);
	}
	
	//determines the best possible move for the computer
	private int bestMove(Game g)
	{
		int bestMoveOne, bestMoveTwo;
		int best, tryMove;
		Game tempGame = g.copy();

		tryMove = 0;

		//gets the first move to try
		tryMove = nextPossibleMove(tempGame.getTokens(), tryMove);

		//if it is the players turn, their token is temporarily put in the game
		//otherwise it is the computer's token
		if (tempGame.getPlayersTurn())
			tempGame.setToken(tryMove + 1, tempGame.getPlayerCh());	
		else
			tempGame.setToken(tryMove + 1, compCh);

		//calls on bestGuess for how good the first move actually is
		bestMoveOne = bestGuess(tempGame, tempGame.getLevel());

		best = tryMove;

		//repeats the same process as above
		while (tryMove != -1)
		{
			tryMove = nextPossibleMove(tempGame.getTokens(), tryMove);
			
			if (tryMove != -1)
			{
				tempGame = g.copy();

				if (tempGame.getPlayersTurn())
					tempGame.setToken(tryMove + 1, tempGame.getPlayerCh());
				else
					tempGame.setToken(tryMove + 1, compCh);
				
				bestMoveTwo = bestGuess(tempGame, tempGame.getLevel());

				//determines a bestMove and where would be the best play for the computer
				if ((tempGame.getPlayersTurn() && (bestMoveTwo > bestMoveOne)) || (!tempGame.getPlayersTurn() && (bestMoveTwo < bestMoveOne)))
				{
					bestMoveOne = bestMoveTwo;
					best = tryMove; 
				}
			}
		}

		//returns the best possible square for the computer
		return best;
	}
	
	//determines the best percentage of each of the available moves based on level
	private int bestGuess(Game g, int l)
	{
		int bestGuessOne = 0;
		int bestGuessTwo = 0;
		int tryMove = 0;
		Game tempGuess = g.copy(); 

		//if the level is zero or the game is over, returns percentage of how good that square was
		if (l == 0 || (!tempGuess.finalSituation().toString().equalsIgnoreCase(Game.Winner.Ongoing.toString())))
		{
			return judge(tempGuess);
		}
		
		//checks to see how good the square is for that level and then decreases the level by one
		else
		{
			tempGuess = g.copy();
			
			tryMove = 0;
			
			tryMove = nextPossibleMove(tempGuess.getTokens(), tryMove);

			if (tempGuess.getPlayersTurn())
			{
				tempGuess.setToken(tryMove + 1, tempGuess.getPlayerCh());
			}
	
			else
			{
				tempGuess.setToken(tryMove + 1, compCh);
			}

			bestGuessOne = bestGuess(tempGuess, l - 1);
			
			while (tryMove != -1)
			{
				tryMove = nextPossibleMove(tempGuess.getTokens(), tryMove);
				
				if (tryMove != -1)
				{
					tempGuess = g.copy();

					if (tempGuess.getPlayersTurn())	
						tempGuess.setToken(tryMove + 1, tempGuess.getPlayerCh());
					
					else
						tempGuess.setToken(tryMove + 1, compCh);
						
					bestGuessTwo = bestGuess(tempGuess, l - 1);
					
					//figures out if the move favors the player or the computer
					if (tempGuess.getPlayersTurn())
					{
						if (bestGuessTwo > bestGuessOne)
						{
							bestGuessOne = bestGuessTwo;
						}	
					}
					
					else
					{
						if (bestGuessTwo < bestGuessOne)
							bestGuessOne = bestGuessTwo;
					}
				}	
			}
		}

		return bestGuessOne; 
	}
	
	//checks to see how good a move is and returns a percentage based on each possible scenario
	private int judge(Game g)
	{		
		//winning situations for the computer
		if (Character.toString(compCh).equals(Character.toString(g.getTokens()[0])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[1])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[2])))
			return 100;
		else if (Character.toString(compCh).equals(Character.toString(g.getTokens()[3])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[4])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[5])))
			return 100;
		else if (Character.toString(compCh).equals(Character.toString(g.getTokens()[6])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[7])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[8])))
			return 100;
		else if (Character.toString(compCh).equals(Character.toString(g.getTokens()[0])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[3])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[6])))
			return 100;
		else if (Character.toString(compCh).equals(Character.toString(g.getTokens()[1])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[4])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[7])))
			return 100;
		else if (Character.toString(compCh).equals(Character.toString(g.getTokens()[2])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[5])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[8])))
			return 100;
		else if (Character.toString(compCh).equals(Character.toString(g.getTokens()[0])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[4])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[8])))
			return 100;
		else if (Character.toString(compCh).equals(Character.toString(g.getTokens()[2])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[4])) && Character.toString(compCh).equals(Character.toString(g.getTokens()[6])))
			return 100;
		
		//winning situations for the player
		else if (Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[0])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[1])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[2])))
			return 0;
		else if (Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[3])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[4])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[5])))
			return 0;
		else if (Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[6])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[7])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[8])))
			return 0;
		else if (Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[0])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[3])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[6])))
			return 0;
		else if (Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[1])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[4])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[7])))
			return 0;
		else if (Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[2])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[5])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[8])))
			return 0;
		else if (Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[0])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[4])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[8])))
			return 0;
		else if (Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[2])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[4])) && Character.toString(g.getPlayerCh()).equals(Character.toString(g.getTokens()[6])))
			return 0;
		
		//middle ground
		else
			return 50;

	}
}

