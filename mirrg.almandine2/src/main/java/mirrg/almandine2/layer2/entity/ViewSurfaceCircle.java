package mirrg.almandine2.layer2.entity;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public abstract class ViewSurfaceCircle<T> extends ViewSurface<T>
{

	public abstract Point2D.Double getPoint(T entity);

	public abstract double getRadius(T entity);

	@Override
	public Ellipse2D.Double getShape(T entity, double margin)
	{
		Point2D.Double point = getPoint(entity);
		double radius = getRadius(entity);
		return new Ellipse2D.Double(
			point.x - radius - margin,
			point.y - radius - margin,
			(radius + margin) * 2,
			(radius + margin) * 2);
	}

	@Override
	public double getDistanceCenterSq(T entity, double x, double y)
	{
		Point2D.Double point = getPoint(entity);
		return Point2D.distanceSq(point.x, point.y, x, y);
	}

	@Override
	public double getDistanceEdge(T entity, double x, double y)
	{
		return Math.sqrt(getDistanceCenterSq(entity, x, y)) - getRadius(entity);
	}

}
