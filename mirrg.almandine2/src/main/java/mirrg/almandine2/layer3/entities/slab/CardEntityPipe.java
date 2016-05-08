package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public class CardEntityPipe<E extends EntityPipe> extends CardEntityWire<EntityPipe>
{

	public static final CardEntityPipe<EntityPipe> INSTANCE = new CardEntityPipe<>(
		(begin, end) -> Optional.of(new EntityPipe(begin, end)),
		ViewEntityPipe::new);

	public CardEntityPipe(BiFunction<Connection, Connection, Optional<EntityPipe>> supplierEntity, Supplier<View<EntityPipe>> supplierView)
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
