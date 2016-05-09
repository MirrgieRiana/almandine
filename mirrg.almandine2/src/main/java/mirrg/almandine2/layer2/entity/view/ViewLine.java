package mirrg.almandine2.layer2.entity.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class ViewLine extends View
{

	public abstract Point2D.Double getPointBegin();

	public abstract Point2D.Double getPointEnd();

	public abstract double getMarginBegin();

	public abstract double getMarginEnd();

	public abstract double getWidth();

	@Override
	public void renderAura(Graphics2D graphics, double width, double margin, Color color)
	{

		AffineTransform transform = graphics.getTransform();
		{
			Point2D.Double begin = getPointBegin();
			Point2D.Double end = getPointEnd();
			Point2D.Double center = new Point2D.Double((begin.x + end.x) / 2, (begin.y + end.y) / 2);

			graphics.translate(center.x, center.y);
			graphics.rotate(getAngle());
			graphics.translate(-center.x, -center.y);

			Stroke stroke = graphics.getStroke();
			{
				graphics.setStroke(new BasicStroke((float) width));

				double width2 = getLength() + margin * 2;
				double height2 = getWidth() + margin * 2;
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
	public double getDistanceCenterSq(double x, double y)
	{
		Point2D.Double begin = getPointBegin();
		Point2D.Double end = getPointEnd();
		return Line2D.ptSegDistSq(begin.x, begin.y, end.x, end.y, x, y);
	}

	@Override
	public double getDistanceEdge(double x, double y)
	{
		return Math.sqrt(getDistanceCenterSq(x, y)) - getWidth();
	}

	public Line2D.Double getShape()
	{
		Point2D.Double[] points = getPointsMargined();
		return new Line2D.Double(points[0].x, points[0].y, points[1].x, points[1].y);
	}

	public Point2D.Double getPoint(double position)
	{
		Point2D.Double[] points = getPointsMargined();
		return new Point2D.Double(
			points[0].x + (points[1].x - points[0].x) * position,
			points[0].y + (points[1].y - points[0].y) * position);
	}

	public double getAngle()
	{
		Point2D.Double begin = getPointBegin();
		Point2D.Double end = getPointEnd();
		return Math.atan2(end.y - begin.y, end.x - begin.x);
	}

	public double getDistance(double x, double y)
	{
		Point2D.Double[] points = getPointsMargined();
		return Line2D.ptSegDist(points[0].x, points[0].y, points[1].x, points[1].y, x, y);
	}

	public double getDistanceSq(double x, double y)
	{
		Point2D.Double[] points = getPointsMargined();
		return Line2D.ptSegDistSq(points[0].x, points[0].y, points[1].x, points[1].y, x, y);
	}

	public double getLength()
	{
		Point2D.Double[] points = getPointsMargined();
		return Point2D.distance(points[0].x, points[0].y, points[1].x, points[1].y);
	}

	public double getPosition(double x, double y)
	{
		Point2D.Double[] points = getPointsMargined();
		double[] p = complexDiv(
			x - points[0].getX(),
			y - points[0].getY(),
			points[1].getX() - points[0].getX(),
			points[1].getY() - points[0].getY());

		return p[0];
	}

	private static double[] complexDiv(double r1, double i1, double r2, double i2)
	{
		double c = r2 * r2 + i2 * i2;
		return new double[] {
			(r1 * r2 + i1 * i2) / c,
			(i1 * r2 - r1 * i2) / c,
		};
	}

	public Point2D.Double[] getPointsMargined()
	{
		Point2D.Double begin = getPointBegin();
		Point2D.Double end = getPointEnd();
		double marginBegin = getMarginBegin();
		double marginEnd = getMarginBegin();

		double distance = Point2D.distance(begin.getX(), begin.getY(), end.getX(), end.getY());
		if (distance == 0) {
			return new Point2D.Double[] {
				begin,
				end,
			};
		}

		double x = end.getX() - begin.getX();
		double y = end.getY() - begin.getY();

		double rateBegin = marginBegin / distance;
		double rateEnd = 1 - marginEnd / distance;

		return new Point2D.Double[] {
			new Point2D.Double(
				begin.getX() + rateBegin * x,
				begin.getY() + rateBegin * y),
			new Point2D.Double(
				begin.getX() + rateEnd * x,
				begin.getY() + rateEnd * y),
		};
	}

	public void drawArrow(Graphics2D graphics, double lengthHead, double angleHead)
	{
		Point2D.Double[] points = getPointsMargined();

		graphics.draw(new Line2D.Double(points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY()));

		{
			double theta = Math.atan2(points[0].getY() - points[1].getY(), points[0].getX() - points[1].getX());
			graphics.draw(new Line2D.Double(points[1].getX(), points[1].getY(),
				points[1].getX() + lengthHead * Math.cos(theta + angleHead * Math.PI / 180),
				points[1].getY() + lengthHead * Math.sin(theta + angleHead * Math.PI / 180)));
			graphics.draw(new Line2D.Double(points[1].getX(), points[1].getY(),
				points[1].getX() + lengthHead * Math.cos(theta - angleHead * Math.PI / 180),
				points[1].getY() + lengthHead * Math.sin(theta - angleHead * Math.PI / 180)));
		}
	}

}
