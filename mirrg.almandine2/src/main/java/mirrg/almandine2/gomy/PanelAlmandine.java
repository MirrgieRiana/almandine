package mirrg.almandine2.gomy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JApplet;

public class PanelAlmandine extends JApplet
{

	/**
	 *
	 */
	private static final long serialVersionUID = 8448530444862268953L;
	private BufferedImage image;
	private Graphics2D graphics;

	private Thread thread1;
	private Thread thread2;

	private IGame game;

	@Override
	public void start()
	{
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		graphics = image.createGraphics();

		thread1 = new Thread(() -> {

			while (true) {

				game.move();

				try {
					Thread.sleep(game.getWaitMove());
				} catch (InterruptedException e) {
					break;
				}

			}

		});
		thread2 = new Thread(() -> {

			while (true) {

				game.paint();
				repaint();

				try {
					Thread.sleep(game.getWaitPaint());
				} catch (InterruptedException e) {
					break;
				}

			}

		});

		game = new GameAlmandine();

		game.init(graphics, this);

		thread1.start();
		thread2.start();
	}

	@Override
	public void stop()
	{
		thread1.interrupt();
		thread2.interrupt();
	}

	@Override
	public void update(Graphics g)
	{
		paint(g);
	}

	@Override
	public void paint(Graphics g)
	{
		g.drawImage(image, 0, 0, this);
	}

}
