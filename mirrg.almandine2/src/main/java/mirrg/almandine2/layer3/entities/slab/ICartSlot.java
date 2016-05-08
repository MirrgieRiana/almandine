package mirrg.almandine2.layer3.entities.slab;

import mirrg.almandine2.layer3.entities.station.EntityCart;

public interface ICartSlot extends ISlot
{

	public EntityCart<?, ?> getEntity();

}
