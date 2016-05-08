package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public class CardEntitySlot<E extends Entity, V extends View<E>> extends CardEntityBlock<E, V>
{

	public static final CardEntitySlot<EntitySlot, ViewEntitySlot<EntitySlot>> INSTANCE = new CardEntitySlot<>(
		c -> Optional.of(new EntitySlot(c, 10)),
		ViewEntitySlot::new);

	public CardEntitySlot(Function<Connection, Optional<E>> supplierEntity, Supplier<V> supplierView)
	{
		super(supplierEntity, supplierView);
	}

	@Override
	public boolean isConnectable(Connection connection)
	{
		return connection instanceof ConnectionPoint;
	}

	@Override
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.point);
	}

}
