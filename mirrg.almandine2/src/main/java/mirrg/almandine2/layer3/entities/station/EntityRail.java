package mirrg.almandine2.layer3.entities.station;

import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;

public class EntityRail extends EntityWire implements IRail
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
	public CardEntityWire<?> getCardEntity()
	{
		return CardEntityRail.INSTANCE;
	}

	@Override
	public EntityWire getEntity()
	{
		return this;
	}

}
