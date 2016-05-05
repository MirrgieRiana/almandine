package mirrg.almandine2.layer3.entities.redstone;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;

public class EntityWireRedstone extends EntityWire implements IWireRedstone
{

	public boolean value;

	public EntityWireRedstone(Connection begin, Connection end)
	{
		super(begin, end);
	}

	public void move()
	{
		boolean value = getBegin() instanceof ConnectionBlock
			? ((IBlockRedstone) ((ConnectionBlock) getBegin()).entity).getValueRedstone()
			: false;

		game.nitrogen.registerRemovable(GameAlmandine2.NitrogenEventPostMove.class, event -> {
			this.value = value;
			return false;
		});
	}

	@Override
	public CardEntityWire<?> getCardEntity()
	{
		return CardEntityWireRedstone.INSTANCE;
	}

	@Override
	public boolean getValueRedstone()
	{
		return value;
	}

	@Override
	public EntityWire getEntity()
	{
		return this;
	}

}
