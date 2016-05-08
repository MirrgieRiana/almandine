package mirrg.almandine2.layer2.entity;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public abstract class CardEntityBlock<E extends EntityBlock<E, V>, V extends View> extends CardEntity<E, V>
{

	private Function<Connection, Optional<E>> functionEntity;

	public CardEntityBlock(Function<Connection, Optional<E>> functionEntity, Function<E, V> functionView)
	{
		super(functionView);
		this.functionEntity = functionEntity;
	}

	public abstract Stream<TypeConnection> getConnectionTypes();

	public abstract boolean isConnectable(Connection connection);

	public Optional<E> create(Connection connection)
	{
		return functionEntity.apply(connection);
	}

}
