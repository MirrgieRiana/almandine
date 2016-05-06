package mirrg.almandine2.layer3.entities.counter;

import java.util.Optional;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public class CardEntityCounter extends CardEntityBlock<EntityCounter>
{

	public static final CardEntityCounter INSTANCE = new CardEntityCounter();

	@Override
	public boolean isConnectable(Connection connection)
	{
		return connection instanceof ConnectionPoint;
	}

	@Override
	public View<EntityCounter> getView()
	{
		return new ViewEntityCounter();
	}

	@Override
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.point);
	}

	@Override
	public Optional<EntityCounter> create(Connection connection)
	{
		return Optional.of(new EntityCounter(connection));
	}

}
