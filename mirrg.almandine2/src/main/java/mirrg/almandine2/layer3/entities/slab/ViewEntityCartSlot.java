package mirrg.almandine2.layer3.entities.slab;

import java.awt.Graphics2D;

import mirrg.almandine2.layer3.entities.station.ViewEntityCart;

public class ViewEntityCartSlot<E extends EntityCartSlot<E, V>, V extends ViewEntityCartSlot<E, V>> extends ViewEntityCart<E, V>
{

	protected ViewSlot<ISlot> viewSlot = new ViewSlot<>(() -> entity, this::getPoint, () -> 12, () -> 12);

	public ViewEntityCartSlot(E entity)
	{
		super(entity);
	}

	@Override
	public void renderOverlay(Graphics2D graphics)
	{
		super.renderOverlay(graphics);
		viewSlot.renderBackground(graphics);
		viewSlot.render(graphics);
		viewSlot.renderOverlay(graphics);
	}

}
