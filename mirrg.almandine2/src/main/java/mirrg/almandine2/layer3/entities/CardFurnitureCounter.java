package mirrg.almandine2.layer3.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import mirrg.almandine2.layer2.entity.IEntityRectangle;
import mirrg.almandine2.layer2.tool.IPoint;
import mirrg.almandine2.layer2.tool.Point;

public class CardFurnitureCounter implements ICardFurniture
{

	public static CardFurnitureCounter INSTANCE = new CardFurnitureCounter();

	@Override
	public IFurniture create(Point point)
	{
		return new Entity(point);
	}

	@Override
	public void render(Graphics2D graphics, Point point)
	{
		render(graphics, point, 1234);
	}

	public void render(Graphics2D graphics, IPoint point, int i)
	{
		graphics.setColor(Color.black);
		graphics.setFont(HRender.getFontDefault());
		HRender.drawString(graphics, "" + i, point.getX(), point.getY(), HRender.ALIGN_CENTER, HRender.ALIGN_MIDDLE);
	}

	@Override
	public boolean isSettable(Point point)
	{
		return true;
	}

	public static class Entity extends EntityFurnitureAbstract implements IEntityRectangle
	{

		private int i;

		public Entity(Point point)
		{
			super(point);
		}

		public void move()
		{
			i++;
		}

		@Override
		public void render(Graphics2D graphics)
		{
			INSTANCE.render(graphics, this, i);
		}

		@Override
		public boolean isSettable(Point point)
		{
			return INSTANCE.isSettable(point);
		}

		@Override
		public Rectangle2D.Double getShape()
		{
			return new Rectangle2D.Double(getX() - 50, getY() - 7, 100, 14);
		}

	}

}
