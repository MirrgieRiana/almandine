package mirrg.almandine2.layer3.entities2.redstone;

import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public class CardEntityGateRedstone extends CardEntityBlock<EntityGateRedstone>
{

	public static CardEntityGateRedstone AND = new CardEntityGateRedstone(TypeGateRedstone.AND);
	public static CardEntityGateRedstone OR = new CardEntityGateRedstone(TypeGateRedstone.OR);
	public static CardEntityGateRedstone NAND = new CardEntityGateRedstone(TypeGateRedstone.NAND);
	public static CardEntityGateRedstone NOR = new CardEntityGateRedstone(TypeGateRedstone.NOR);
	public static CardEntityGateRedstone XOR = new CardEntityGateRedstone(TypeGateRedstone.XOR);

	private TypeGateRedstone type;

	public CardEntityGateRedstone(TypeGateRedstone type)
	{
		this.type = type;
	}

	@Override
	public boolean isConnectable(Connection connection)
	{
		return true;
	}

	@Override
	public View<EntityGateRedstone> getView()
	{
		return new ViewGateRedstone();
	}

	@Override
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.point);
	}

	@Override
	public EntityGateRedstone create(Connection connection)
	{
		return new EntityGateRedstone(connection, type);
	}

}
