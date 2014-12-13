package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;


public class TestICardSet
{
	private CardSet groupCardSet;
	private CardSet runCardSet;
	private CardSet badCardSet;
	
	@Before
	public void setUp()
	{
		Set<Card> groupSet = new HashSet<Card>();
		groupSet.add(new Card(Rank.ACE, Suit.CLUBS));
		groupSet.add(new Card(Rank.ACE, Suit.DIAMONDS));
		groupSet.add(new Card(Rank.ACE, Suit.HEARTS));
		groupSet.add(new Card(Rank.ACE, Suit.SPADES));
		groupCardSet = new CardSet(groupSet);
		
		Set<Card> runSet = new HashSet<Card>();
		runSet.add(new Card(Rank.ACE, Suit.CLUBS));
		runSet.add(new Card(Rank.TWO, Suit.CLUBS));
		runSet.add(new Card(Rank.THREE, Suit.CLUBS));
		runSet.add(new Card(Rank.FOUR, Suit.CLUBS));
		runSet.add(new Card(Rank.FIVE, Suit.CLUBS));
		runCardSet = new CardSet(runSet);
		
		Set<Card> badSet = new HashSet<Card>();
		badSet.add(new Card(Rank.KING, Suit.HEARTS));
		badCardSet = new CardSet(badSet);
		
	}
	
	@Test
	public void testGroup()
	{
		
		assertTrue(groupCardSet.isGroup());
		assertTrue(!runCardSet.isGroup());
		assertTrue(!badCardSet.isGroup());
	}
	
	@Test
	public void testRun()
	{
		assertTrue(runCardSet.isRun());
		assertTrue(!groupCardSet.isRun());
		assertTrue(!badCardSet.isRun());
	}
	
	@Test
	public void testContains()
	{
		assertTrue(runCardSet.contains(new Card(Rank.FIVE, Suit.CLUBS)));
		assertTrue(groupCardSet.contains(new Card(Rank.ACE, Suit.SPADES)));
		
		assertTrue(!runCardSet.contains(new Card(Rank.KING, Suit.CLUBS)));
		assertTrue(!groupCardSet.contains(new Card(Rank.TWO, Suit.SPADES)));
	}
	
	@Test
	public void testDeadWood()
	{
		assertTrue(runCardSet.deadWoodValue()==0);
		assertTrue(groupCardSet.deadWoodValue()==0);
		assertTrue(badCardSet.deadWoodValue()==10);
	}

}
