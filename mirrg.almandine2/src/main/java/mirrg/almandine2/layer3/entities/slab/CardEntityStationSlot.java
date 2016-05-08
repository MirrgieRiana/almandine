package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;
import java.util.function.Function;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer3.entities.station.CardEntityStation;

public class CardEntityStationSlot<E extends EntityStationSlot<E, V>, V extends ViewEntityStationSlot<E, V>> extends CardEntityStation<E, V>
{

	@SuppressWarnings({
		"unchecked", "rawtypes"
	})
	public static CardEntityBlock<?, ?> NORMAL = new CardEntityStationSlot<>(
		c -> Optional.of(new EntityStationSlot(c, TypeStationSlot.NORMAL)),
		e -> new ViewEntityStationSlot(e)),
		LOAD = new CardEntityStationSlot<>(
			c -> Optional.of(new EntityStationSlot(c, TypeStationSlot.LOAD)),
			e -> new ViewEntityStationSlot(e)),
		UNLOAD = new CardEntityStationSlot<>(
			c -> Optional.of(new EntityStationSlot(c, TypeStationSlot.UNLOAD)),
			e -> new ViewEntityStationSlot(e));

	public CardEntityStationSlot(Function<Connection, Optional<E>> functionEntity, Function<E, V> functionView)
	{
		super(functionEntity, functionView);
	}

}
