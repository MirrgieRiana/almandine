package mirrg.almandine2.layer3.entities.slab;

import mirrg.almandine2.layer2.entity.CardEntity;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer3.entities.station.EntityCart;

public class EntityCartSlot<E extends EntityCartSlot<E, V>, V extends ViewEntityCartSlot<E, V>> extends EntityCart<E, V> implements ICartSlot
{

	public int amount;
	public int amountMax;

	public EntityCartSlot(Connection connection, int amountMax)
	{
		super(connection);
		amount = 3; // TODO
		this.amountMax = amountMax;
	}

	@Override
	public void move()
	{
		super.move();
	}

	@Override
	public CardEntity<?, ?> getCardEntityImpl()
	{
		return CardEntityCartSlot.INSTANCE;
	}

	@Override
	public EntityCart<?, ?> getEntity()
	{
		return this;
	}

	@Override
	public int getAmount()
	{
		return amount;
	}

	@Override
	public int getAmountMax()
	{
		return amountMax;
	}

	@Override
	public void push(int amount)
	{
		this.amount += amount;
	}

	@Override
	public void pop(int amount)
	{
		this.amount -= amount;
	}

}
