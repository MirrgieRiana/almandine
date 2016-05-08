package mirrg.almandine2.layer3.entities.redstone.station;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer3.entities.redstone.IBlockRedstone;
import mirrg.almandine2.layer3.entities.station.IRail;

public class CardEntityWireRedstoneRail<E extends Entity, V extends View<E>> extends CardEntityWire<E, V>
{

	public static final CardEntityWireRedstoneRail<EntityWireRedstoneRail, ViewEntityWireRedstoneRail<EntityWireRedstoneRail>> INSTANCE = new CardEntityWireRedstoneRail<>(
		(begin, end) -> Optional.of(new EntityWireRedstoneRail(begin, end)),
		ViewEntityWireRedstoneRail::new);

	public CardEntityWireRedstoneRail(BiFunction<Connection, Connection, Optional<E>> supplierEntity, Supplier<V> supplierView)
	{
		super(supplierEntity, supplierView);
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
