package lecture20;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 * @author Martin Robillard 
 *
 * Illustrates design tradeoffs related to component coupling,
 * and how to implement non-blocking delays in the UI. 
 * Warning: the code does not represent best practices.
 */
@SuppressWarnings("serial")
public class ShowPanel extends JPanel implements GameObserver
{
	private static final int WIDTH = 150;
	private static final int HEIGHT = 150;
	
	public ShowPanel()
	{
		setBorder( new EmptyBorder( 5, 5, 5, 5 ));
		setBackground( GameFrame.BACKGROUND_COLOR );
		setMinimumSize(new Dimension(WIDTH,HEIGHT));
	}
	
	@Override
	public void newHand(CardList pNewHand)
	{
		removeAll();
		repaint();
	}

		
    //@Override
	public void discard(final Card pCard)
	{
		// Obtain the speed. Used to demonstrate navigating the component hierarchy. 
		// Not necessarily a best practice. Any change to the component composition
		// will break this code.
		JSlider lSlider = (JSlider)((JPanel)getParent().getParent().getComponent(1)).getComponent(1);
	
		new Timer(lSlider.getValue() * 500, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				removeAll();
				JLabel lLabel = new JLabel( CardImages.getCard(pCard));
				add(lLabel);	
				validate();
				repaint(); 		
				((Timer)e.getSource()).stop();
			}
		}).start();
	}
	
	// This version of the call back (rename "discard" to plug it in)
    // will freeze the GUI.
	public void discardBlocking(final Card pCard)
	{
		long start = System.currentTimeMillis();
		while( System.currentTimeMillis() < start + 5000 )
		{
			// do nothing
		}
		removeAll();
		JLabel lLabel = new JLabel( CardImages.getCard(pCard));
		add(lLabel);
		validate();
		repaint(); 		

	}
	
	/**
	 * This version of the callback is not thread safe!
	 */
	public void discardInvalidThreadInteraction(final Card pCard)
	{
		new Thread( new Runnable()
		{	
			@Override
			public void run()
			{
				long start = System.currentTimeMillis();
				while( System.currentTimeMillis() < start + 5000 )
				{
					// do nothing
				}
				// The calls below call into the Even thread from a different
				// thread, which violates the thread containment policy of swing.
				removeAll();
				JLabel lLabel = new JLabel( CardImages.getCard(pCard));
				add(lLabel);	
				validate();
				repaint(); 						
			}
		}).start();
	}
}
