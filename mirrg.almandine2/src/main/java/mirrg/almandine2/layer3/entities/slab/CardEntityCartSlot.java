package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;
import java.util.function.Function;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer3.entities.station.CardEntityCart;

public class CardEntityCartSlot<E extends EntityCartSlot<E, V>, V extends ViewEntityCartSlot<E, V>> extends CardEntityCart<E, V>
{

	@SuppressWarnings({
		"unchecked", "rawtypes"
	})
	public static CardEntityBlock<?, ?> INSTANCE = new CardEntityCartSlot<>(
		c -> Optional.of(new EntityCartSlot(c, 10)),
		e -> new ViewEntityCartSlot(e));

	public CardEntityCartSlot(Function<Connection, Optional<E>> functionEntity, Function<E, V> functionView)
	{
		super(functionEntity, functionView);
	}

}
