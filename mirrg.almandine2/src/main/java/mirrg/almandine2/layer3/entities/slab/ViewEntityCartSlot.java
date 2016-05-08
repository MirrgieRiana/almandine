package mirrg.almandine2.layer3.entities.slab;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import mirrg.almandine2.layer3.entities.station.EntityCart;
import mirrg.almandine2.layer3.entities.station.ViewEntityCart;

public class ViewEntityCartSlot extends ViewEntityCart
{
	@Override
	public void render(EntityCart entity, Graphics2D graphics)
	{
		super.render(entity, graphics);

		Point2D.Double center = getCenter(entity.getConnection());
		ViewEntitySlot.render((EntityCartSlot) /* TODO */ entity, graphics, () -> new Rectangle2D.Double(
			center.x - 6,
			center.y - 6,
			12,
			12));
	}

}
