package mirrg.almandine2.layer2.entity;

import java.awt.geom.Point2D;
import java.util.stream.Stream;

public abstract class EntityCart extends EntitySurface
{

	private Connection connection;

	public EntityCart(Connection connection)
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

	@Override
	public abstract CardEntityCart<?> getCardEntity();

	@Override
	public void onConnectionEvent(Entity owner, Event event)
	{
		super.onConnectionEvent(owner, event);

		if (event instanceof EventDied) {
			if (connection instanceof ConnectionBlock) {
				if (((ConnectionBlock) connection).entity == owner) {

					Connection connection2 = new ConnectionPoint(connection.getPoint());
					if (getCardEntity().isConnectable(connection2)) {
						setConnection(connection2);
					} else {
						markDie();
					}

				}
			}
		}

	}

}
