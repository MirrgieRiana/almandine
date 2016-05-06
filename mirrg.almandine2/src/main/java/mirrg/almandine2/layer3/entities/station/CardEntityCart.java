package mirrg.almandine2.layer3.entities.station;

import java.util.Optional;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionAnchor;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

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
		return new ViewEntityCart();
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
