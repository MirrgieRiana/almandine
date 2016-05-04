package mirrg.almandine2.layer2.tool;

import java.awt.geom.Point2D;

public class Point implements IPoint
{

	public double x;
	public double y;

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Point(Point2D.Double point)
	{
		this(point.x, point.y);
	}

	public Point2D.Double toAWT()
	{
		return new Point2D.Double(x, y);
	}

	@Override
	public double getX()
	{
		return x;
	}

	@Override
	public double getY()
	{
		return y;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) return false;
		return true;
	}

}
