package mirrg.almandine2.layer3.entities.redstone;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;

public class CardEntityWireRedstone<E extends EntityWireRedstone, V extends ViewEntityWireRedstone<E>> extends CardEntityWire<E, V>
{

	public static final CardEntityWireRedstone<EntityWireRedstone, ViewEntityWireRedstone<EntityWireRedstone>> INSTANCE = new CardEntityWireRedstone<>(
		(begin, end) -> Optional.of(new EntityWireRedstone(begin, end)),
		ViewEntityWireRedstone::new);

	public CardEntityWireRedstone(BiFunction<Connection, Connection, Optional<E>> supplierEntity, Supplier<V> supplierView)
	{
		super(supplierEntity, supplierView);
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
				&& ((ConnectionBlock) connection).entity instanceof IBlockRedstone);
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
				&& ((ConnectionBlock) connection).entity instanceof IBlockRedstone);
	}

}
