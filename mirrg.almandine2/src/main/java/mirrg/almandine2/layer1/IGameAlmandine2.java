package mirrg.almandine2.layer1;

import java.awt.Graphics2D;

public interface IGameAlmandine2
{

	public void init(PanelAlmandine2 panel, int width, int height);

	public void resized(int width, int height);

	public void move();

	public void paint(Graphics2D graphics);

	public double getTickPerSecond();

	public double getFramePerSecond();

}
