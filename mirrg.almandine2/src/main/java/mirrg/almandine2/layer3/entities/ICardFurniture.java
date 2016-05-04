package mirrg.almandine2.layer3.entities;

import java.awt.Graphics2D;

import mirrg.almandine2.layer2.tool.Point;

public interface ICardFurniture
{

	public IFurniture create(Point point);

	public boolean isSettable(Point point);

	public void render(Graphics2D graphics, Point point);

}
