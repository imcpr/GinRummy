package ca.mcgill.cs.comp303.rummy.model;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;

public class GreedyBot extends Player
{
	public void play()
	{
		aHand.autoMatch();
		System.out.println(aHand);
		System.out.println("DW value = "+new CardSet(aHand.getUnmatchedCards()).deadWoodValue());
		if( new CardSet(aHand.getUnmatchedCards()).deadWoodValue() < 10)
		{
			System.out.println("KNOCKING");
			knock();
		}
		else
//		if(true)
		{
			Card highest = new Card(Rank.ACE, Suit.CLUBS);
			for(Card c: aHand)
			{
				if (c.getRank().ordinal() > highest.getRank().ordinal())
				{
					highest = c;
				}
			}
			if(GameEngine.getInstance().getDiscardCard()== null || GameEngine.getInstance().getDiscardCard().getRank().ordinal() <= highest.getRank().ordinal())
			{
				discard(highest, false);
//				draw(null);
			}
			else
			{
				Card old = GameEngine.getInstance().getDiscardCard();
				discard(highest, false);
				aHand.add(old);
				System.out.println("Getting card from discard pile "+old);
				GameEngine.getInstance().setDiscardCard(highest);
			}
		}	
	}
	
	public void knock()
	{
		GameEngine.getInstance().endGame();
	}
}
