package mirrg.almandine2.layer3.entities.slab;

import java.awt.Color;
import java.awt.Graphics2D;

import mirrg.almandine2.layer3.entities.HRender;
import mirrg.almandine2.layer3.entities.station.ViewEntityStation;

public class ViewEntityStationSlot<E extends EntityStationSlot<E, V>, V extends ViewEntityStationSlot<E, V>> extends ViewEntityStation<E, V>
{

	public ViewEntityStationSlot(E entity)
	{
		super(entity);
	}

	@Override
	public void render(Graphics2D graphics)
	{
		super.render(graphics);

		entity.getCartSlot().ifPresent(s -> {
			new ViewSlot<>(() -> entity, () -> getPoint(), () -> 20, () -> 20).render(graphics);
		});

		graphics.setColor(Color.black);
		graphics.setFont(HRender.getFontDefault());
		HRender.drawString(graphics,
			entity.type.toString(),
			entity.getConnection().getPoint().x,
			entity.getConnection().getPoint().y + 25 + 3,
			HRender.ALIGN_CENTER,
			HRender.ALIGN_TOP);
	}

	public double getRadiusSlot(double angle)
	{
		return new ViewSlot<>(() -> entity, () -> getPoint(), () -> 20, () -> 20).getRadius(angle, 0);
	}

}
