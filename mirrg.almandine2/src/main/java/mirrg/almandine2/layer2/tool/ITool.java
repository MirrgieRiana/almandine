package mirrg.almandine2.layer2.tool;

import java.awt.Graphics2D;

import mirrg.almandine2.layer2.core.GameAlmandine2;

public interface ITool
{

	public void init(GameAlmandine2 game);

	public void dispose();

	public void render(Graphics2D graphics);

}
