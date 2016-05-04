package mirrg.almandine2.layer3.entities.redstone;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.stream.Collectors;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.IEntityCircle;
import mirrg.almandine2.layer2.tool.Point;
import mirrg.almandine2.layer3.entities.EntityFurnitureAbstract;
import mirrg.almandine2.layer3.entities.IWire;

public class EntityFurnitureRedstoneGate extends EntityFurnitureAbstract implements IFurnitureRedstone, IEntityCircle
{

	public static enum TypeGate
	{
		AND("AND"),
		OR(""),
		NAND("NAND"),
		NOR("NOT"),
		XOR("XOR"),

		;

		private String label;

		private TypeGate(String label)
		{
			this.label = label;
		}

		@Override
		public String toString()
		{
			return label;
		}

	}

	private boolean value = true;
	private TypeGate type;

	public EntityFurnitureRedstoneGate(Point point, TypeGate type)
	{
		super(point);
		this.type = type;
	}

	public void move()
	{
		boolean value;
		switch (type) {
			case AND:
				value = game.data.getEntities()
					.filter(entity -> entity instanceof IWire)
					.map(entity -> (IWire) entity)
					.filter(wire -> wire.getEnd() == this)
					.filter(wire -> wire instanceof IWireRedstone)
					.map(wire -> (IWire & IWireRedstone) wire)
					.allMatch(wire -> wire.getValueRedstone());
				break;
			case OR:
				value = game.data.getEntities()
					.filter(entity -> entity instanceof IWire)
					.map(entity -> (IWire) entity)
					.filter(wire -> wire.getEnd() == this)
					.filter(wire -> wire instanceof IWireRedstone)
					.map(wire -> (IWire & IWireRedstone) wire)
					.anyMatch(wire -> wire.getValueRedstone());
				break;
			case NAND:
				value = !game.data.getEntities()
					.filter(entity -> entity instanceof IWire)
					.map(entity -> (IWire) entity)
					.filter(wire -> wire.getEnd() == this)
					.filter(wire -> wire instanceof IWireRedstone)
					.map(wire -> (IWire & IWireRedstone) wire)
					.allMatch(wire -> wire.getValueRedstone());
				break;
			case NOR:
				value = !game.data.getEntities()
					.filter(entity -> entity instanceof IWire)
					.map(entity -> (IWire) entity)
					.filter(wire -> wire.getEnd() == this)
					.filter(wire -> wire instanceof IWireRedstone)
					.map(wire -> (IWire & IWireRedstone) wire)
					.anyMatch(wire -> wire.getValueRedstone());
				break;
			case XOR: {
				int count = (int) (long) game.data.getEntities()
					.filter(entity -> entity instanceof IWire)
					.map(entity -> (IWire) entity)
					.filter(wire -> wire.getEnd() == this)
					.filter(wire -> wire instanceof IWireRedstone)
					.map(wire -> (IWire & IWireRedstone) wire)
					.filter(wire -> wire.getValueRedstone())
					.collect(Collectors.counting());
				value = count % 2 == 1;
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

	@Override
	public void render(Graphics2D graphics)
	{
		CardFurnitureRedstone.render(graphics, this, type, value);
	}

	@Override
	public boolean getValueRedstone()
	{
		return value;
	}

	@Override
	public Shape getShape()
	{
		return new Ellipse2D.Double(getX() - 5, getY() - 5, 10, 10);
	}

	@Override
	public double getRadiusRedstone()
	{
		return 5;
	}

	@Override
	public boolean isSettable(Point point)
	{
		return true;
	}

	@Override
	public double getRadius()
	{
		return 5;
	}

}
