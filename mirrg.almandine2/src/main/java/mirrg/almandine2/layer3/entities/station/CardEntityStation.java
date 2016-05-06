package mirrg.almandine2.layer3.entities.station;

import java.util.Optional;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public class CardEntityStation extends CardEntityBlock<EntityStation>
{

	public static final CardEntityStation INSTANCE = new CardEntityStation();

	@Override
	public boolean isConnectable(Connection connection)
	{
		return connection instanceof ConnectionPoint;
	}

	@Override
	public View<EntityStation> getView()
	{
		return new ViewEntityStation();
	}

	@Override
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.point);
	}

	@Override
	public Optional<EntityStation> create(Connection connection)
	{
		return Optional.of(new EntityStation(connection));
	}

}
