package mirrg.almandine2.layer2.entity;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class ViewSurfaceRectangle<T> extends ViewSurface<T>
{

	public abstract Point2D.Double getPoint(T entity);

	public abstract double getWidth(T entity);

	public abstract double getHeight(T entity);

	@Override
	public Rectangle2D.Double getShape(T entity, double margin)
	{
		Point2D.Double point = getPoint(entity);
		double width = getWidth(entity);
		double height = getHeight(entity);
		return new Rectangle2D.Double(
			point.x - width / 2 - margin,
			point.y - height / 2 - margin,
			width + margin * 2,
			height + margin * 2);
	}

	@Override
	public double getDistanceCenterSq(T entity, double x, double y)
	{
		Point2D.Double point = getPoint(entity);
		double a = Math.max(
			Math.abs(point.x - x),
			Math.abs(point.y - y));
		return a * a;
	}

	@Override
	public double getDistanceEdge(T entity, double x, double y)
	{
		Point2D.Double point = getPoint(entity);
		double width = getWidth(entity);
		double height = getHeight(entity);
		return Math.max(
			Math.abs(point.x - x) - width / 2,
			Math.abs(point.y - y) - height / 2);
	}

}
