package lecture20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * A mutable list of cards. Does not support duplicates.
 * The cards are maintained in the order added.
 */
public class CardList implements Iterable<Card>, Cloneable
{
	protected List<Card> aCards;
	
	/**
	 * Creates a new, empty card list.
	 */
	public CardList()
	{
		aCards = new ArrayList<Card>();
	}
	
	public Card get(int i )
	{
		return aCards.get(i);
	}
	
	/**
	 * Adds a card if it is not
	 * already in the list. Has no effect if the card
	 * is already in the list.
	 * @param pCard The card to add.
	 * @pre pCard != null
	 */
	public void add(Card pCard)
	{
		assert pCard != null;
		if( !aCards.contains(pCard ))
		{
			aCards.add(pCard);
		}
	}
	
	/**
	 * @return The number of cards in the list.
	 */
	public int size()
	{
		return aCards.size();
	}
	
	/**
	 * @return The first card in the list, according to whatever
	 * order is currently being used. 
	 * @throws GameException if the list is empty.
	 */
	public Card getFirst()
	{
		if( aCards.size() == 0 )
		{
			throw new GameUtilException("Empty CardList");
		}
		return aCards.get(0);
	}
	
	/**
	 * @return The last card in the list, according to whatever
	 * order is currently being used. 
	 * @throws GameException If the list is empty.
	 */
	public Card getLast()
	{
		if( aCards.size() == 0 )
		{
			throw new GameUtilException("Empty CardList");
		}
		return aCards.get(aCards.size()-1);
	}
	
	/**
	 * Removes a card from the list. Has no effect if the card is
	 * not in the list.
	 * @param pCard The card to remove. 
	 * @pre pCard != null;
	 */
	public void remove(Card pCard)
	{
		assert pCard != null;
		aCards.remove(pCard);
	}
	
	/**
	 * @see java.lang.Object#clone()
	 * {@inheritDoc}
	 */
	public CardList clone()
	{
		CardList lClone = null;
		try
		{
			lClone = (CardList)super.clone();
			lClone.aCards = new ArrayList<Card>(aCards);
		}
		catch( CloneNotSupportedException lException)
		{}
		return lClone;
	}
	
	/**
	 * @see java.lang.Iterable#iterator()
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Card> iterator()
	{
		return aCards.iterator();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * {@inheritDoc}
	 */
	public String toString()
	{
		String lReturn = "";
		for(Card card : aCards)
		{
			lReturn += " " + card;
		}
		return lReturn;
	}
	
	/**
	 * @pre aCards.size() > 0
	 * @return A randomly-chosen card from the set. 
	 */
	public Card random()
	{
		assert aCards.size() > 0;
		ArrayList<Card> lList = new ArrayList<Card>(aCards);
		Collections.shuffle(lList);
		return lList.get(0);
	}
	
	/**
	 * Returns another CardList, sorted as desired. This
	 * method has no side effects.
	 * @param pComparator Defines the sorting order.
	 * @return the sorted CardList
	 * @pre pComparator != null
	 */
	public CardList sort()
	{
		CardList lReturn = new CardList();
		ArrayList<Card> lList = new ArrayList<Card>(aCards);
		Collections.sort(lList);
		for( Card card : lList )
		{
			lReturn.add(card);
		}
		return lReturn;
	}
}
