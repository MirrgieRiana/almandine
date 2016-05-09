package mirrg.almandine2.layer2.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.Event;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer2.entity.view.ViewLine;
import mirrg.almandine2.layer2.entity.view.ViewSurfaceCircle;

public abstract class EntityWire<E extends EntityWire<E, V>, V extends ViewLine> extends Entity<E, V>
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

	@SuppressWarnings("unchecked")
	@Override
	public CardEntityWire<E, V> getCardEntity()
	{
		return (CardEntityWire<E, V>) getCardEntityImpl();
	}

	public abstract CardEntity<?, ?> getCardEntityImpl();

	public boolean isConnectableBegin(Connection connection)
	{
		if (connection.getEntities().anyMatch(e -> e == this)) return false;
		if (connection.getEntities().anyMatch(e -> end.getEntities().anyMatch(e2 -> e == e2))) return false;
		return getCardEntity().isConnectableBegin(connection);
	}

	public boolean isConnectableEnd(Connection connection)
	{
		if (connection.getEntities().anyMatch(e -> e == this)) return false;
		if (connection.getEntities().anyMatch(e -> begin.getEntities().anyMatch(e2 -> e == e2))) return false;
		return getCardEntity().isConnectableEnd(connection);
	}

	@Override
	public void onConnectionEvent(Entity<?, ?> owner, Event event)
	{
		super.onConnectionEvent(owner, event);

		dieIfNotConnectableToPoint(owner, event, begin, getCardEntity()::isConnectableBegin, this::setBegin);
		dieIfNotConnectableToPoint(owner, event, end, getCardEntity()::isConnectableEnd, this::setEnd);

	}

	@Override
	public Stream<IHandle> getHandles()
	{
		return Stream.of(new IHandle() {

			@Override
			public Entity<?, ?> getOwner()
			{
				return EntityWire.this;
			}

			@Override
			public View getView()
			{
				return new ViewSurfaceCircle() {

					@Override
					public Point2D.Double getPoint()
					{
						return EntityWire.this.getView().getPointsMargined()[0];
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
				return EntityWire.this.getCardEntity().getConnectionTypesBegin();
			}

			@Override
			public boolean isConnectable(Connection connection)
			{
				return EntityWire.this.isConnectableBegin(connection);
			}

			@Override
			public void set(Connection connection)
			{
				setBegin(connection);
			}

		}, new IHandle() {

			@Override
			public Entity<?, ?> getOwner()
			{
				return EntityWire.this;
			}

			@Override
			public View getView()
			{
				return new ViewSurfaceCircle() {

					@Override
					public Point2D.Double getPoint()
					{
						return EntityWire.this.getView().getPointsMargined()[1];
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
				return EntityWire.this.getCardEntity().getConnectionTypesEnd();
			}

			@Override
			public boolean isConnectable(Connection connection)
			{
				return EntityWire.this.isConnectableEnd(connection);
			}

			@Override
			public void set(Connection connection)
			{
				setEnd(connection);
			}

		});
	}

}
