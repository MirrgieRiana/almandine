package mirrg.almandine2.layer3.entities.station;

import mirrg.almandine2.layer2.entity.CardEntity;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;

public class EntityRail<E extends EntityRail<E, V>, V extends ViewEntityRail<E, V>> extends EntityWire<E, V> implements IRail
{

	public EntityRail(Connection begin, Connection end)
	{
		super(begin, end);
	}

	@Override
	public void move()
	{

	}

	@Override
	public CardEntity<?, ?> getCardEntityImpl()
	{
		return CardEntityRail.INSTANCE;
	}

	@Override
	public EntityWire<?, ?> getEntity()
	{
		return this;
	}

}
