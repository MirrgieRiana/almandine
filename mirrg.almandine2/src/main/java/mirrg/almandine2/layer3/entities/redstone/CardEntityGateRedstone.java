package mirrg.almandine2.layer3.entities.redstone;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;

public class CardEntityGateRedstone<E extends EntityGateRedstone<E, V>, V extends ViewEntityGateRedstone<E, V>> extends CardEntityBlock<E, V>
{

	@SuppressWarnings({
		"unchecked", "rawtypes"
	})
	public static CardEntityBlock<?, ?> AND = new CardEntityGateRedstone<>(
		c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.AND)),
		e -> new ViewEntityGateRedstone(e)),
		OR = new CardEntityGateRedstone<>(
			c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.OR)),
			e -> new ViewEntityGateRedstone(e)),
		NAND = new CardEntityGateRedstone<>(
			c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.NAND)),
			e -> new ViewEntityGateRedstone(e)),
		NOR = new CardEntityGateRedstone<>(
			c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.NOR)),
			e -> new ViewEntityGateRedstone(e)),
		XOR = new CardEntityGateRedstone<>(
			c -> Optional.of(new EntityGateRedstone(c, TypeGateRedstone.XOR)),
			e -> new ViewEntityGateRedstone(e));

	public CardEntityGateRedstone(Function<Connection, Optional<E>> functionEntity, Function<E, V> functionView)
	{
		super(functionEntity, functionView);
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
