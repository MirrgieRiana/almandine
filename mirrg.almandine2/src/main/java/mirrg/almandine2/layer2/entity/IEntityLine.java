package mirrg.almandine2.layer2.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.tool.IPoint;

public interface IEntityLine extends IEntity
{

	public IPoint getBegin();

	public IPoint getEnd();

	@Override
	public default void renderAura(Graphics2D graphics, double width, double margin, Color color)
	{
		graphics.setColor(color);
		Stroke stroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke((float) (width + margin) * 2));
		{
			IPoint begin = getBegin();
			IPoint end = getEnd();

			graphics.draw(new Line2D.Double(begin.getX(), begin.getY(), end.getX(), end.getY()));
		}
		graphics.setStroke(stroke);
	}

	@Override
	public default boolean contains(double x, double y)
	{
		return getDistanceSq(x, y) < 3 * 3;
	}

	@Override
	public default double getDistanceSq(double x, double y)
	{
		IPoint begin = getBegin();
		IPoint end = getEnd();
		return Line2D.Double.ptSegDistSq(begin.getX(), begin.getY(), end.getX(), end.getY(), x, y);
	}

	public default double getLength()
	{
		IPoint begin = getBegin();
		IPoint end = getEnd();
		return Point2D.distance(begin.getX(), begin.getY(), end.getX(), end.getY());
	}

	public default double getAngle()
	{
		IPoint begin = getBegin();
		IPoint end = getEnd();
		return Math.atan2(end.getY() - begin.getY(), end.getX() - begin.getX());
	}

	public default double getPosition(double x, double y)
	{
		IPoint begin = getBegin();
		IPoint end = getEnd();

		double[] p = div(
			x - begin.getX(),
			y - begin.getY(),
			end.getX() - begin.getX(),
			end.getY() - begin.getY());

		return p[0];
	}

	public static double[] div(double r1, double i1, double r2, double i2)
	{
		double c = r2 * r2 + i2 * i2;
		return new double[] {
			(r1 * r2 + i1 * i2) / c,
			(i1 * r2 - r1 * i2) / c,
		};
	}

}
