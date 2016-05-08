package mirrg.almandine2.layer3.entities.station;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public class CardEntityStation<E extends EntityStation> extends CardEntityBlock<E>
{

	public static final CardEntityStation<EntityStation> INSTANCE = new CardEntityStation<>(c -> Optional.of(new EntityStation(c)), ViewEntityStation::new);

	public CardEntityStation(Function<Connection, Optional<E>> supplierEntity, Supplier<View<E>> supplierView)
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
