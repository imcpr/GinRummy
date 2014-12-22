package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Deck;
import ca.mcgill.cs.comp303.rummy.model.GameEngine;
import ca.mcgill.cs.comp303.rummy.model.Observer;

/**
 * GUI application to demonstrate the card utilities at work.
 * You do not need to understand how this code works: it will be explained 
 * later in the course.
 */
@SuppressWarnings("serial")
public class GameUI extends JFrame implements Observer
{
	private static final int MARGIN = 10;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
	private Deck aDeck = new Deck();
	private JLabel aDiscard = new JLabel();
	private JLabel aPile = new JLabel();
	private JLabel aText = new JLabel();
	private JPanel aCenter = new JPanel(new BorderLayout());
	private JButton aButton = new JButton("Next");
	private JLabel tmp = new JLabel();
	public static Boolean drawPile = null;

	private CardHolder plCardHolder;
	private CardHolder cpuCardHolder;
	
	/**
	 * Demo.
	 */
	public GameUI()
	{
		super("Card Demo");
		GameEngine.getInstance().addObserver(this);
		GameEngine.getInstance().newGame();
		aDeck.shuffle();
		aDiscard.setIcon(CardImages.getJoker());
		aPile.setIcon(CardImages.getBack());
//		CardClickListener ccl = new CardClickListener();
//		aDiscard.addMouseListener(ccl);
//		aPile.addMouseListener(ccl);
		aDiscard.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (aDiscard.getIcon() == CardImages.getJoker())
					return;
				tmp = new JLabel();
				tmp.setIcon(aDiscard.getIcon());
				tmp.setHorizontalAlignment(JLabel.CENTER);
				aText.setText("Choose a card from your pile to discard");
				aCenter.add(tmp, BorderLayout.SOUTH);
				aDiscard.setIcon(CardImages.getBack());
//				tmp.setLocation(aDiscard.getLocation().x, aDiscard.getLocation().y+200);
				drawPile = false;
				revalidate();
				repaint();
			}
		});
		aPile.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e)
			{
				tmp = new JLabel();
				tmp.setIcon(CardImages.getCard(GameEngine.getInstance().peek()));
				tmp.setHorizontalAlignment(JLabel.CENTER);
				aText.setText("Choose a card from your pile to discard");
				aCenter.add(tmp, BorderLayout.SOUTH);
//				tmp.setLocation(aDiscard.getLocation().x, aDiscard.getLocation().y+200);
				drawPile = true;
				revalidate();
				repaint();
			}
		});
		aCenter.add(aDiscard, BorderLayout.WEST);
		aText.setText("Pick a card to take from");
		aText.setHorizontalAlignment(JLabel.CENTER);
		aCenter.add(aText, BorderLayout.CENTER);
		aCenter.add(aPile, BorderLayout.EAST);
		aCenter.setBackground(new Color(100,165,3));
		cpuCardHolder = new CardHolder(true);
		plCardHolder = new CardHolder();
		GameEngine.getInstance().addObserver(cpuCardHolder);
		GameEngine.getInstance().addObserver(plCardHolder);
		setLayout(new BorderLayout(MARGIN, MARGIN));
//		add(aText, BorderLayout.NORTH);
//		aCenter.setLayout(new GridBagLayout());
		add(aCenter, BorderLayout.CENTER);
		add(cpuCardHolder, BorderLayout.NORTH);
//		add(aImage, BorderLayout.CENTER);
//		add(aButton, BorderLayout.SOUTH);
		add(aCenter, BorderLayout.CENTER);
		add(plCardHolder, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	public void reInit()
	{
		aPile.setIcon(CardImages.getBack());
		aText.setText("Pick a card to take from");
		
	}
	
	public void newEvent()
	{
		drawPile = null;
		aDiscard.setIcon(CardImages.getCard(GameEngine.getInstance().getDiscardCard()));
		aCenter.remove(tmp);
		reInit();
		revalidate();
		repaint();
	}
	
	public Boolean getDrawPile()
	{
		return drawPile;
	}

	
	/**
	 * @param pArgs Nothing used
	 */
	public static void main(String[] pArgs)
	{
		new GameUI();
	}

//	public class CardClickListener extends MouseAdapter
//	{
//		@Override
//		public void mouseClicked(MouseEvent e)
//		{
//			if (aDiscard == e.getSource())
//			{
//				JLabel tmp = new JLabel();
//				tmp.setIcon(aDiscard.getIcon());
//				tmp.setLocation(aDiscard.getLocation().x, aDiscard.getLocation().y+200);
//				add(tmp);
//			}
//			else
//			{
//				aPile.setLocation(aPile.getLocation().x, aPile.getLocation().y+200);
//				aPile.setIcon(CardImages.getCard(GameEngine.getInstance().peek()));
//			}
////			aText.setText("Pick a card to discard");
////			aText.setToolTipText("HI");
//			revalidate();
//			repaint();
//		}
//	}
}
