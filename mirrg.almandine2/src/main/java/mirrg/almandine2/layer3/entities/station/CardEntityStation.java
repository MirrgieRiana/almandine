package mirrg.almandine2.layer3.entities.station;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;

public class CardEntityStation<E extends EntityStation<E, V>, V extends ViewEntityStation<E, V>> extends CardEntityBlock<E, V>
{

	@SuppressWarnings({
		"unchecked", "rawtypes"
	})
	public static CardEntityBlock<?, ?> INSTANCE = new CardEntityStation<>(
		c -> Optional.of(new EntityStation(c)),
		e -> new ViewEntityStation(e));

	public CardEntityStation(Function<Connection, Optional<E>> functionEntity, Function<E, V> functionView)
	{
		super(functionEntity, functionView);
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
