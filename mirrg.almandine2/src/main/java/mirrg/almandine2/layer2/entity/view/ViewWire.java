package mirrg.almandine2.layer2.entity.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import mirrg.almandine2.layer2.entity.EntityWire;

public abstract class ViewWire<T> extends View<T>
{

	public abstract Point2D.Double getPointBegin(T entity);

	public abstract Point2D.Double getPointEnd(T entity);

	public abstract double getWidth(T entity);

	@Override
	public void renderAura(T entity, Graphics2D graphics, double width, double margin, Color color)
	{

		AffineTransform transform = graphics.getTransform();
		{
			Point2D.Double begin = getPointBegin(entity);
			Point2D.Double end = getPointEnd(entity);
			Point2D.Double center = new Point2D.Double((begin.x + end.x) / 2, (begin.y + end.y) / 2);

			graphics.translate(center.x, center.y);
			graphics.rotate(EntityWire.getAngle(begin, end));
			graphics.translate(-center.x, -center.y);

			Stroke stroke = graphics.getStroke();
			{
				graphics.setStroke(new BasicStroke((float) width));

				double width2 = EntityWire.getLength(begin, end) + margin * 2;
				double height2 = getWidth(entity) + margin * 2;
				graphics.setColor(color);
				graphics.draw(new Rectangle2D.Double(
					center.x - width2 / 2,
					center.y - height2 / 2,
					width2,
					height2));
			}
			graphics.setStroke(stroke);

		}
		graphics.setTransform(transform);
	}

	@Override
	public double getDistanceCenterSq(T entity, double x, double y)
	{
		Point2D.Double begin = getPointBegin(entity);
		Point2D.Double end = getPointEnd(entity);
		return Line2D.ptSegDistSq(begin.x, begin.y, end.x, end.y, x, y);
	}

	@Override
	public double getDistanceEdge(T entity, double x, double y)
	{
		return Math.sqrt(getDistanceCenterSq(entity, x, y)) - getWidth(entity);
	}

}
