package mirrg.almandine2.layer3.entities.station;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionAnchor;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;

public class CardEntityCart<E extends EntityCart<E, V>, V extends ViewEntityCart<E, V>> extends CardEntityBlock<E, V>
{

	@SuppressWarnings({
		"unchecked", "rawtypes"
	})
	public static CardEntityBlock<?, ?> INSTANCE = new CardEntityCart<>(
		c -> Optional.of(new EntityCart(c)),
		e -> new ViewEntityCart(e));

	public CardEntityCart(Function<Connection, Optional<E>> functionEntity, Function<E, V> functionView)
	{
		super(functionEntity, functionView);
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
