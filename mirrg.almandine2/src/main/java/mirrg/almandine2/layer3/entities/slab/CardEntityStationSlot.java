package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer3.entities.station.CardEntityStation;

public class CardEntityStationSlot<E extends Entity, V extends View<E>> extends CardEntityStation<E, V>
{

	public static final CardEntityStationSlot<EntityStationSlot, ViewEntityStationSlot<EntityStationSlot>> NORMAL = new CardEntityStationSlot<>(
		c -> Optional.of(new EntityStationSlot(c, TypeStationSlot.NORMAL)),
		ViewEntityStationSlot::new);
	public static final CardEntityStationSlot<EntityStationSlot, ViewEntityStationSlot<EntityStationSlot>> LOAD = new CardEntityStationSlot<>(
		c -> Optional.of(new EntityStationSlot(c, TypeStationSlot.LOAD)),
		ViewEntityStationSlot::new);
	public static final CardEntityStationSlot<EntityStationSlot, ViewEntityStationSlot<EntityStationSlot>> UNLOAD = new CardEntityStationSlot<>(
		c -> Optional.of(new EntityStationSlot(c, TypeStationSlot.UNLOAD)),
		ViewEntityStationSlot::new);

	public CardEntityStationSlot(Function<Connection, Optional<E>> supplierEntity, Supplier<V> supplierView)
	{
		super(supplierEntity, supplierView);
	}

}
