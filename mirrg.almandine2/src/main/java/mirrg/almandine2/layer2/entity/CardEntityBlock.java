package mirrg.almandine2.layer2.entity;

import java.util.Optional;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;

public abstract class CardEntityBlock<E extends EntityBlock> extends CardEntity<E>
{

	public abstract Stream<TypeConnection> getConnectionTypes();

	public abstract boolean isConnectable(Connection connection);

	@Deprecated
	@Override
	public E create()
	{
		throw new UnsupportedOperationException();
	}

	public abstract Optional<E> create(Connection connection);

}
