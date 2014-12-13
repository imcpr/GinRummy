package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;

/**
 * Represents a set of cards.
 */
public class CardSet implements ICardSet
{
	private final int SUIT_VALUE = 10;
	private ArrayList<Card> aHand = new ArrayList<Card>();
	
	/**
	 * @param pCards An set of cards
	 */
	public CardSet(Set<Card> pCards)
	{
		aHand.addAll(pCards);
	}
	
	/**
	 * @param pCard A card to check
	 * @return True if pCard is in this set
	 */
	public boolean contains(Card pCard)
	{
		for (Card c : aHand)
		{
			if (c.compareTo(pCard) == 0)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return The size of the group.
	 */
	public int size()
	{
		return aHand.size();
	}
	
	/**
	 * @return true if the object represents a group.
	 */
	public boolean isGroup()
	{
		if (size() < 3)
		{
			return false;
		}
		Rank tmpRank = null;
		for( Card c : aHand )
		{
			if (tmpRank == null)
			{
				tmpRank = c.getRank();
			}
			if (c.getRank() != tmpRank)
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return true if the object represents a run.
	 */
	public boolean isRun()
	{
		if (size() < 3)
		{
			return false;
		}
		ArrayList<Card> tmpHand = new ArrayList<Card>(aHand);
		Collections.sort(tmpHand);
//		System.out.println(tmpHand);
		int tmpRank = tmpHand.get(0).getRank().ordinal()-1;
		Suit tmpSuit = tmpHand.get(0).getSuit();
		for (Card c: tmpHand)
		{
			if ( ( c.getSuit() != tmpSuit) || (c.getRank().ordinal() != (tmpRank+1)) )
			{
				return false;
			}
			else
			{
				tmpRank ++;
			}
		}
		return true;
	}
	
	/**
	 * @return the value of the unmatched cards in this cardset.
	 */
	public int deadWoodValue()
	{
		if(isRun()||isGroup())
		{
			return 0;
		}
		else
		{
			int value = 0;
			for(Card c : aHand)
			{
				if(c.getRank().ordinal() > SUIT_VALUE)
				{
					value += SUIT_VALUE;
				}
				else
				{
					value += c.getRank().ordinal()+1;
				}
			}
			return value;
		}
			
	}
	
	@Override
	public Iterator<Card> iterator()
	{
		return aHand.iterator();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * @return See above.
	 */
	public String toString()
	{
		String myString = new String();
		for(Card c : aHand)
		{
			myString += c.getRank() + " of " + c.getSuit() + " | ";
		}
		return myString;
	}
//	public static void main(String[] args)
//	{
//		Set<Card> mHand = new HashSet<Card>();
//		mHand.add(new Card(Rank.EIGHT, Suit.SPADES));
//		mHand.add(new Card(Rank.NINE, Suit.SPADES));
//		mHand.add(new Card(Rank.EIGHT, Suit.CLUBS));
////		mHand.add(new Card(Rank.NINE, Suit.SPADES));
////		mHand.add(new Card(Rank.TEN, Suit.SPADES));
////		mHand.add(new Card(Rank.JACK, Suit.SPADES));
//		CardSet myCardSet = new CardSet(mHand);
//		System.out.println(myCardSet.size());
//		System.out.println(myCardSet.isGroup());
//		System.out.println(myCardSet.isRun());
//	}

}
