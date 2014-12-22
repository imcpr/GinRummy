package lecture20;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import lecture20.Card.Rank;
import lecture20.Card.Suit;

/**
 * A basic wrapper around the CardSelectionPanel.
 * 
 * @author Martin P. Robillard McGill University 2014
 *
 */
public final class CardSelectionDemo
{
	private CardSelectionDemo(){}
	
	/**
	 * Demonstration.
	 * @param pArgs Not used.
	 */
	public static void main(String[] pArgs)
	{
		// CSOFF:
		final Card[] cards = new Card[11];
		for( int i = 0; i < cards.length; i++)
		{
			cards[i] = new Card(Rank.values()[i], Suit.CLUBS);
		}
		SwingUtilities.invokeLater( new Runnable() {
			public void run()
			{
				CardSelectionPanel panel = new CardSelectionPanel(11);
				panel.loadCards(cards);
				JFrame frame = new JFrame("Demo for the Card Selection Panel");
				frame.add(panel);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setResizable(false);
				frame.setVisible(true); 
			}});
		// CSON:
	}
}
