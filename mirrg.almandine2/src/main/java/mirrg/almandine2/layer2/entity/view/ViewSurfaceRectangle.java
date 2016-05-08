package mirrg.almandine2.layer2.entity.view;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class ViewSurfaceRectangle extends ViewSurface
{

	public abstract Point2D.Double getPoint();

	public abstract double getWidth();

	public abstract double getHeight();

	public double getRadius(double angle, double margin)
	{
		// w/2 = x1 * cos(angle)
		// h/2 = x2 * sin(angle)
		double x1 = Math.abs((getWidth() + margin) / (2 * Math.cos(angle)));
		double x2 = Math.abs((getHeight() + margin) / (2 * Math.sin(angle)));

		return Math.min(x1, x2);
	}

	@Override
	public Rectangle2D.Double getShape(double margin)
	{
		Point2D.Double point = getPoint();
		double width = getWidth();
		double height = getHeight();
		return new Rectangle2D.Double(
			point.x - width / 2 - margin,
			point.y - height / 2 - margin,
			width + margin * 2,
			height + margin * 2);
	}

	@Override
	public double getDistanceCenterSq(double x, double y)
	{
		Point2D.Double point = getPoint();
		double a = Math.max(
			Math.abs(point.x - x),
			Math.abs(point.y - y));
		return a * a;
	}

	@Override
	public double getDistanceEdge(double x, double y)
	{
		Point2D.Double point = getPoint();
		double width = getWidth();
		double height = getHeight();
		return Math.max(
			Math.abs(point.x - x) - width / 2,
			Math.abs(point.y - y) - height / 2);
	}

}
