package mirrg.almandine2.layer2.entity;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public abstract class CardEntityBlock<E extends EntityBlock> extends CardEntity<E>
{

	private Function<Connection, Optional<E>> supplierEntity;

	public CardEntityBlock(Function<Connection, Optional<E>> supplierEntity, Supplier<View<E>> supplierView)
	{
		super(supplierView);
		this.supplierEntity = supplierEntity;
	}

	public abstract Stream<TypeConnection> getConnectionTypes();

	public abstract boolean isConnectable(Connection connection);

	public Optional<E> create(Connection connection)
	{
		return supplierEntity.apply(connection);
	}

}
