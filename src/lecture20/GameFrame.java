package lecture20;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	public static Color BACKGROUND_COLOR = new Color(40, 160, 20);
//	private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("MessageBundle");
	
	private GameEngine aEngine = new GameEngine();
	
	/**
	 * This code should be modularized into separate builder methods.
	 */
	public GameFrame()
	{
		setTitle("Card Selector");
		setLayout( new BorderLayout() );
		JPanel lCenterPanel = new JPanel();
		lCenterPanel.setLayout(new GridLayout(2, 1));
		add(lCenterPanel, BorderLayout.CENTER);
		
		final CardPanel lCardPanel = new CardPanel();
		
		lCenterPanel.add( lCardPanel );
		aEngine.addObserver( lCardPanel );
		ShowPanel lSPanel = new ShowPanel();
		lCenterPanel.add(lSPanel);
		aEngine.addObserver(lSPanel);
		
		JPanel lButtonPanel = new JPanel();
		lButtonPanel.setLayout(new GridLayout(3,1));
		add(lButtonPanel, BorderLayout.SOUTH);
		
		JButton lButton = new JButton("discard");
		lButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Card lUp = lCardPanel.isUp();
				if( lUp != null )
				{
					aEngine.discard(lCardPanel.isUp());
				}
			}
		});
		lButtonPanel.add(lButton);
		
		// This code should be in a separate method. 
		JSlider lSlider = new JSlider();
		lSlider.setMinimum(0);
		lSlider.setMaximum(10);
		lSlider.setPaintTicks(true);
		lSlider.setMajorTickSpacing(1);
		lSlider.setSnapToTicks(true);
		lSlider.setValue(0);
		lSlider.setPaintLabels(true);
		lButtonPanel.add(lSlider);
		
		lButton = new JButton("deal");
		lButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				aEngine.deal();
			}
		});
		lButtonPanel.add(lButton);
		 
		aEngine.deal();
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocationRelativeTo(null);
		pack();
		setVisible( true );
	}
	
	public static void main(String[] args)
	{
		new GameFrame();
	}
}
