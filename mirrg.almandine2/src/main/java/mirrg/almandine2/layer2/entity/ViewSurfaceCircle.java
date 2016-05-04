package mirrg.almandine2.layer2.entity;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public abstract class ViewSurfaceCircle<E extends EntitySurface> extends ViewSurface<E>
{

	public abstract double getRadius(E entity);

	@Override
	public Ellipse2D.Double getShape(E entity, double margin)
	{
		Point2D.Double point = entity.getPoint();
		double radius = getRadius(entity);
		return new Ellipse2D.Double(
			point.x - radius - margin,
			point.y - radius - margin,
			(radius + margin) * 2,
			(radius + margin) * 2);
	}

	@Override
	public double getDistanceCenterSq(E entity, double x, double y)
	{
		Point2D.Double point = entity.getPoint();
		return Point2D.distanceSq(point.x, point.y, x, y);
	}

	@Override
	public double getDistanceEdge(E entity, double x, double y)
	{
		return Math.sqrt(getDistanceCenterSq(entity, x, y)) - getRadius(entity);
	}

}
