package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;

public class GameEngine
{
	private static GameEngine INSTANCE = new GameEngine();
	
	public enum GameState {PLAYING, END, UNINITIALIZED};
	
	Deck aDeck = new Deck();
	Player aPlayer;
	Player aComputer;
//	GreedyBot aPlayer;
//	GreedyBot aComputer;
	Card aTopDiscard;
	Player aDealer;
	GameState aGameState = GameState.UNINITIALIZED;
	ArrayList<Observer> aObservers = new ArrayList<Observer>();
	
	private GameEngine()
	{
		
	}
	
//	public static void main(String[] args)
//	{
//		GameEngine ge = GameEngine.getInstance();
//		ge.newGame();
//		while(ge.cardCount()>2 && ge.getGameState() == GameState.PLAYING)
//		{
//			if(ge.cardCount() == 2)
//				ge.endGame();
//			System.out.println("Card coutn is " + ge.cardCount());
//			System.out.println("human playing-------");
//			ge.getHumanPlayer().play();
//			if(ge.cardCount() == 2)
//				ge.endGame();
//			if (ge.getGameState() != GameState.END)
//			{
//				System.out.println("cpu playing-------");
//				ge.getComputerPlayer().play();
//			}
//		}
//		if(ge.cardCount() == 2)
//			ge.endGame();
//	}
	
	public static GameEngine getInstance()
	{
		return INSTANCE;
	}
	
	public void newGame()
	{
		aGameState = GameState.PLAYING;
		aDeck.shuffle();
		aTopDiscard = null;
//		aPlayer = new Player();
//		aComputer = new Player();
		aPlayer = new Player();
		aComputer = new GreedyBot();
		if (getDealer() == null)
		{
			aDealer = aComputer;
		}
		else
		{
			// switch dealer, idk if this is right
			aDealer = (aDealer==aComputer) ? aPlayer : aComputer;
		}
		//deal to player
		for(int i = 0; i < 10; i++)
		{
			aPlayer.add(aDeck.draw());
		}
		//deal to CPU
		for(int i = 0; i < 10; i++)
		{
			aComputer.add(aDeck.draw());
		}
		
	}
	
	public void printResults()
	{
		System.out.println("**********************************************************************");
		System.out.println("HUMAN: ");
		System.out.println(aPlayer.toString());
		System.out.println("**********************************************************************");
		System.out.println("CPU: ");
		System.out.println(aComputer.toString());
		System.out.println("**********************************************************************");
	}
	public void endGame()
	{
		if(aPlayer.getDeadWood() >= aComputer.getDeadWood() && aComputer.getDeadWood() <=10)
		{
			aGameState = GameState.END;
			System.out.println("Computer wins with deadwood score of " + aComputer.getDeadWood());
			printResults();
		}
		else if(aPlayer.getDeadWood() < aComputer.getDeadWood() && aPlayer.getDeadWood() <=10)
		{
			aGameState = GameState.END;
			System.out.println("Player wins with deadwood score of " + aPlayer.getDeadWood());
			printResults();
			
		}
		else
		{
			aGameState = GameState.END;
			System.out.println("Game is cancelled because Card count is " + cardCount());
			printResults();
		}
	}
	
	public Player getDealer()
	{
		return aDealer;
	}
	
	public Player getHumanPlayer()
	{
		return aPlayer;
	}
	
	public Player getComputerPlayer()
	{
		return aComputer;
	}
	
	public void setDiscardCard(Card c)
	{
		aTopDiscard = c;
		notifyObservers();
	}
	
	public Card getDiscardCard()
	{
		return aTopDiscard;
	}
	
	public Card drawTop()
	{
		return aDeck.draw();
	}
	
	public Card peek()
	{
		return aDeck.peek();
	}
	
	public int cardCount()
	{
		return aDeck.size();
	}
	
	public GameState getGameState()
	{
		return aGameState;
	}
	
	public void addObserver(Observer pObserver)
	{
		aObservers.add(pObserver);
	}
	
	public void notifyObservers()
	{
		for(Observer iObserver: aObservers)
			iObserver.newEvent();
	}
	
}
