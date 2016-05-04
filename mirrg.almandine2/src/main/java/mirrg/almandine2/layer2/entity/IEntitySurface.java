package mirrg.almandine2.layer2.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

import mirrg.almandine2.layer2.tool.IPoint;

public interface IEntitySurface extends IEntity, IPoint
{

	@Override
	default void renderAura(Graphics2D graphics, double width, double margin, Color color)
	{
		graphics.setColor(color);
		Stroke stroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke((float) width));
		{
			graphics.draw(getShape(margin));
		}
		graphics.setStroke(stroke);
	}

	public Shape getShape(double margin);

	@Override
	default double getDistanceSq(double x, double y)
	{
		return IPoint.super.getDistanceSq(x, y);
	}

}
