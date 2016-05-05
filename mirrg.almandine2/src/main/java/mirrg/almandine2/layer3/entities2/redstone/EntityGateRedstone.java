package mirrg.almandine2.layer3.entities2.redstone;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;

public class EntityGateRedstone extends EntityBlock
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
				value = getWires()
					.allMatch(wire -> wire.value);
				break;
			case OR:
				value = getWires()
					.anyMatch(wire -> wire.value);
				break;
			case NAND:
				value = !getWires()
					.allMatch(wire -> wire.value);
				break;
			case NOR:
				value = !getWires()
					.anyMatch(wire -> wire.value);
				break;
			case XOR: {
				value = getWires()
					.filter(wire -> wire.value)
					.collect(Collectors.counting()) % 2 == 1;
				break;
			}
			default:
				value = false;
				break;
		}

		game.nitrogen.registerRemovable(GameAlmandine2.NitrogenEventPostMove.class, event -> {
			this.value = value;
			return false;
		});
	}

	private Stream<EntityWireRedstone> getWires()
	{
		return game.data.getEntities(EntityWireRedstone.class)
			.filter(wire -> wire.getEnd() instanceof ConnectionBlock)
			.filter(wire -> ((ConnectionBlock) wire.getEnd()).entity == this);
	}

	@Override
	public CardEntityBlock<?> getCardEntity()
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

	public int getRadiusRedstone() // TODO interface
	{
		return 5;
	}

}
