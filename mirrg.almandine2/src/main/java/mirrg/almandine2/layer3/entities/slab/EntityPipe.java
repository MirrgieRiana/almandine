package mirrg.almandine2.layer3.entities.slab;

import mirrg.almandine2.layer2.entity.CardEntity;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;

public class EntityPipe<E extends EntityPipe<E, V>, V extends ViewEntityPipe<E, V>> extends EntityWire<E, V>
{

	public double position;
	public int amount;

	public EntityPipe(Connection begin, Connection end)
	{
		super(begin, end);
	}

	public void move()
	{
		if (amount != 0) {
			if (position >= 1) {

				if (getEnd() instanceof ConnectionBlock) {
					IBlockSlot end = (IBlockSlot) ((ConnectionBlock) getEnd()).entity;
					int space = end.getAmountMax() - end.getAmount();

					if (space >= amount) {
						end.push(amount);
						amount = 0;
						position = 0;
					}

				}

			} else {
				position += 0.01;
			}
		} else {
			if (getBegin() instanceof ConnectionBlock) {
				IBlockSlot begin = (IBlockSlot) ((ConnectionBlock) getBegin()).entity;

				if (begin.getAmount() > 0) {
					begin.pop(1);
					amount = 1;
					position = 0;
				}

			}
		}
	}

	@Override
	public CardEntity<?, ?> getCardEntityImpl()
	{
		return CardEntityPipe.INSTANCE;
	}

}
