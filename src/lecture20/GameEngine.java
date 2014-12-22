package lecture20;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Robillard 
 * Simulation of the GameEngine. Just maintains very basic state and 
 * has a partial observer mechanism.
 */
public class GameEngine
{
	private CardList aHand = new CardList();
	private List<GameObserver> aObservers = new ArrayList<GameObserver>();
	private Deck aDeck = new Deck();
	
	public void addObserver(GameObserver o)
	{
		aObservers.add(o);
	}
	
	public void notifyDeal()
	{
		for( GameObserver o : aObservers )
		{
			o.newHand(aHand.clone());
		}
	}
	
	public void notifyDiscard(Card pCard)
	{
		for( GameObserver o : aObservers )
		{
			o.discard(pCard);
		}
	}
	
	public void deal()
	{
		aHand = new CardList();
		aDeck.shuffle();
		for( int i = 0; i < 5; i++)
		{
			aHand.add(aDeck.draw());
		}
		aHand = aHand.sort();
		notifyDeal();
	}
	
	public void discard(Card pCard)
	{
		aHand.remove(pCard);
		notifyDiscard(pCard);
	}
	
}
