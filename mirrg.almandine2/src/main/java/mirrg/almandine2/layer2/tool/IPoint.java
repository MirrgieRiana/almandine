package mirrg.almandine2.layer2.tool;

import java.awt.geom.Point2D;

public interface IPoint
{

	public double getX();

	public double getY();

	public default Point copyAsPoint()
	{
		return new Point(getX(), getY());
	}

	public default double getDistanceSq(double x, double y)
	{
		return Point2D.distanceSq(getX(), getY(), x, y);
	}

}
