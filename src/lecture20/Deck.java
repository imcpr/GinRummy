package lecture20;

import java.util.Collections;
import java.util.Stack;

import lecture20.Card.Rank;
import lecture20.Card.Suit;

/**
 * Models a deck of 52 cards (no joker).
 */
public class Deck 
{
	private Stack<Card> aCards = new Stack<Card>();
	
	public Deck()
	{
		shuffle();
	}

	public void shuffle()
	{
		aCards.clear();
		for(Suit suit : Suit.values())
		{
			for(Rank rank : Rank.values())
			{
				aCards.add(new Card(rank,suit));
			}
		}
		Collections.shuffle(aCards);
	}
	
	public Card draw()
	{
		return aCards.pop();
	}
	
	public int size()
	{
		return aCards.size();
	}
}
