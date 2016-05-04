package mirrg.almandine2.layer2.entity;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class ConnectionPoint extends Connection
{

	public Point2D.Double point;

	public ConnectionPoint(Double point)
	{
		this.point = point;
	}

	@Override
	public Point2D.Double getPoint()
	{
		return point;
	}

}
