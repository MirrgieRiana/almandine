package mirrg.almandine2.layer2.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.Event;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer2.entity.view.ViewSurfaceCircle;

public abstract class EntityBlock extends Entity
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

	public Point2D.Double getPoint()
	{
		return connection.getPoint();
	}

	@Override
	public abstract CardEntityBlock<?, ?> getCardEntity();

	public boolean isConnectable(Connection connection)
	{
		if (connection.getEntities().anyMatch(e -> e == this)) return false;
		return getCardEntity().isConnectable(connection);
	}

	@Override
	public void onConnectionEvent(Entity owner, Event event)
	{
		super.onConnectionEvent(owner, event);

		dieIfNotConnectableToPoint(owner, event, connection, getCardEntity()::isConnectable, this::setConnection);

	}

	@Override
	public Stream<IHandle> getHandles()
	{
		return Stream.of(new IHandle() {

			@Override
			public Entity getOwner()
			{
				return EntityBlock.this;
			}

			@Override
			public View<IHandle> getView()
			{
				return new ViewSurfaceCircle<IHandle>() {

					@Override
					public Point2D.Double getPoint(IHandle entity)
					{
						return EntityBlock.this.getConnection().getPoint();
					}

					@Override
					public double getRadius(IHandle entity)
					{
						return 3;
					}

					@Override
					public void render(IHandle entity, Graphics2D graphics)
					{
						graphics.setColor(Color.white);
						graphics.fill(getShape(entity, 0));

						graphics.setColor(Color.red);
						graphics.draw(getShape(entity, 0));
					}

				};
			}

			@Override
			public Stream<TypeConnection> getConnectionTypes()
			{
				return EntityBlock.this.getCardEntity().getConnectionTypes();
			}

			@Override
			public boolean isConnectable(Connection connection)
			{
				return EntityBlock.this.isConnectable(connection);
			}

			@Override
			public void set(Connection connection)
			{
				setConnection(connection);
			}

		});
	}

}
