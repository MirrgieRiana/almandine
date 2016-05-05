package mirrg.almandine2.layer3.entities.counter;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;

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
	public CardEntityBlock<?> getCardEntity()
	{
		return CardEntityCounter.INSTANCE;
	}

}
