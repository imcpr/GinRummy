package lecture20;


import java.awt.Component;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * @Author Martin Robillard
 * Manages a CardList and supports selection of an individual card.
 * Illustrates the coupling challenges seen in class: not necessarily 
 * the best design for this functionality.
 */
@SuppressWarnings("serial")
public class CardPanel extends JPanel implements GameObserver
{
	// Read-only: not synchronized with the GameEngine
	private HashMap<JLabel,Card> aCards = new HashMap<JLabel,Card>();
	
	public CardPanel()
	{
		super(new OverlapLayout(new Point(30, 0)));
		setBorder(new TitledBorder( "Martin's Hand" ));
		
		Insets ins = new Insets(10, 0, 0, 0);
		((OverlapLayout)getLayout()).setPopupInsets(ins);
		setBackground( GameFrame.BACKGROUND_COLOR );
	}
	
	private void initialize(CardList pCards)
	{
		aCards.clear();
		removeAll();
		for( Card card : pCards )
		{
			JLabel lLabel = new JLabel( CardImages.getCard(card));
			aCards.put(lLabel,card);
			lLabel.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
				    Component c = e.getComponent();
				    Boolean constraint = ((OverlapLayout)getLayout()).getConstraints(c);

				    if (constraint == null || constraint == OverlapLayout.POP_DOWN)
				    {
				    	popAllDown();
				    	((OverlapLayout)getLayout()).addLayoutComponent(c, OverlapLayout.POP_UP);
				    }
				    else
				    {
				    	((OverlapLayout)getLayout()).addLayoutComponent(c, OverlapLayout.POP_DOWN);
				    }

				    c.getParent().invalidate();
				    c.getParent().validate();
				}
			});
			add(lLabel);
		}
		validate();
		repaint();
	}
	
	private void popAllDown()
	{
		Component[] lChildren = getComponents();
		for( Component component : lChildren )
		{
			((OverlapLayout)getLayout()).addLayoutComponent(component, OverlapLayout.POP_DOWN);
		}
	}
	
	/**
	 * @return The card that is up. Null if none.
	 */
	public Card isUp()
	{
		for( Component component : getComponents() )
		{
			Boolean constraint = ((OverlapLayout)getLayout()).getConstraints(component);
			if (constraint != null && constraint == OverlapLayout.POP_UP)
			{
				return aCards.get(component);
			}
		}
		return null;
	}

	@Override
	public void newHand(CardList pNewHand)
	{
		initialize(pNewHand);
	}

	@Override
	public void discard(Card pCard)
	{
		JLabel lToRemove = null;
		for( JLabel label : aCards.keySet())
		{
			if( aCards.get(label).equals(pCard))
			{
				lToRemove = label;
				break;
			}
		}
		aCards.remove(lToRemove);
		removeAll();
		CardList lList = new CardList();
		for(Card card : aCards.values())
		{
			lList.add(card);
		}
		lList = lList.sort();
		initialize(lList);
		validate();
		repaint();
	}
}
