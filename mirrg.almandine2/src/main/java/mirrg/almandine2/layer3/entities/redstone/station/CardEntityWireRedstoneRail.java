package mirrg.almandine2.layer3.entities.redstone.station;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer3.entities.redstone.IBlockRedstone;
import mirrg.almandine2.layer3.entities.station.IRail;

public class CardEntityWireRedstoneRail<E extends EntityWireRedstoneRail<E, V>, V extends ViewEntityWireRedstoneRail<E, V>> extends CardEntityWire<E, V>
{

	@SuppressWarnings({
		"unchecked", "rawtypes"
	})
	public static CardEntityWire<?, ?> INSTANCE = new CardEntityWireRedstoneRail<>(
		(begin, end) -> Optional.of(new EntityWireRedstoneRail(begin, end)),
		e -> new ViewEntityWireRedstoneRail(e));

	public CardEntityWireRedstoneRail(BiFunction<Connection, Connection, Optional<E>> functionEntity, Function<E, V> functionView)
	{
		super(functionEntity, functionView);
	}

	@Override
	public Stream<TypeConnection> getConnectionTypesBegin()
	{
		return Stream.of(TypeConnection.traffic, TypeConnection.point);
	}

	@Override
	public boolean isConnectableBegin(Connection connection)
	{
		return connection instanceof ConnectionPoint
			|| (connection instanceof ConnectionTraffic
				&& ((ConnectionTraffic) connection).entity instanceof IRail);
	}

	@Override
	public Stream<TypeConnection> getConnectionTypesEnd()
	{
		return Stream.of(TypeConnection.block, TypeConnection.traffic, TypeConnection.point);
	}

	@Override
	public boolean isConnectableEnd(Connection connection)
	{
		return connection instanceof ConnectionPoint
			|| (connection instanceof ConnectionBlock
				&& ((ConnectionBlock) connection).entity instanceof IBlockRedstone);
	}

}
