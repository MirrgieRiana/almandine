package mirrg.almandine2.layer2.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.Event;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer2.entity.view.ViewSurfaceCircle;

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

	public static Point2D.Double getPoint(Point2D.Double begin, Point2D.Double end, double position)
	{
		return new Point2D.Double(
			begin.x + (end.x - begin.x) * position,
			begin.y + (end.y - begin.y) * position);
	}

	public Point2D.Double getPoint(double position)
	{
		return getPoint(getBegin().getPoint(), getEnd().getPoint(), position);
	}

	public static double getAngle(Point2D.Double begin, Point2D.Double end)
	{
		return Math.atan2(end.y - begin.y, end.x - begin.x);
	}

	public double getAngle()
	{
		return getAngle(getBegin().getPoint(), getEnd().getPoint());
	}

	public static double getDistance(Point2D.Double begin, Point2D.Double end, double x, double y)
	{
		return Line2D.ptSegDist(begin.x, begin.y, end.x, end.y, x, y);
	}

	public double getDistance(double x, double y)
	{
		return getDistance(getBegin().getPoint(), getEnd().getPoint(), x, y);
	}

	public static double getDistanceSq(Point2D.Double begin, Point2D.Double end, double x, double y)
	{
		return Line2D.ptSegDistSq(begin.x, begin.y, end.x, end.y, x, y);
	}

	public double getDistanceSq(double x, double y)
	{
		return getDistanceSq(getBegin().getPoint(), getEnd().getPoint(), x, y);
	}

	public static double getLength(Point2D.Double begin, Point2D.Double end)
	{
		return Point2D.distance(begin.x, begin.y, end.x, end.y);
	}

	public double getLength()
	{
		return getLength(getBegin().getPoint(), getEnd().getPoint());
	}

	public static double getPosition(Point2D.Double begin, Point2D.Double end, double x, double y)
	{
		double[] p = complexDiv(
			x - begin.getX(),
			y - begin.getY(),
			end.getX() - begin.getX(),
			end.getY() - begin.getY());

		return p[0];
	}

	public double getPosition(double x, double y)
	{
		return getPosition(getBegin().getPoint(), getEnd().getPoint(), x, y);
	}

	private static double[] complexDiv(double r1, double i1, double r2, double i2)
	{
		double c = r2 * r2 + i2 * i2;
		return new double[] {
			(r1 * r2 + i1 * i2) / c,
			(i1 * r2 - r1 * i2) / c,
		};
	}

	@Override
	public abstract CardEntityWire<?, ?> getCardEntity();

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
	public void onConnectionEvent(Entity owner, Event event)
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
			public Entity getOwner()
			{
				return EntityWire.this;
			}

			@Override
			public View<IHandle> getView()
			{
				return new ViewSurfaceCircle<IHandle>() {

					@Override
					public Point2D.Double getPoint(IHandle entity)
					{
						Point2D.Double point = EntityWire.this.getBegin().getPoint();
						return new Point2D.Double(
							point.x + 8 * Math.cos(EntityWire.this.getAngle()),
							point.y + 8 * Math.sin(EntityWire.this.getAngle()));
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
			public Entity getOwner()
			{
				return EntityWire.this;
			}

			@Override
			public View<IHandle> getView()
			{
				return new ViewSurfaceCircle<IHandle>() {

					@Override
					public Point2D.Double getPoint(IHandle entity)
					{
						Point2D.Double point = EntityWire.this.getEnd().getPoint();
						return new Point2D.Double(
							point.x - 8 * Math.cos(EntityWire.this.getAngle()),
							point.y - 8 * Math.sin(EntityWire.this.getAngle()));
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
