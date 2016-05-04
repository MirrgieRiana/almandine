package mirrg.almandine2.layer2.entity;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface IEntityRectangle extends IEntitySurface
{

	public Rectangle2D.Double getShape();

	@Override
	public default double getX()
	{
		return getShape().getCenterX();
	}

	@Override
	public default double getY()
	{
		return getShape().getCenterY();
	}

	@Override
	public default Rectangle2D.Double getShape(double margin)
	{
		Rectangle2D.Double rectangle = getShape();
		return new Rectangle2D.Double(
			rectangle.x - margin,
			rectangle.y - margin,
			rectangle.width + margin * 2,
			rectangle.getHeight() + margin * 2);
	}

	@Override
	public default boolean contains(double x, double y)
	{
		return getShape().contains(x, y);
	}

	@Override
	public default double getDistanceSq(double x, double y)
	{
		Rectangle2D.Double shape = getShape();
		return Point2D.distanceSq(shape.getCenterX(), shape.getCenterY(), x, y);
	}

}
