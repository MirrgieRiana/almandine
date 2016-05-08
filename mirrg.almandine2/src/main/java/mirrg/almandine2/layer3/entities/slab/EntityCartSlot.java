package mirrg.almandine2.layer3.entities.slab;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer3.entities.station.EntityCart;

public class EntityCartSlot extends EntityCart implements ISlot
{

	public int amount;
	public int amountMax;

	public EntityCartSlot(Connection connection, int amountMax)
	{
		super(connection);
		amount = 0;
		this.amountMax = amountMax;
	}

	@Override
	public void move()
	{
		super.move();
		amount = 3; // TODO
	}

	@Override
	public CardEntityBlock<?> getCardEntity()
	{
		return CardEntityCartSlot.INSTANCE;
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
