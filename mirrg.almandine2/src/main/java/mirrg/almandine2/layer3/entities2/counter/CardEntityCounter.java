package mirrg.almandine2.layer3.entities2.counter;

import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.Connection;
import mirrg.almandine2.layer2.entity.TypeConnection;
import mirrg.almandine2.layer2.entity.View;

public class CardEntityCounter extends CardEntityBlock<EntityCounter>
{

	public static final CardEntityCounter INSTANCE = new CardEntityCounter();

	@Override
	public boolean isConnectable(Connection connection)
	{
		return true;
	}

	@Override
	public View<EntityCounter> getView()
	{
		return new ViewCounter();
	}

	@Override
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.point);
	}

	@Override
	public EntityCounter create(Connection connection)
	{
		return new EntityCounter(connection);
	}

}
