package mirrg.almandine2.layer3.entities;

import java.awt.Font;
import java.awt.Graphics2D;

public class HRender
{

	public static Font getFontDefault()
	{
		return new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	}

	public static int ALIGN_LEFT = 0;
	public static int ALIGN_CENTER = 1;
	public static int ALIGN_RIGHT = 2;
	public static int ALIGN_TOP = 0;
	public static int ALIGN_MIDDLE = 1;
	public static int ALIGN_BOTTOM = 2;

	public static void drawString(Graphics2D graphics, String text, double x, double y, int alignX, int alignY)
	{
		int width = graphics.getFontMetrics().stringWidth(text);
		int height = graphics.getFont().getSize();
		graphics.drawString(text, (int) (x - width * alignX / 2), (int) (y + height * (2 - alignY) / 2));
	}

}
