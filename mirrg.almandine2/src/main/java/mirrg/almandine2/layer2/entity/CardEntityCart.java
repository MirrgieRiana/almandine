package mirrg.almandine2.layer2.entity;

import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;

public abstract class CardEntityCart<E extends EntityCart> extends CardEntity<E>
{

	public abstract Stream<TypeConnection> getConnectionTypes();

	public abstract boolean isConnectable(Connection connection);

	@Deprecated
	@Override
	public E create()
	{
		throw new UnsupportedOperationException();
	}

	public abstract E create(Connection connection);

}
