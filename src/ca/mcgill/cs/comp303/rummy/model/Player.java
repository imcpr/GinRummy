package ca.mcgill.cs.comp303.rummy.model;

public class Player
{
	protected Hand aHand;
	private int aScore;
	
	public Player()
	{
		aHand = new Hand();
		aScore = 0;
	}
	
	public void add(Card c)
	{
		aHand.add(c);
	}
	
	public Hand getHand()
	{
		return aHand;
	}
	
	public void discard(Card c, Boolean draw)
	{
		if(aHand.contains(c))
		{
			System.out.println("Discarding card "+c);
			aHand.remove(c);
			if (draw)
			{
				GameEngine.getInstance().setDiscardCard(c);
				draw(null);
			}
			else
			{
				Card cc = GameEngine.getInstance().getDiscardCard();
				GameEngine.getInstance().setDiscardCard(c);
				draw(cc);
			}
		}
		else
		{
			throw new HandException("Attempted to discard card not in hand");
		}
	}
	
	public void draw(Card cc)
	{
		Card d = null;
		if (cc == null)
		{
			d= GameEngine.getInstance().drawTop();
			System.out.println("Drawing from deck"+d);
		}
		else
		{
			d = cc;
			System.out.println("Drawing from discard"+d);
		}
		aHand.add(d);
		GameEngine.getInstance().notifyObservers();
	}
	
	public int getScore()
	{
		return aScore;
	}
	
	public int getDeadWood()
	{
		aHand.autoMatch();
		return (new CardSet(aHand.getUnmatchedCards()).deadWoodValue());
	}
	
	public String toString()
	{
		return aHand.toString();
	}
}
