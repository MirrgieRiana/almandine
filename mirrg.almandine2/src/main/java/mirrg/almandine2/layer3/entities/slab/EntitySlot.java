package mirrg.almandine2.layer3.entities.slab;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;

public class EntitySlot extends EntityBlock implements ISlot
{

	public int amount;
	public int amountMax;

	public EntitySlot(Connection connection, int amountMax)
	{
		super(connection);
		amount = 0;
		this.amountMax = amountMax;
	}

	@Override
	public void move()
	{
		amount = 3; // TODO
	}

	@Override
	public CardEntityBlock<?> getCardEntity()
	{
		return CardEntitySlot.INSTANCE;
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

}
