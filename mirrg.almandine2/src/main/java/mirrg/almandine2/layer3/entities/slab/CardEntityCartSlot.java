package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer3.entities.station.CardEntityCart;
import mirrg.almandine2.layer3.entities.station.EntityCart;

public class CardEntityCartSlot extends CardEntityCart
{

	public static final CardEntityCartSlot INSTANCE = new CardEntityCartSlot();

	@Override
	public View<EntityCart /* TODO */> getView()
	{
		return new ViewEntityCartSlot();
	}

	@Override
	public Optional<EntityCart> create(Connection connection)
	{
		return Optional.of(new EntityCartSlot(connection, 10));
	}

}
