package mirrg.almandine2.layer2.entity;

import java.util.stream.Stream;

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

	public abstract E create(Connection begin, Connection end);

}
