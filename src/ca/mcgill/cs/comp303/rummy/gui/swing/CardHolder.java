package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Deck;
import ca.mcgill.cs.comp303.rummy.model.GameEngine;
import ca.mcgill.cs.comp303.rummy.model.GreedyBot;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.Observer;
import ca.mcgill.cs.comp303.rummy.model.Player;

public class CardHolder extends JPanel implements Observer
{
	private ArrayList<Card> aCards = new ArrayList<Card>();
	private Deck aDeck = null;
	private ArrayList<JLabel> aCardImages = new ArrayList<JLabel>();
	private Boolean abot = false;
	
	public CardHolder ()
	{
		super(new FlowLayout());
		initiateCards();
	}
	public CardHolder (Boolean bot)
	{
		super(new FlowLayout());
		abot = bot;
		initiateCards();
	}
	
	
	private Player getPlayer()
	{
		if (abot)
			return GameEngine.getInstance().getComputerPlayer();
		else
			return  GameEngine.getInstance().getHumanPlayer();
	}
	
	private void initiateCards()
	{
//		for(JLabel prev : aCardImages)
//		{
//			remove(prev);
//		}
		removeAll();
		aCardImages = new ArrayList<JLabel>();
		aCards = new ArrayList<Card>();
	
		for(Card lCard : getPlayer().getHand())
		{
//			System.out.println("Re-added "+lCard);
//			Card lCard = aDeck.draw();
			aCards.add(lCard);
			JLabel lLabel = new JLabel();
			lLabel.setIcon(CardImages.getCard(lCard));
			CardClickListener ccl = new CardClickListener();
			lLabel.addMouseListener(ccl);
			aCardImages.add(lLabel);
		}
		for(JLabel aImage: aCardImages)
		{
			aImage.setHorizontalAlignment(JLabel.CENTER);
			add(aImage);
		}
		revalidate();
		repaint();
	}
	
	public class CardClickListener extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if (GameUI.drawPile == null)
				return;
			for(JLabel aImage : aCardImages)
			{
				if (aImage == e.getSource())
				{
					Card dCard = aCards.get(aCardImages.indexOf(aImage));
					getPlayer().discard(dCard, GameUI.drawPile);
					JLabel aniTemp = new JLabel();
					aniTemp.setIcon(CardImages.getCard(dCard));
					aniTemp.setLocation(aImage.getLocation());
//					setComponentZOrder(aniTemp, 999999);
					add(aniTemp);
					Thread ani = new Thread(new CardSlideUp(aniTemp));
					ani.start();
					if (!(getPlayer() instanceof GreedyBot))
					{
						Timer t = new Timer(1000, new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								GameEngine.getInstance().getComputerPlayer().play();
							}
						});
						t.setRepeats(false);
						t.start();
					}
				}
				
			}
		}
		@Override
		public void mouseEntered(MouseEvent e)
		{
			for(JLabel aImage : aCardImages)
			{
				if (aImage == e.getSource())
				{
					aImage.setLocation(aImage.getLocation().x, aImage.getLocation().y-20);
					aImage.setToolTipText("Discard this card");
					repaint();
				}
				
			}
			
		}
		@Override
		public void mouseExited(MouseEvent e)
		{
			for(JLabel aImage : aCardImages)
			{
				if (aImage == e.getSource())
				{
					aImage.setLocation(aImage.getLocation().x, aImage.getLocation().y + 20);
				}
				
			}
			
		}
	}

	public void newEvent()
	{
		// TODO Auto-generated method stub
		initiateCards();
//		revalidate();
//		repaint();
	}

}
