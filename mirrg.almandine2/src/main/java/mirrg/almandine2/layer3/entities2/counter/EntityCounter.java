package mirrg.almandine2.layer3.entities2.counter;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.Connection;
import mirrg.almandine2.layer2.entity.EntityBlock;

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
