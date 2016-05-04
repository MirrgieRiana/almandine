package mirrg.almandine2.layer2.entity;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public interface IEntityCircle extends IEntitySurface
{

	public double getRadius();

	@Override
	public default Ellipse2D.Double getShape(double margin)
	{
		double radius = getRadius();
		return new Ellipse2D.Double(
			getX() - radius - margin,
			getY() - radius - margin,
			(radius + margin) * 2,
			(radius + margin) * 2);
	}

	@Override
	public default boolean contains(double x, double y)
	{
		double radius = getRadius();
		return getDistanceSq(x, y) < radius * radius;
	}

	@Override
	public default double getDistanceSq(double x, double y)
	{
		return Point2D.distanceSq(getX(), getY(), x, y);
	}

}
