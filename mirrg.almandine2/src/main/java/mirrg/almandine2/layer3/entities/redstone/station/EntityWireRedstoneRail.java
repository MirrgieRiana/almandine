package mirrg.almandine2.layer3.entities.redstone.station;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.almandine2.layer3.entities.redstone.IWireRedstone;
import mirrg.almandine2.layer3.entities.station.ICart;

public class EntityWireRedstoneRail extends EntityWire implements IWireRedstone
{

	public boolean value;

	public EntityWireRedstoneRail(Connection begin, Connection end)
	{
		super(begin, end);
	}

	public void move()
	{
		boolean value;

		if (getBegin() instanceof ConnectionTraffic) {
			value = ((ConnectionTraffic) getBegin()).entity.getConnectors().anyMatch(c -> {
				if (c instanceof ConnectionTraffic) {
					if (((ConnectionTraffic) c).getParent() instanceof ICart) {
						return true;
					}
				}
				return false;
			});
		} else {
			value = false;
		}

		game.nitrogen.registerRemovable(GameAlmandine2.NitrogenEventPostMove.class, event -> {
			this.value = value;
			return false;
		});
	}

	@Override
	public CardEntityWire<?> getCardEntity()
	{
		return CardEntityWireRedstoneRail.INSTANCE;
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
