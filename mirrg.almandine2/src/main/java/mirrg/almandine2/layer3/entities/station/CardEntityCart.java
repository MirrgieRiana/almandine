package mirrg.almandine2.layer3.entities.station;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionAnchor;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public class CardEntityCart<E extends Entity, V extends View<E>> extends CardEntityBlock<E, V>
{

	public static final CardEntityCart<EntityCart, ViewEntityCart<EntityCart>> INSTANCE = new CardEntityCart<>(
		c -> Optional.of(new EntityCart(c)),
		ViewEntityCart::new);

	public CardEntityCart(Function<Connection, Optional<E>> supplierEntity, Supplier<V> supplierView)
	{
		super(supplierEntity, supplierView);
	}

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
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.traffic, TypeConnection.anchor, TypeConnection.point);
	}

}
