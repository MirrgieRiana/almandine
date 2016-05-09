package mirrg.almandine2.layer2.entity.view;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public abstract class ViewSurfaceCircle extends ViewSurface
{

	public abstract double getRadius();

	public double getRadius(double angle, double margin)
	{
		return getRadius() + margin;
	}

	@Override
	public Ellipse2D.Double getShape(double margin)
	{
		Point2D.Double point = getPoint();
		double radius = getRadius();
		return new Ellipse2D.Double(
			point.x - radius - margin,
			point.y - radius - margin,
			(radius + margin) * 2,
			(radius + margin) * 2);
	}

	@Override
	public double getDistanceCenterSq(double x, double y)
	{
		Point2D.Double point = getPoint();
		return Point2D.distanceSq(point.x, point.y, x, y);
	}

	@Override
	public double getDistanceEdge(double x, double y)
	{
		return Math.sqrt(getDistanceCenterSq(x, y)) - getRadius();
	}

}
