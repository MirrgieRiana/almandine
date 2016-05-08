package mirrg.almandine2.layer2.entity.connection;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.Entity;

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

	@Override
	public Stream<Entity<?, ?>> getEntities()
	{
		return Stream.empty();
	}

}
