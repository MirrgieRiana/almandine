package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer3.entities.station.CardEntityCart;

public class CardEntityCartSlot<E extends Entity, V extends View<E>> extends CardEntityCart<E, V>
{

	public static final CardEntityCartSlot<EntityCartSlot, ViewEntityCartSlot<EntityCartSlot>> INSTANCE = new CardEntityCartSlot<>(
		c -> Optional.of(new EntityCartSlot(c, 10)),
		ViewEntityCartSlot::new);

	public CardEntityCartSlot(Function<Connection, Optional<E>> supplierEntity, Supplier<V> supplierView)
	{
		super(supplierEntity, supplierView);
	}

}
