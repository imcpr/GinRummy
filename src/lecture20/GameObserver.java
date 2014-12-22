package lecture20;


public interface GameObserver
{
	void newHand(CardList pNewHand);
	void discard(Card pCard);
}
