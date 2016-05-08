package mirrg.almandine2.layer3.entities.slab;

import java.awt.Graphics2D;

import mirrg.almandine2.layer3.entities.station.ViewEntityCart;

public class ViewEntityCartSlot<E extends EntityCartSlot<E, V>, V extends ViewEntityCartSlot<E, V>> extends ViewEntityCart<E, V>
{

	public ViewEntityCartSlot(E entity)
	{
		super(entity);
	}

	@Override
	public void render(Graphics2D graphics)
	{
		super.render(graphics);

		new ViewSlot<>(() -> entity, () -> getPoint(), () -> 12, () -> 12).render(graphics);
	}

}
