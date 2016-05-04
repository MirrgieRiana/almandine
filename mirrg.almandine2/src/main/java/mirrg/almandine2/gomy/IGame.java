package mirrg.almandine2.gomy;

import java.awt.Graphics2D;

import javax.swing.JApplet;

public interface IGame
{

	public int getWaitMove();

	public int getWaitPaint();

	public void init(Graphics2D graphics, JApplet applet);

	public void move();

	public void paint();

}
