package mirrg.almandine2.layer3.entities2.redstone;

import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.Connection;
import mirrg.almandine2.layer2.entity.ConnectionBlock;
import mirrg.almandine2.layer2.entity.ConnectionPoint;
import mirrg.almandine2.layer2.entity.TypeConnection;
import mirrg.almandine2.layer2.entity.View;

public class CardEntityWireRedstone extends CardEntityWire<EntityWireRedstone>
{

	public static final CardEntityWireRedstone INSTANCE = new CardEntityWireRedstone();

	@Override
	public Stream<TypeConnection> getConnectionTypesBegin()
	{
		return Stream.of(TypeConnection.block, TypeConnection.point);
	}

	@Override
	public boolean isConnectableBegin(Connection connection)
	{
		return connection instanceof ConnectionPoint
			|| (connection instanceof ConnectionBlock
				&& ((ConnectionBlock) connection).entity instanceof EntityGateRedstone);
	}

	@Override
	public Stream<TypeConnection> getConnectionTypesEnd()
	{
		return Stream.of(TypeConnection.block, TypeConnection.point);
	}

	@Override
	public boolean isConnectableEnd(Connection connection)
	{
		return connection instanceof ConnectionPoint
			|| (connection instanceof ConnectionBlock
				&& ((ConnectionBlock) connection).entity instanceof EntityGateRedstone);
	}

	@Override
	public EntityWireRedstone create(Connection begin, Connection end)
	{
		return new EntityWireRedstone(begin, end);
	}

	@Override
	public View<EntityWireRedstone> getView()
	{
		return new ViewWireRedstone();
	}

}
