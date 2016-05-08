package mirrg.almandine2.layer3.entities.slab;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import mirrg.almandine2.layer3.entities.station.ViewEntityStation;

public class ViewEntityStationSlot<E extends EntityStationSlot> extends ViewEntityStation<E>
{

	@Override
	public void render(E entity, Graphics2D graphics)
	{
		super.render(entity, graphics);

		entity.getCartSlot().ifPresent(s -> {

			ViewEntitySlot.render(s, graphics, () -> {
				Ellipse2D.Double shape = getShape(entity, -8);
				return new Rectangle2D.Double(
					shape.x,
					shape.y,
					shape.width,
					shape.height);
			});

		});

	}

}
