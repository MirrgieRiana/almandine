package mirrg.almandine2.layer3.entities.redstone;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public class CardEntityGateRedstone<E extends EntityGateRedstone> extends CardEntityBlock<E>
{

	public static CardEntityGateRedstone<EntityGateRedstone> AND = new CardEntityGateRedstone<>(c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.AND)), ViewEntityGateRedstone::new);
	public static CardEntityGateRedstone<EntityGateRedstone> OR = new CardEntityGateRedstone<>(c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.OR)), ViewEntityGateRedstone::new);
	public static CardEntityGateRedstone<EntityGateRedstone> NAND = new CardEntityGateRedstone<>(c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.NAND)), ViewEntityGateRedstone::new);
	public static CardEntityGateRedstone<EntityGateRedstone> NOR = new CardEntityGateRedstone<>(c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.NOR)), ViewEntityGateRedstone::new);
	public static CardEntityGateRedstone<EntityGateRedstone> XOR = new CardEntityGateRedstone<>(c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.XOR)), ViewEntityGateRedstone::new);

	public CardEntityGateRedstone(Function<Connection, Optional<E>> supplierEntity, Supplier<View<E>> supplierView)
	{
		super(supplierEntity, supplierView);
	}

	@Override
	public boolean isConnectable(Connection connection)
	{
		return true;
	}

	@Override
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.point);
	}

}
