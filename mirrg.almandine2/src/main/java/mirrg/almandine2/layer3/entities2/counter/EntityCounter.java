package mirrg.almandine2.layer3.entities2.counter;

import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntity;
import mirrg.almandine2.layer2.entity.Connection;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.Event;
import mirrg.almandine2.layer2.entity.IHandle;

public class EntityCounter extends EntityBlock
{

	public int i;

	public EntityCounter(Connection connection)
	{
		super(connection);
	}

	@Override
	public void move()
	{
		i++;
	}

	@Override
	public CardEntity<?> getCardEntity()
	{
		return CardEntityCounter.INSTANCE;
	}

	@Override
	public void onConnectionEvent(Entity owner, Event event)
	{

	}

	@Override
	public Stream<IHandle> getHandles()
	{
		return Stream.empty();
	}

}
