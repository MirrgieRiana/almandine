package mirrg.almandine2.layer2.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.Event;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer2.entity.view.ViewSurface;
import mirrg.almandine2.layer2.entity.view.ViewSurfaceCircle;

public abstract class EntityBlock<E extends EntityBlock<E, V>, V extends ViewSurface> extends Entity<E, V>
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

	@SuppressWarnings("unchecked")
	@Override
	public CardEntityBlock<E, V> getCardEntity()
	{
		return (CardEntityBlock<E, V>) getCardEntityImpl();
	}

	public abstract CardEntity<?, ?> getCardEntityImpl();

	public boolean isConnectable(Connection connection)
	{
		if (connection.getEntities().anyMatch(e -> e == this)) return false;
		return getCardEntity().isConnectable(connection);
	}

	@Override
	public void onConnectionEvent(Entity<?, ?> owner, Event event)
	{
		super.onConnectionEvent(owner, event);

		dieIfNotConnectableToPoint(owner, event, connection, getCardEntity()::isConnectable, this::setConnection);

	}

	@Override
	public Stream<IHandle> getHandles()
	{
		return Stream.of(new IHandle() {

			@Override
			public Entity<?, ?> getOwner()
			{
				return EntityBlock.this;
			}

			@Override
			public View getView()
			{
				return new ViewSurfaceCircle() {

					@Override
					public Point2D.Double getPoint()
					{
						return EntityBlock.this.getView().getPoint();
					}

					@Override
					public double getRadius()
					{
						return 3;
					}

					@Override
					public void renderOverlay(Graphics2D graphics)
					{
						graphics.setColor(Color.white);
						graphics.fill(getShape(0));

						graphics.setColor(Color.red);
						graphics.draw(getShape(0));
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
