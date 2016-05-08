package mirrg.almandine2.layer3.entities.counter;

import mirrg.almandine2.layer2.entity.CardEntity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;

public class EntityCounter<E extends EntityCounter<E, V>, V extends ViewEntityCounter<E, V>> extends EntityBlock<E, V>
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
	public CardEntity<?, ?> getCardEntityImpl()
	{
		return CardEntityCounter.INSTANCE;
	}

}
