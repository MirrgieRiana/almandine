package mirrg.almandine2.layer3.entities.redstone;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public class CardEntityGateRedstone<E extends Entity, V extends View<E>> extends CardEntityBlock<E, V>
{

	public static CardEntityGateRedstone<EntityGateRedstone, ViewEntityGateRedstone<EntityGateRedstone>> AND = new CardEntityGateRedstone<>(
		c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.AND)),
		ViewEntityGateRedstone::new);
	public static CardEntityGateRedstone<EntityGateRedstone, ViewEntityGateRedstone<EntityGateRedstone>> OR = new CardEntityGateRedstone<>(
		c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.OR)),
		ViewEntityGateRedstone::new);
	public static CardEntityGateRedstone<EntityGateRedstone, ViewEntityGateRedstone<EntityGateRedstone>> NAND = new CardEntityGateRedstone<>(
		c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.NAND)),
		ViewEntityGateRedstone::new);
	public static CardEntityGateRedstone<EntityGateRedstone, ViewEntityGateRedstone<EntityGateRedstone>> NOR = new CardEntityGateRedstone<>(
		c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.NOR)),
		ViewEntityGateRedstone::new);
	public static CardEntityGateRedstone<EntityGateRedstone, ViewEntityGateRedstone<EntityGateRedstone>> XOR = new CardEntityGateRedstone<>(
		c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.XOR)),
		ViewEntityGateRedstone::new);

	public CardEntityGateRedstone(Function<Connection, Optional<E>> supplierEntity, Supplier<V> supplierView)
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
