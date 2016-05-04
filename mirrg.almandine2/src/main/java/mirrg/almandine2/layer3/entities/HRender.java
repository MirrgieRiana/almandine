package mirrg.almandine2.layer3.entities;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

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

	public static Point2D.Double[] getPointsMargined(Point2D.Double begin, Point2D.Double end, double marginBegin, double marginEnd)
	{
		double distance = Point2D.distance(begin.getX(), begin.getY(), end.getX(), end.getY());
		if (distance == 0) {
			return new Point2D.Double[] {
				begin,
				end,
			};
		}

		double x = end.getX() - begin.getX();
		double y = end.getY() - begin.getY();

		double rateBegin = marginBegin / distance;
		double rateEnd = 1 - marginEnd / distance;

		return new Point2D.Double[] {
			new Point2D.Double(
				begin.getX() + rateBegin * x,
				begin.getY() + rateBegin * y),
			new Point2D.Double(
				begin.getX() + rateEnd * x,
				begin.getY() + rateEnd * y),
		};
	}

	public static void drawArrow(Graphics2D graphics, Point2D.Double begin, Point2D.Double end, double lengthHead, double angleHead)
	{
		graphics.draw(new Line2D.Double(begin.getX(), begin.getY(), end.getX(), end.getY()));

		{
			double theta = Math.atan2(begin.getY() - end.getY(), begin.getX() - end.getX());
			graphics.draw(new Line2D.Double(end.getX(), end.getY(),
				end.getX() + lengthHead * Math.cos(theta + angleHead * Math.PI / 180),
				end.getY() + lengthHead * Math.sin(theta + angleHead * Math.PI / 180)));
			graphics.draw(new Line2D.Double(end.getX(), end.getY(),
				end.getX() + lengthHead * Math.cos(theta - angleHead * Math.PI / 180),
				end.getY() + lengthHead * Math.sin(theta - angleHead * Math.PI / 180)));
		}
	}

}
