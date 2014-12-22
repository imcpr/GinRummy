package lecture20;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class ShiftedIcon implements Icon
{
	private final Icon aDecorated;
	private int aX = 0;
	private int aY = 0;
	
	public ShiftedIcon(Icon pIcon, int pX, int pY)
	{
		aDecorated = pIcon;
		aX = pX;
		aY = pY;
	}
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		aDecorated.paintIcon(c, g, x+aX, y+aY);		
	}

	@Override
	public int getIconWidth()
	{
		return aDecorated.getIconWidth() + aX;
	}

	@Override
	public int getIconHeight()
	{
		return aDecorated.getIconHeight() + aY;
	}

}
