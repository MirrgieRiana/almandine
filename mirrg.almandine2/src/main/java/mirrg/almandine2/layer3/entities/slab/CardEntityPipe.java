package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;

public class CardEntityPipe<E extends EntityPipe<E, V>, V extends ViewEntityPipe<E, V>> extends CardEntityWire<E, V>
{

	@SuppressWarnings({
		"unchecked", "rawtypes"
	})
	public static CardEntityWire<?, ?> INSTANCE = new CardEntityPipe<>(
		(begin, end) -> Optional.of(new EntityPipe(begin, end)),
		e -> new ViewEntityPipe(e));

	public CardEntityPipe(BiFunction<Connection, Connection, Optional<E>> functionEntity, Function<E, V> functionView)
	{
		super(functionEntity, functionView);
	}

	@Override
	public Stream<TypeConnection> getConnectionTypesBegin()
	{
		return Stream.of(TypeConnection.block, TypeConnection.point);
	}

	@Override
	public boolean isConnectableBegin(Connection connection)
	{
		return connection instanceof ConnectionPoint
			|| (connection instanceof ConnectionBlock
				&& ((ConnectionBlock) connection).entity instanceof IBlockSlot);
	}

	@Override
	public Stream<TypeConnection> getConnectionTypesEnd()
	{
		return Stream.of(TypeConnection.block, TypeConnection.point);
	}

	@Override
	public boolean isConnectableEnd(Connection connection)
	{
		return connection instanceof ConnectionPoint
			|| (connection instanceof ConnectionBlock
				&& ((ConnectionBlock) connection).entity instanceof IBlockSlot);
	}

}
