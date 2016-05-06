package mirrg.almandine2.layer2.entity;

import java.util.Optional;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;

public abstract class CardEntityWire<E extends EntityWire> extends CardEntity<E>
{

	public abstract Stream<TypeConnection> getConnectionTypesBegin();

	public abstract boolean isConnectableBegin(Connection connection);

	public abstract Stream<TypeConnection> getConnectionTypesEnd();

	public abstract boolean isConnectableEnd(Connection connection);

	@Deprecated
	@Override
	public E create()
	{
		throw new UnsupportedOperationException();
	}

	protected boolean isDuplicated(Connection begin, Connection end)
	{
		return begin.getEntities().anyMatch(e -> end.getEntities().anyMatch(e2 -> e == e2));
	}

	public abstract Optional<E> create(Connection begin, Connection end);

}
