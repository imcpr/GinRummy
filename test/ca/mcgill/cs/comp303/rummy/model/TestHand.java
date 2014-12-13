package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;


public class TestHand
{
	private Hand myHand = new Hand();
	
	@Before
	public void setUp()
	{
		Deck d = new Deck();
		for (int i = 0 ; i < 10 ; i ++)
		{
			myHand.add(d.draw());
		}
		myHand.autoMatch();
	}
	
	@Test
	public void testComplete()
	{
		assertTrue(myHand.isComplete());
	}
	
	@Test(expected=HandException.class)
	public void testDuplicate()
	{
		Hand tmpHand = new Hand();
		tmpHand.add(new Card(Rank.ACE, Suit.CLUBS));
		tmpHand.add(new Card(Rank.ACE, Suit.CLUBS));
	}
	
	@Test(expected=HandException.class)
	public void testFull()
	{
		myHand.add(new Card(Rank.ACE, Suit.CLUBS));
	}
	
	@Test
	public void testContains()
	{
		Hand tmpHand = new Hand();
		tmpHand.add(new Card(Rank.ACE, Suit.CLUBS));
		assertTrue(tmpHand.contains(new Card(Rank.ACE, Suit.CLUBS)));
	}
	
	@Test
	public void testRemove()
	{
		Hand tmpHand = new Hand();
		tmpHand.add(new Card(Rank.ACE, Suit.CLUBS));
		assertTrue(tmpHand.size()==1);
		tmpHand.remove(new Card(Rank.ACE, Suit.CLUBS));
		assertTrue(tmpHand.size()==0);
		assertTrue(!tmpHand.contains(new Card(Rank.ACE, Suit.CLUBS)));
	}
	
	@Test
	public void testClear()
	{
		Hand tmpHand = new Hand();
		tmpHand.add(new Card(Rank.ACE, Suit.CLUBS));
		assertTrue(tmpHand.size()==1);
		tmpHand.clear();
		assertTrue(tmpHand.size()==0);
	}
}
