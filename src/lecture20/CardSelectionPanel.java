package lecture20;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Supports displaying cards and selecting one. Also a nice demonstration
 * of the Composite and Decorator design patterns in action.
 * 
 * @author Martin P. Robillard McGill University 3 November 2014
 */
@SuppressWarnings("serial")
public class CardSelectionPanel extends JPanel
{
	private static final int H_SHIFT = 30;
	private static final int V_SHIFT = 20;
	private static final Color BACKGROUND_COLOR = new Color(0, 102, 0);
	private static final int CARD_HEIGHT = CardImages.getBack().getIconHeight();
	private static final int CARD_WIDTH = CardImages.getBack().getIconWidth();
	private static final int PREFERRED_HEIGHT = CardImages.getBack().getIconHeight() + V_SHIFT;
	
	private Card[] aCards; 
	private JLabel aLabel = new JLabel();
	private int aHovered = -1;
	private int aSelected = -1;
	
	/**
	 * Creates an empty hand panel.
	 * @param pMaxCards The maximum number of cards that will ever be displayed.
	 */
	public CardSelectionPanel(int pMaxCards)
	{
		aCards = new Card[pMaxCards];
		setPreferredSize(new Dimension((pMaxCards -1) * H_SHIFT + CARD_WIDTH, PREFERRED_HEIGHT));
		setBackground(BACKGROUND_COLOR);
		add(aLabel);
		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(MouseEvent pEvent)
			{
				aHovered = getSelectedCardIndex(pEvent.getX(), pEvent.getY());
				paintCards();
			}
		});
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent pEvent)
			{
				int selection = getSelectedCardIndex(pEvent.getX(), pEvent.getY());
				if( aSelected == selection )
				{
					aSelected = -1;
				}
				else
				{
					aSelected = selection;
				}
				paintCards();
			}
		});
	}
	
	/**
	 * Loads the cards and shows them on the panel.
	 * @param pCards The cards to load into the panel.
	 */
	public void loadCards(Card[] pCards)
	{
		aCards = Arrays.copyOf(pCards, aCards.length);
		paintCards();
	}
	
	private void paintCards()
	{
		CompositeIcon icon = new CompositeIcon();
		for( int i = 0; i < aCards.length; i++ )
		{
			if( aCards[i] == null )
			{
				continue;
			}
			int upShift = V_SHIFT;
			if( aHovered == i || aSelected == i)
			{
				upShift = 0;
			}
			icon.addIcon(new ShiftedIcon( CardImages.getCard(aCards[i]), i * H_SHIFT, upShift));
		}
		aLabel.setIcon(icon);
		repaint();
	}
	
	private int getSelectedCardIndex(int pX, int pY)
	{
		if( pY < aLabel.getY() || pY >= aLabel.getY() + CARD_HEIGHT)
		{
			return -1;
		}
		int trueX = pX - aLabel.getX();
		if( trueX < 0 || trueX >= aLabel.getWidth())
		{
			return -1;
		}
		return Math.min(trueX / H_SHIFT, aCards.length-1);
	}
}