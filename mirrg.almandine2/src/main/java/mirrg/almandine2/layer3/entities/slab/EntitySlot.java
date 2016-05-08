package mirrg.almandine2.layer3.entities.slab;

import mirrg.almandine2.layer2.entity.CardEntity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;

public class EntitySlot<E extends EntitySlot<E, V>, V extends ViewEntitySlot<E, V>> extends EntityBlock<E, V> implements IBlockSlot
{

	public int amount;
	public int amountMax;

	public EntitySlot(Connection connection, int amountMax)
	{
		super(connection);
		amount = 3; // TODO
		this.amountMax = amountMax;
	}

	@Override
	public void move()
	{

	}

	@Override
	public CardEntity<?, ?> getCardEntityImpl()
	{
		return CardEntitySlot.INSTANCE;
	}

	@Override
	public EntityBlock<?, ?> getEntity()
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

	@Override
	public double getRadiusSlot(double angle)
	{
		return getView().getRadiusSlot(angle);
	}

}
