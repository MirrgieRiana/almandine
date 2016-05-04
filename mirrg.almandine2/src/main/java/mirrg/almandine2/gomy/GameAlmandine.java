package mirrg.almandine2.gomy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JApplet;

public class GameAlmandine implements IGame
{

	private Graphics2D graphics;
	private JApplet applet;

	private volatile double[][] value = new double[20][20];
	private volatile double[][] speed = new double[20][20];

	@Override
	public void init(Graphics2D graphics, JApplet applet)
	{
		this.graphics = graphics;
		this.applet = applet;

		for (int x = 0; x < value.length; x++) {
			for (int y = 0; y < value[x].length; y++) {
				speed[x][y] = Math.random() * 1000;
				value[x][y] = Math.random() * 5 + 1;
			}
		}

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	public void move()
	{
		for (int x = 0; x < speed.length; x++) {
			for (int y = 0; y < speed[x].length; y++) {
				speed[x][y] += value[x][y];
				while (speed[x][y] >= 1000) {
					speed[x][y] -= 1000;
				}
			}
		}
	}

	@Override
	public void paint()
	{
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, applet.getWidth(), applet.getHeight());

		graphics.setColor(Color.black);
		for (int x = 0; x < speed.length; x++) {
			for (int y = 0; y < speed[x].length; y++) {

				double xBase = applet.getWidth() * x / speed.length;
				double yBase = applet.getHeight() * y / speed[x].length;
				double xHalf = applet.getWidth() / speed.length / 2;
				double yHalf = applet.getHeight() / speed[x].length / 2;

				try {
					graphics.fill(new Rectangle.Double(xBase + xHalf - (speed[x][y] / 1000) * xHalf,
						yBase + yHalf - (speed[x][y] / 1000) * yHalf, 2 * (speed[x][y] / 1000) * xHalf,
						2 * (speed[x][y] / 1000) * yHalf));
				} catch (NullPointerException e) {

				}

			}
		}
	}

	@Override
	public int getWaitMove()
	{
		return 20;
	}

	@Override
	public int getWaitPaint()
	{
		return 20;
	}

}
