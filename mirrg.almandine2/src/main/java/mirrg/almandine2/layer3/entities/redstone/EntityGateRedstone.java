package mirrg.almandine2.layer3.entities.redstone;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;

public class EntityGateRedstone extends EntityBlock implements IBlockRedstone
{

	public boolean value = true;
	public TypeGateRedstone type;

	public EntityGateRedstone(Connection connection, TypeGateRedstone type)
	{
		super(connection);
		this.type = type;
	}

	public void move()
	{
		boolean value;
		switch (type) {
			case AND:
				value = getValues()
					.allMatch(v -> v);
				break;
			case OR:
				value = getValues()
					.anyMatch(v -> v);
				break;
			case NAND:
				value = !getValues()
					.allMatch(v -> v);
				break;
			case NOR:
				value = !getValues()
					.anyMatch(v -> v);
				break;
			case XOR: {
				value = getValues()
					.filter(v -> v)
					.collect(Collectors.counting()) % 2 == 1;
				break;
			}
			default:
				throw new RuntimeException("Illegal redstone gate type: " + type);
		}

		game.nitrogen.registerRemovable(GameAlmandine2.NitrogenEventPostMove.class, event -> {
			this.value = value;
			return false;
		});
	}

	private Stream<Boolean> getValues()
	{
		return game.data.getEntities(EntityWire.class)
			.filter(wire -> wire.getEnd() instanceof ConnectionBlock)
			.filter(wire -> ((ConnectionBlock) wire.getEnd()).entity == this)
			.filter(wire -> wire instanceof IWireRedstone)
			.map(wire -> (IWireRedstone) wire)
			.map(wire -> wire.getValueRedstone());
	}

	@Override
	public CardEntityBlock<?, ?> getCardEntity()
	{
		return getCardEntity();
	}

	private CardEntityBlock<EntityGateRedstone, ViewEntityGateRedstone<EntityGateRedstone>> getCardEntityImpl()
	{
		switch (type) {
			case AND:
				return CardEntityGateRedstone.AND;
			case OR:
				return CardEntityGateRedstone.OR;
			case NAND:
				return CardEntityGateRedstone.NAND;
			case NOR:
				return CardEntityGateRedstone.NOR;
			case XOR:
				return CardEntityGateRedstone.XOR;
			default:
				throw new RuntimeException("Illegal redstone gate type: " + type);
		}
	}

	@Override
	public double getRadiusRedstone(double angle)
	{
		return getCardEntityImpl().getView().getRadiusRedstone(this, angle);
	}

	@Override
	public boolean getValueRedstone()
	{
		return value;
	}

	@Override
	public EntityBlock getEntity()
	{
		return this;
	}

}
