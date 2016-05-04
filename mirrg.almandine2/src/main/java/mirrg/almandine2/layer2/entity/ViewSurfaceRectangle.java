package mirrg.almandine2.layer2.entity;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class ViewSurfaceRectangle<E extends EntityBlock> extends ViewSurface<E>
{

	public abstract double getWidth(E entity);

	public abstract double getHeight(E entity);

	@Override
	public Rectangle2D.Double getShape(E entity, double margin)
	{
		Point2D.Double point = entity.getPoint();
		double width = getWidth(entity);
		double height = getHeight(entity);
		return new Rectangle2D.Double(
			point.x - width / 2 - margin,
			point.y - height / 2 - margin,
			width + margin * 2,
			height + margin * 2);
	}

	@Override
	public double getDistanceCenterSq(E entity, double x, double y)
	{
		Point2D.Double point = entity.getPoint();
		double a = Math.max(
			Math.abs(point.x - x),
			Math.abs(point.y - y));
		return a * a;
	}

	@Override
	public double getDistanceEdge(E entity, double x, double y)
	{
		Point2D.Double point = entity.getPoint();
		double width = getWidth(entity);
		double height = getHeight(entity);
		return Math.max(
			Math.abs(point.x - x) - width / 2,
			Math.abs(point.y - y) - height / 2);
	}

}
