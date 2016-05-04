package mirrg.almandine2.layer2.entity;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.stream.Stream;

public abstract class EntityWire extends Entity
{

	private Connection begin;
	private Connection end;

	public EntityWire(Connection begin, Connection end)
	{
		this.begin = begin;
		this.end = end;
	}

	@Override
	public Stream<Connection> getConnections()
	{
		return Stream.of(begin, end);
	}

	public Connection getBegin()
	{
		return begin;
	}

	public void setBegin(Connection connection)
	{
		this.begin.disable();
		this.begin = connection;
		this.begin.enable(this);
	}

	public Connection getEnd()
	{
		return end;
	}

	public void setEnd(Connection connection)
	{
		this.end.disable();
		this.end = connection;
		this.end.enable(this);
	}

	public Point2D.Double getPoint(double position)
	{
		Point2D.Double begin = getBegin().getPoint();
		Point2D.Double end = getEnd().getPoint();
		return new Point2D.Double(
			begin.x + (end.x - begin.x) * position,
			begin.y + (end.y - begin.y) * position);
	}

	public double getAngle()
	{
		Point2D.Double begin = getBegin().getPoint();
		Point2D.Double end = getEnd().getPoint();
		return Math.atan2(end.x - begin.x, end.y - begin.y);
	}

	public double getDistance(double x, double y)
	{
		Point2D.Double begin = getBegin().getPoint();
		Point2D.Double end = getEnd().getPoint();
		return Line2D.ptSegDist(begin.x, begin.y, end.x, end.y, x, y);
	}

	public double getDistanceSq(double x, double y)
	{
		Point2D.Double begin = getBegin().getPoint();
		Point2D.Double end = getEnd().getPoint();
		return Line2D.ptSegDistSq(begin.x, begin.y, end.x, end.y, x, y);
	}

	public double getLength()
	{
		Point2D.Double begin = getBegin().getPoint();
		Point2D.Double end = getEnd().getPoint();
		return Point2D.distance(begin.x, begin.y, end.x, end.y);
	}

	public double getPosition(double x, double y)
	{
		Point2D.Double begin = getBegin().getPoint();
		Point2D.Double end = getEnd().getPoint();

		double[] p = complexDiv(
			x - begin.getX(),
			y - begin.getY(),
			end.getX() - begin.getX(),
			end.getY() - begin.getY());

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

}
