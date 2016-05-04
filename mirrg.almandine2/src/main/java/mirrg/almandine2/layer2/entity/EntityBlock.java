package mirrg.almandine2.layer2.entity;

import java.awt.geom.Point2D;
import java.util.stream.Stream;

public abstract class EntityBlock extends EntitySurface
{

	private Connection connection;

	public EntityBlock(Connection connection)
	{
		this.connection = connection;
	}

	@Override
	public Stream<Connection> getConnections()
	{
		return Stream.of(connection);
	}

	public Connection getConnection()
	{
		return connection;
	}

	public void setConnection(Connection connection)
	{
		this.connection.disable();
		this.connection = connection;
		this.connection.enable(this);
	}

	@Override
	public Point2D.Double getPoint()
	{
		return connection.getPoint();
	}

}
