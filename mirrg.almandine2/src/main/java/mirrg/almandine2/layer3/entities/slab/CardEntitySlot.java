package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public class CardEntitySlot extends CardEntityBlock<EntitySlot>
{

	public static final CardEntitySlot INSTANCE = new CardEntitySlot();

	@Override
	public boolean isConnectable(Connection connection)
	{
		return connection instanceof ConnectionPoint;
	}

	@Override
	public View<EntitySlot> getView()
	{
		return new ViewEntitySlot();
	}

	@Override
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.point);
	}

	@Override
	public Optional<EntitySlot> create(Connection connection)
	{
		return Optional.of(new EntitySlot(connection, 10));
	}

}
