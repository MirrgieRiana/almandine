package mirrg.almandine2.layer2.entity;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public abstract class CardEntityWire<E extends EntityWire> extends CardEntity<E>
{

	private BiFunction<Connection, Connection, Optional<E>> supplierEntity;

	public CardEntityWire(BiFunction<Connection, Connection, Optional<E>> supplierEntity, Supplier<View<E>> supplierView)
	{
		super(supplierView);
		this.supplierEntity = supplierEntity;
	}

	public abstract Stream<TypeConnection> getConnectionTypesBegin();

	public abstract boolean isConnectableBegin(Connection connection);

	public abstract Stream<TypeConnection> getConnectionTypesEnd();

	public abstract boolean isConnectableEnd(Connection connection);

	protected boolean isDuplicated(Connection begin, Connection end)
	{
		return begin.getEntities().anyMatch(e -> end.getEntities().anyMatch(e2 -> e == e2));
	}

	public Optional<E> create(Connection begin, Connection end)
	{
		return isDuplicated(begin, end) ? Optional.empty() : supplierEntity.apply(begin, end);
	}

}
