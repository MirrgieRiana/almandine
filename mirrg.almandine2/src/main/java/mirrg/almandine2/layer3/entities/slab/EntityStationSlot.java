package mirrg.almandine2.layer3.entities.slab;

import java.util.Optional;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionAnchor;
import mirrg.almandine2.layer3.entities.station.EntityStation;
import mirrg.almandine2.layer3.entities.station.ICart;
import mirrg.struct.hydrogen.Tuple;

public class EntityStationSlot extends EntityStation implements IBlockSlot
{

	public TypeStationSlot type;

	public EntityStationSlot(Connection connection, TypeStationSlot type)
	{
		super(connection);
		this.type = type;
	}

	@Override
	public void move()
	{
		Tuple<ICart, ConnectionAnchor> cart = getCart().orElse(null);

		if (cart != null) {
			if (cart.getX() instanceof ICartSlot) {
				ICartSlot cartSlot = (ICartSlot) cart.getX();

				switch (type) {
					case NORMAL:
						leaveRandom(cart);
						break;
					case LOAD:
						if (cartSlot.getAmount() == cartSlot.getAmountMax()) leaveRandom(cart);
						break;
					case UNLOAD:
						if (cartSlot.getAmount() == 0) leaveRandom(cart);
						break;
					default:
						throw new RuntimeException("Illegal station slot type: " + type);
				}

			} else {
				leaveRandom(cart);
			}
		}
	}

	@Override
	public CardEntityBlock<?, ?> getCardEntity()
	{
		return getCardEntityImpl();
	}

	private CardEntityStationSlot<EntityStationSlot, ViewEntityStationSlot<EntityStationSlot>> getCardEntityImpl()
	{
		switch (type) {
			case NORMAL:
				return CardEntityStationSlot.NORMAL;
			case LOAD:
				return CardEntityStationSlot.LOAD;
			case UNLOAD:
				return CardEntityStationSlot.UNLOAD;
			default:
				throw new RuntimeException("Illegal station slot type: " + type);
		}
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

	@Override
	public void push(int amount)
	{
		getCartSlot().ifPresent(s -> s.push(amount));
	}

	@Override
	public void pop(int amount)
	{
		getCartSlot().ifPresent(s -> s.pop(amount));
	}

	@Override
	public double getRadiusSlot(double angle)
	{
		return getCardEntityImpl().getView().getRadiusSlot(this, angle);
	}

	public Optional<ICartSlot> getCartSlot()
	{
		Tuple<ICart, ConnectionAnchor> cart = getCarts()
			.min((a, b) -> a.getY().order - b.getY().order)
			.orElse(null);
		if (cart != null) {
			if (cart.getX() instanceof ICartSlot) {

				return Optional.of((ICartSlot) cart.getX());

			}
		}
		return Optional.empty();
	}

}
