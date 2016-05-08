package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer3.entities.station.CardEntityCart;

public class CardEntityCartSlot<E extends EntityCartSlot> extends CardEntityCart<E>
{

	public static final CardEntityCartSlot<EntityCartSlot> INSTANCE = new CardEntityCartSlot<>(c -> Optional.of(new EntityCartSlot(c, 10)), ViewEntityCartSlot::new);

	public CardEntityCartSlot(Function<Connection, Optional<E>> supplierEntity, Supplier<View<E>> supplierView)
	{
		super(supplierEntity, supplierView);
	}

}
