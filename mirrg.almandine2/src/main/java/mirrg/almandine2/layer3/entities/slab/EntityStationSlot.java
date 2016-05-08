package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionAnchor;
import mirrg.almandine2.layer3.entities.station.EntityStation;
import mirrg.almandine2.layer3.entities.station.ICart;
import mirrg.struct.hydrogen.Tuple;

public class EntityStationSlot extends EntityStation implements ISlot
{

	public EntityStationSlot(Connection connection)
	{
		super(connection);
	}

	@Override
	public CardEntityBlock<?> getCardEntity()
	{
		return CardEntityStationSlot.INSTANCE;
	}

	@Override
	public int getAmount()
	{
		return getCartSlot().map(s -> s.getAmount()).orElse(0);
	}

	@Override
	public int getAmountMax()
	{
		return getCartSlot().map(s -> s.getAmountMax()).orElse(0);
	}

	public Optional<ISlot> getCartSlot()
	{
		Tuple<ICart, ConnectionAnchor> cart = getCarts()
			.min((a, b) -> a.getY().order - b.getY().order)
			.orElse(null);
		if (cart != null) {
			if (cart.getX() instanceof ISlot) {

				return Optional.of((ISlot) cart.getX());

			}
		}
		return Optional.empty();
	}

}
