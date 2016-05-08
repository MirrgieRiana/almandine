package mirrg.almandine2.layer3.entities.slab;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;

public class EntitySlot extends EntityBlock implements IBlockSlot
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
	public CardEntityBlock<?, ?> getCardEntity()
	{
		return getCardEntityImpl();
	}

	private CardEntityBlock<EntitySlot, ViewEntitySlot<EntitySlot>> getCardEntityImpl()
	{
		return CardEntitySlot.INSTANCE;
	}

	@Override
	public EntityBlock getEntity()
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
		return getCardEntityImpl().getView().getRadiusSlot(this, angle);
	}

}
