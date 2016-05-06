package mirrg.almandine2.layer3.entities.station;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Optional;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionAnchor;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer2.entity.view.ViewSurfaceCircle;

public class CardEntityCart extends CardEntityBlock<EntityCart>
{

	public static final CardEntityCart INSTANCE = new CardEntityCart();

	@Override
	public boolean isConnectable(Connection connection)
	{
		return connection instanceof ConnectionPoint
			|| (connection instanceof ConnectionAnchor
				&& ((ConnectionAnchor) connection).entity instanceof IStation)
			|| (connection instanceof ConnectionTraffic
				&& ((ConnectionTraffic) connection).entity instanceof IRail);
	}

	@Override
	public View<EntityCart> getView()
	{
		return new ViewSurfaceCircle<EntityCart>() {

			@Override
			public Point2D.Double getPoint(EntityCart entity)
			{
				return entity.getPoint();
			}

			@Override
			public double getRadius(EntityCart entity)
			{
				return 10;
			}

			@Override
			public void render(EntityCart entity, Graphics2D graphics)
			{
				graphics.setColor(Color.blue);
				Stroke stroke = graphics.getStroke();
				graphics.setStroke(new BasicStroke(3));
				{
					Point2D.Double center = getCenter(entity.getConnection());
					graphics.draw(new Ellipse2D.Double(center.getX() - 10, center.getY() - 10, 20, 20));

					double angle = getAngle(entity.getConnection());

					graphics.draw(new Line2D.Double(
						center.getX() + (double) 10 * Math.cos(angle),
						center.getY() + (double) 10 * Math.sin(angle),
						center.getX() + (double) 10 * Math.cos(angle + 150 * Math.PI / 180),
						center.getY() + (double) 10 * Math.sin(angle + 150 * Math.PI / 180)));
					graphics.draw(new Line2D.Double(
						center.getX() + (double) 10 * Math.cos(angle),
						center.getY() + (double) 10 * Math.sin(angle),
						center.getX() + (double) 10 * Math.cos(angle - 150 * Math.PI / 180),
						center.getY() + (double) 10 * Math.sin(angle - 150 * Math.PI / 180)));
				}
				graphics.setStroke(stroke);
			}

			private Point2D.Double getCenter(Connection connection)
			{
				if (connection instanceof ConnectionTraffic) {
					ConnectionTraffic connection2 = (ConnectionTraffic) connection;

					Point2D.Double begin = connection2.entity.getBegin().getPoint();
					Point2D.Double end = connection2.entity.getEnd().getPoint();

					double x = end.getX() - begin.getX();
					double y = end.getY() - begin.getY();

					return new Point2D.Double(
						begin.getX() + connection2.position * x,
						begin.getY() + connection2.position * y);

				} else if (connection instanceof ConnectionAnchor) {
					ConnectionAnchor connection2 = (ConnectionAnchor) connection;
					return new Point2D.Double(
						connection2.entity.getPoint().x,
						connection2.entity.getPoint().y - 50 - 30 * 0);

				} else {
					return connection.getPoint();
				}
			}

			private double getAngle(Connection connection)
			{
				if (connection instanceof ConnectionTraffic) {
					ConnectionTraffic connection2 = (ConnectionTraffic) connection;
					double angle = connection2.entity.getAngle();
					if (connection2.reverse) angle += 180 * Math.PI / 180;
					return angle;
				} else if (connection instanceof ConnectionAnchor) {
					return 0;
				} else {
					return 0;
				}
			}

		};
	}

	@Override
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.traffic, TypeConnection.anchor, TypeConnection.point);
	}

	@Override
	public Optional<EntityCart> create(Connection connection)
	{
		return Optional.of(new EntityCart(connection));
	}

}
