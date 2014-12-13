package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;

/**
 * Models a hand of 10 cards. The hand is not sorted. Not threadsafe.
 * The hand is a set: adding the same card twice will not add duplicates
 * of the card.
 * @inv size() > 0
 * @inv size() <= HAND_SIZE
 */
public class Hand implements Iterable<Card>
{

	public static final int MAX_HAND_SIZE = 10;
	public static final int FACE_CARD_VALUE = 10;
	private Set<Card> aHand;
	private Set<ICardSet> aMatchedSets;
	/**
	 * Creates a new, empty hand.
	 */
	public Hand()
	{
		// TODO
		aHand = new HashSet<Card>();
		aMatchedSets = new HashSet<ICardSet>();
	}
	
	/**
	 * Adds pCard to the list of unmatched cards.
	 * If the card is already in the hand, it is not added.
	 * @param pCard The card to add.
	 * @throws HandException if the hand is complete.
	 * @throws HandException if the card is already in the hand.
	 * @pre pCard != null
	 */
	public void add( Card pCard )
	{
		// TODO
		if(aHand.size() == MAX_HAND_SIZE)
		{
			throw new HandException("Hand is already complete");
		}
		if (!aHand.add(pCard))
		{
			throw new HandException("The Card is already in the hand!");
		}
	}
	
	/**
	 * Remove pCard from the hand and break any matched set
	 * that the card is part of. Does nothing if
	 * pCard is not in the hand.
	 * @param pCard The card to remove.
	 * @pre pCard != null
	 */
	public void remove( Card pCard )
	{
		autoMatch();
		ICardSet toRemove = new CardSet(new HashSet<Card>());
		aHand.remove(pCard);
		for(ICardSet matchedSet : aMatchedSets)
		{
			if (matchedSet.contains(pCard))
			{
				toRemove = matchedSet;
			}
		}
		aMatchedSets.remove(toRemove);
		// TODO
	}
	
	/**
	 * @return True if the hand is complete.
	 */
	public boolean isComplete()
	{
		return aHand.size() == MAX_HAND_SIZE;
	}
	
	/**
	 * Removes all the cards from the hand.
	 */
	public void clear()
	{
		aHand.clear();
		aMatchedSets.clear();
	}
	
	/**
	 * @return A copy of the set of matched sets
	 */
	public Set<ICardSet> getMatchedSets()
	{
		//autoMatch();
		return Collections.unmodifiableSet(aMatchedSets); // TODO
	}
	
	/**
	 * @return A copy of the set of unmatched cards.
	 */
	public Set<Card> getUnmatchedCards()
	{
		//autoMatch();
		Set<Card> tmpSet = new HashSet<Card>();
		boolean isMatched;
		for (Card c : aHand)
		{
			isMatched = false;
			for(ICardSet matchedSet : aMatchedSets)
			{
				if(matchedSet.contains(c))
				{
					isMatched = true;
				}
			}
			if(!isMatched)
			{
				tmpSet.add(c);
			}
		}
		return tmpSet; // TODO
	}
	
	/**
	 * @return The number of cards in the hand.
	 */
	public int size()
	{
		return aHand.size();
	}
	
	/**
	 * Determines if pCard is already in the hand, either as an
	 * unmatched card or as part of a set.
	 * @param pCard The card to check.
	 * @return true if the card is already in the hand.
	 * @pre pCard != null
	 */
	public boolean contains( Card pCard )
	{
		return aHand.contains(pCard);
	}
	
	/**
	 * @return The total point value of the unmatched cards in this hand.
	 */
	public int score()
	{
		autoMatch();
		int score = 0;
		Set<Card> unmatchedCards = getUnmatchedCards();
		for (Card c : unmatchedCards)
		{
			if(c.getRank() == Rank.JACK || c.getRank() == Rank.QUEEN ||
					 c.getRank() == Rank.KING)
			{
				score += FACE_CARD_VALUE;
			}
			else
			{
				score += c.getRank().ordinal()+1;
			}
		}
		return score; // TODO
	}
	
	/**
	 * Checks if pCards all belong to cards that are not in a group or run.
	 * @param pCards the set of cards to check
	 * @return true if cards are all unmatched
	 * @pre pCards.size() > 0
	 */
	private boolean areAllUnmatched( Set<Card> pCards )
	{
		Set<Card> unmatchedCards = getUnmatchedCards();
		for (Card c : pCards)
		{
			if(!unmatchedCards.contains(c))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Creates a group of cards of the same rank.
	 * @param pCards The cards to groups
	 * @pre pCards != null
	 * @throws HandException If the cards in pCard are not all unmatched
	 * cards of the hand or if the group is not a valid group.
	 */
	public void createGroup( Set<Card> pCards )
	{
		// TODO
		ICardSet tmpSet = new CardSet(pCards);
		if( !areAllUnmatched(pCards) )
		{
			throw new HandException("Not all the cards in pCards are unmatched cards of the hand");
		}
		if (!tmpSet.isGroup())
		{
			throw new HandException("Attempted to create group but is not a valid group");
		}
		aMatchedSets.add(tmpSet);
	}
	
	/**
	 * Creates a run of cards of the same suit.
	 * @param pCards The cards to group in a run
	 * @pre pCards != null
	 * @throws HandException If the cards in pCard are not all unmatched
	 * cards of the hand or if the group is not a valid group.
	 */
	public void createRun( Set<Card> pCards )
	{
		// TODO
		ICardSet tmpSet = new CardSet(pCards);
		if( !areAllUnmatched(pCards) )
		{
			throw new HandException("Not all the cards in pCards are unmatched cards of the hand");
		}
		if (!tmpSet.isRun())
		{
			throw new HandException("Attempted to create group but is not a valid group");
		}
		aMatchedSets.add(tmpSet);
	}
	
	private Set<Set> getGroups( Set<Card> pCards )
	{
		//ArrayList<Set> matchedGroups = new ArrayList<Set>();
		int rankSize = Rank.values().length;
		Set[] bin = new Set[rankSize];
		for(int i = 0; i < rankSize; i++)
		{
			bin[i] = new HashSet<Card>();
		}
		for(Card c : pCards)
		{
			bin[c.getRank().ordinal()].add(c);
		}
		Set<Set> matchedGroups = new HashSet<Set>();
		for(int i = 0; i < rankSize; i++)
		{
			if (bin[i].size()>2)
			{
				matchedGroups.add(new HashSet(bin[i]));
			}
		}
		return matchedGroups;
	}
	
	private Set<Set> getPartialGroups( Set<Card> pCards )
	{
		//ArrayList<Set> matchedGroups = new ArrayList<Set>();
		int rankSize = Rank.values().length;
		Set[] bin = new Set[rankSize];
		for(int i = 0; i < rankSize; i++)
		{
			bin[i] = new HashSet<Card>();
		}
		for(Card c : pCards)
		{
			bin[c.getRank().ordinal()].add(c);
		}
		Set<Set> matchedGroups = new HashSet<Set>();
		for(int i = 0; i < rankSize; i++)
		{
			if (bin[i].size()==2)
			{
				matchedGroups.add(new HashSet(bin[i]));
			}
		}
		return matchedGroups;
	}
	
	private ArrayList<Card> sortCardsByHash( ArrayList<Card> pCards)
	{
		Collections.sort(pCards, new Comparator<Card>()
		{
			@Override
			public int compare(Card p1, Card p2)
			{
				// TODO Auto-generated method stub
				return p1.hashCode()-p2.hashCode();
			}
			
		});
		return pCards;
	}
	
	private int sequenceCount(Card pCard, List<Card> pCards)
	{
		if (pCards.size() == 0)
		{
			return 0;
		}
		/*
		if(pCards.get(0).getSuit() != pCard.getSuit())
		{
			return sequenceCount(pCard, pCards.subList(1, pCards.size()));
		}*/
		if(pCards.get(0).getRank().ordinal() == (pCard.getRank().ordinal()+1) 
				&& pCards.get(0).getSuit() == pCard.getSuit())
		{
			Card next = pCards.get(0);
			return 1+sequenceCount(next, pCards.subList(1, pCards.size()));
		}
		else
		{
			return 0;
		}
	}
	private Set<Set> getRuns( Set<Card> pCards )
	{
		ArrayList<Card> sortedCards = new ArrayList<Card>(pCards);
		Collections.sort(sortedCards);
		//System.out.println("sorted by Rank \n" + sortedCards);
		sortedCards = sortCardsByHash(sortedCards);
		//System.out.println("sorted by hash \n" + sortedCards);
		//Suit storeSuit = null;
		int subtractor = 0;
		Set<Card> storeSet = null;
		Set<Set> matchedSets = new HashSet<Set>();
		for( int i = 0; i < sortedCards.size(); i ++)
		{
			Card c = sortedCards.get(i);
			if( subtractor > 0 &&  storeSet != null)
			{
				storeSet.add(c);
				subtractor --;
				if (subtractor == 0)
				{
					matchedSets.add(new HashSet(storeSet));
					//storeSuit = null;
					storeSet = null;
				}
			}
			else
			{
				List<Card> subList = sortedCards.subList(i+1, sortedCards.size());
				int seq = sequenceCount(c, subList);
				if(seq >= 2)
				{
					subtractor = seq;
					//storeSuit = c.getSuit();
					storeSet = new HashSet<Card>();
					storeSet.add(c);
				}
			}
		}
		return matchedSets;
	}
	
	private Card hasRank(Rank pRank, Set<Card> pCards)
	{
		for (Card c : pCards)
		{
			if(c.getRank() == pRank)
			{
				return c;
			}
		}
		return null;
	}
	
	private int value(Set<Card> pCards)
	{
		CardSet tmp = new CardSet(pCards);
		return tmp.deadWoodValue();
	}
	
	private void adjustPartialGroups(Set<Set> pPartialGroups, Set<Set> pCompleteRuns)
	{
		for(Set<Card> partialGroup : pPartialGroups)
		{
			Rank cRank = null;
			for(Card c : partialGroup)
			{
				cRank = c.getRank();
				break;
			}
			for(Set<Card> run : pCompleteRuns)
			{
				Card thatCard = hasRank(cRank, run);
				if(thatCard != null)
				{
					//so here we know if it has
					ArrayList<Card> tmpRun = new ArrayList<Card>(run);
					Collections.sort(tmpRun);
					int point = tmpRun.indexOf(thatCard);
					int originalDeadWood = (cRank.ordinal()+1)*2;
					if(point != -1)
					{
						int leftList = value(new HashSet<Card>(tmpRun.subList(0, point)));
						int rightList = value(new HashSet<Card>(tmpRun.subList(point+1, tmpRun.size())));
						int newDeadWood = leftList + rightList;
						if (newDeadWood < originalDeadWood)
						{
							//dont think i need these
//							run.remove(thatCard);
							pCompleteRuns.remove(run);
							//System.out.println("Breaking set");
							if(leftList == 0)
							{
								Set<Card> leftAddSet = new HashSet<Card>(tmpRun.subList(0, point));
								if(leftAddSet.size() > 2)
								{
									createRun(leftAddSet);
								}
							}
							if(rightList == 0)
							{
								Set<Card> rightAddSet = new HashSet<Card>(tmpRun.subList(point+1, tmpRun.size()));
								if(rightAddSet.size() > 2)
								{
									createRun(rightAddSet);
								}
							}
							Set<Card> newGroup = new HashSet<Card>(partialGroup);
							newGroup.add(thatCard);
							createGroup(newGroup);
						}
//						else
//						{
//							createRun(run);
//						}
						
					}
				}
//				else
//				{
//					createRun(run);
//				}
			}
		}
		for(Set<Card> run : pCompleteRuns)
		{
			createRun(run);
		}
	}
	
	public static void main(String[] args)
	{
		//Set<Card> mHand = new HashSet<Card>();
		Hand mHand = new Hand();
//		//mHand.add(new Card(Rank.TWO, Suit.HEARTS));
//		mHand.add(new Card(Rank.THREE, Suit.HEARTS));
//		mHand.add(new Card(Rank.FOUR, Suit.HEARTS));
//		mHand.add(new Card(Rank.FIVE, Suit.HEARTS));
//		mHand.add(new Card(Rank.FIVE, Suit.CLUBS));
//		mHand.add(new Card(Rank.FIVE, Suit.DIAMONDS));
//		//mHand.add(new Card(Rank.FIVE, Suit.SPADES));
//		mHand.add(new Card(Rank.ACE, Suit.SPADES));
		
//		mHand.add(new Card(Rank.TWO, Suit.SPADES));
//		mHand.add(new Card(Rank.THREE, Suit.SPADES));
//		mHand.add(new Card(Rank.FOUR, Suit.SPADES));
//		mHand.add(new Card(Rank.FIVE, Suit.SPADES));
//		mHand.add(new Card(Rank.SIX, Suit.SPADES));
//		mHand.add(new Card(Rank.SEVEN, Suit.SPADES));
//		mHand.add(new Card(Rank.EIGHT, Suit.SPADES));
//		mHand.add(new Card(Rank.FIVE, Suit.CLUBS));
//		mHand.add(new Card(Rank.FIVE, Suit.DIAMONDS));
		
//		mHand.add(new Card(Rank.FIVE, Suit.HEARTS));
//		mHand.add(new Card(Rank.FIVE, Suit.CLUBS));
//		mHand.add(new Card(Rank.FIVE, Suit.DIAMONDS));
////		mHand.add(new Card(Rank.SIX, Suit.HEARTS));
//		mHand.add(new Card(Rank.SIX, Suit.CLUBS));
//		mHand.add(new Card(Rank.SIX, Suit.DIAMONDS));
//		mHand.add(new Card(Rank.EIGHT, Suit.HEARTS));
//		mHand.add(new Card(Rank.EIGHT, Suit.CLUBS));
//		mHand.add(new Card(Rank.EIGHT, Suit.DIAMONDS));
		
//		mHand.add(new Card(Rank.NINE, Suit.SPADES));
//		mHand.add(new Card(Rank.TEN, Suit.SPADES));
////		mHand.add(new Card(Rank.JACK, Suit.SPADES));
//		CardSet myCardSet = new CardSet(mHand);
//		System.out.println(myCardSet.size());
//		System.out.println(myCardSet.isGroup());
//		System.out.println(myCardSet.isRun());
		//Hand hi = new Hand();
//		//ArrayList<Card> sublist = new ArrayList<Card>(mHand);
//		Collections.sort(sublist);
//		System.out.println(sublist.subList(0, sublist.size()));
//		//System.out.println(hi.sequenceCount(new Card(Rank.EIGHT, Suit.SPADES), sublist.subList(1, sublist.size())));
////		System.out.println(hi.getRuns(mHand));
		
		
//		Set<Card> tmpSet = new HashSet<Card>();
//		mHand.add(new Card(Rank.ACE, Suit.CLUBS));
//		mHand.add(new Card(Rank.TWO, Suit.CLUBS));
//		mHand.add(new Card(Rank.THREE, Suit.DIAMONDS));
//		mHand.add(new Card(Rank.FOUR, Suit.SPADES));
//		mHand.add(new Card(Rank.FIVE, Suit.SPADES));
//		mHand.add(new Card(Rank.SIX, Suit.SPADES));
//		mHand.add(new Card(Rank.NINE, Suit.DIAMONDS));
//		mHand.add(new Card(Rank.JACK, Suit.HEARTS));
//		mHand.add(new Card(Rank.KING, Suit.HEARTS));
//		mHand.add(new Card(Rank.KING, Suit.SPADES));
		
		
		
//		Deck myd = new Deck();
//		myd.shuffle();
//		for(int i = 0; i < 10 ; i++)
//		{
//			mHand.add(myd.draw());
//		}
//		mHand.autoMatch();
		
		
		
		//mHand.createGroup(tmpSet);
	}
	
	/**
	 * Calculates the matching of cards into groups and runs that
	 * results in the lowest amount of points for unmatched cards.
	 */
	public void autoMatch()
	{
		aMatchedSets.clear();
//		System.out.println("Automatch start----------------------------------------------------------------");
		Set<Card> tmpHand = new HashSet<Card>(aHand);
//		System.out.println("automatch: clear, unmatched Cards " + tmpHand.size());
		Set<Set> completeRuns = getRuns(tmpHand);
		if (!completeRuns.isEmpty())
		{
//			System.out.println("autoatch: got complete runs: " + completeRuns);
			//aMatchedSets.addAll(completeRuns);
			for(Set run : completeRuns)
			{
				//createRun(run);
				tmpHand.removeAll(run);
			}
		}
//		System.out.println("automatch: Complete runs, unmatched Cards " + tmpHand.size());
		Set<Set> completeGroups = getGroups(tmpHand);
		if (!completeGroups.isEmpty())
		{
//			System.out.println("autoatch: got complete groups: " + completeGroups);
			//aMatchedSets.addAll(completeGroups);
			for(Set group : completeGroups)
			{
				createGroup(group);
				tmpHand.removeAll(group);
			}
		}
//		System.out.println("automatch: Complete groups, unmatched Cards " + tmpHand.size());
		Set<Set> partialGroups = getPartialGroups(tmpHand);
		if (!partialGroups.isEmpty())
		{
//			System.out.println("autoatch: got partial groups: " + partialGroups);
			adjustPartialGroups(partialGroups, completeRuns);
		}
		else
		{
			for(Set run : completeRuns)
			{
				createRun(run);
			}
		}
//		System.out.println("automatch: END, unmatched Cards " + getUnmatchedCards().size());
//		System.out.println(getMatchedSets());
//		System.out.println("unmatched Cards : " + getUnmatchedCards());
//		System.out.println("Automatch end----------------------------------------------------------------");
		
	}

	public String toString()
	{
		return "full hand = "+aHand.toString()+"\n unmatched = " + getUnmatchedCards() + "\n matched = "+aMatchedSets.toString()+"\nDW = "+ (new CardSet(getUnmatchedCards()).deadWoodValue());
	}
	@Override
	public Iterator<Card> iterator()
	{
		// TODO Auto-generated method stub
		return aHand.iterator();
	}
}
