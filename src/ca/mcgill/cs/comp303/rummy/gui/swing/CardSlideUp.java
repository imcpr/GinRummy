package ca.mcgill.cs.comp303.rummy.gui.swing;

import javax.swing.JLabel;

public class CardSlideUp implements Runnable
{
	private JLabel aImage;
	CardSlideUp(JLabel pImage)
	{
		aImage = pImage;
	}
	@Override
	public void run()
	{
		try
		{
			for(int i = 0; i < 1000; i++)
			{
				aImage.setLocation(aImage.getLocation().x+10, aImage.getLocation().y);
				Thread.sleep(10);
			}
		}
		catch (InterruptedException e)
		{
			return;
		}
		
		
	}

}
