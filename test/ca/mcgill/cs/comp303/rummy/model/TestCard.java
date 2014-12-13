package ca.mcgill.cs.comp303.rummy.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;


public class TestCard
{
	@Test
	public void testEqual()
	{
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		Card c2 = new Card(Rank.ACE, Suit.CLUBS);
		Card c3 = new Card(Rank.ACE, Suit.DIAMONDS);
		assertTrue(c1.equals(c2));
		assertTrue(!c1.equals(new Object()));
		assertTrue(!c1.equals(c3));
	}
	@Test
	public void testCompare()
	{
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		Card c2 = new Card(Rank.TWO, Suit.CLUBS);
		Card c3 = new Card(Rank.ACE, Suit.DIAMONDS);
		assertTrue(c1.compareTo(c2)<0);
		assertTrue(c2.compareTo(c1) > 0);
		assertTrue(c1.compareTo(c3)<0);
	}
	@Test
	public void testHashCode()
	{
		Card c1 = new Card(Rank.ACE, Suit.CLUBS);
		assertTrue(c1.hashCode()==0);
	}
}
