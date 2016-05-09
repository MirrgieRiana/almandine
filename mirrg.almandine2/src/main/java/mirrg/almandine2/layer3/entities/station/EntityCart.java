package mirrg.almandine2.layer3.entities.station;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import mirrg.almandine2.layer2.entity.CardEntity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionAnchor;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;

public class EntityCart<E extends EntityCart<E, V>, V extends ViewEntityCart<E, V>> extends EntityBlock<E, V> implements ICart
{

	public EntityCart(Connection connection)
	{
		super(connection);
	}

	@Override
	public void move()
	{
		if (getConnection() instanceof ConnectionTraffic) {
			ConnectionTraffic connection = (ConnectionTraffic) getConnection();

			if (!connection.reverse) {
				connection.position += 0.005;
				if (connection.position > 1) {

					if (connection.entity.getEnd() instanceof ConnectionBlock) {
						ConnectionBlock connection2 = (ConnectionBlock) connection.entity.getEnd();
						if (connection2.entity instanceof IStation) {
							setConnection(new ConnectionAnchor(connection2.entity, ((IStation) connection2.entity).getFreeOrder(), connection.entity));
						}
					} else {
						connection.reverse = true;
						connection.position = 1 - (connection.position - 1);
					}

				}
			} else {
				connection.position -= 0.005;
				if (connection.position < 0) {

					if (connection.entity.getBegin() instanceof ConnectionBlock) {
						ConnectionBlock connection2 = (ConnectionBlock) connection.entity.getBegin();
						if (connection2.entity instanceof IStation) {
							setConnection(new ConnectionAnchor(connection2.entity, ((IStation) connection2.entity).getFreeOrder(), connection.entity));
						}
					} else {
						connection.reverse = false;
						connection.position = -connection.position;
					}

				}
			}

		}
	}

	@Override
	public CardEntity<?, ?> getCardEntityImpl()
	{
		return CardEntityCart.INSTANCE;
	}

	@Override
	public EntityBlock<?, ?> getEntity()
	{
		return this;
	}

	@Override
	public Double getPoint()
	{
		if (getConnection() instanceof ConnectionTraffic) {
			ConnectionTraffic connection2 = (ConnectionTraffic) getConnection();

			Point2D.Double begin = connection2.entity.getBegin().getPoint();
			Point2D.Double end = connection2.entity.getEnd().getPoint();

			double x = end.getX() - begin.getX();
			double y = end.getY() - begin.getY();

			return new Point2D.Double(
				begin.getX() + connection2.position * x,
				begin.getY() + connection2.position * y);

		} else if (getConnection() instanceof ConnectionAnchor) {
			ConnectionAnchor connection2 = (ConnectionAnchor) getConnection();
			return new Point2D.Double(
				connection2.entity.getPoint().x,
				connection2.entity.getPoint().y - 35 - 25 * connection2.order);

		} else {
			return getConnection().getPoint();
		}
	}

}
