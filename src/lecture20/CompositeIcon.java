package lecture20;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.Icon;

public class CompositeIcon implements Icon
{
	private final ArrayList<Icon> aIcons = new ArrayList<Icon>();
	
	public void addIcon(Icon pIcon)
	{
		aIcons.add(pIcon);
	}
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		for( Icon i : aIcons )
		{
			i.paintIcon(c,  g,  x, y);
		}
	}

	@Override
	public int getIconWidth()
	{
		int max = 0;
		for( Icon i : aIcons )
		{
			if( i.getIconWidth() > max )
			{
				max = i.getIconWidth();
			}
		}
		return max;
	}

	@Override
	public int getIconHeight()
	{
		int max = 0;
		for( Icon i : aIcons )
		{
			if( i.getIconHeight() > max )
			{
				max = i.getIconHeight();
			}
		}
		return max;
	}
}
