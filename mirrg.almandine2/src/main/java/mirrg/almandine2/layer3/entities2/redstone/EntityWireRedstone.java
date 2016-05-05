package mirrg.almandine2.layer3.entities2.redstone;

import java.util.stream.Stream;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.Connection;
import mirrg.almandine2.layer2.entity.ConnectionBlock;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.entity.IHandle;

public class EntityWireRedstone extends EntityWire
{

	public boolean value;

	public EntityWireRedstone(Connection begin, Connection end)
	{
		super(begin, end);
	}

	public void move()
	{
		boolean value = getBegin() instanceof ConnectionBlock
			? ((EntityGateRedstone) ((ConnectionBlock) getBegin()).entity).value
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
	public Stream<IHandle> getHandles()
	{
		return Stream.empty();
	}

}
