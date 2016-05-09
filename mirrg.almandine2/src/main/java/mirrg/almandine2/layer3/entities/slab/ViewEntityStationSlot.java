package mirrg.almandine2.layer3.entities.slab;

import java.awt.Color;
import java.awt.Graphics2D;

import mirrg.almandine2.layer3.entities.HRender;
import mirrg.almandine2.layer3.entities.station.ViewEntityStation;

public class ViewEntityStationSlot<E extends EntityStationSlot<E, V>, V extends ViewEntityStationSlot<E, V>> extends ViewEntityStation<E, V>
{

	protected ViewSlot<ISlot> viewSlot = new ViewSlot<>(() -> entity, this::getPoint, () -> 20, () -> 20);

	public ViewEntityStationSlot(E entity)
	{
		super(entity);
	}

	@Override
	public void renderBackground(Graphics2D graphics)
	{
		super.renderBackground(graphics);

		graphics.setColor(Color.black);
		graphics.setFont(HRender.getFontDefault());
		HRender.drawString(graphics,
			entity.type.toString(),
			entity.getConnection().getPoint().x,
			entity.getConnection().getPoint().y + 25 + 3,
			HRender.ALIGN_CENTER,
			HRender.ALIGN_TOP);

		entity.getCartSlot().ifPresent(s -> {
			viewSlot.renderBackground(graphics);
		});
	}

	@Override
	public void render(Graphics2D graphics)
	{
		super.render(graphics);
		entity.getCartSlot().ifPresent(s -> {
			viewSlot.render(graphics);
		});
	}

	@Override
	public void renderOverlay(Graphics2D graphics)
	{
		super.renderOverlay(graphics);
		entity.getCartSlot().ifPresent(s -> {
			viewSlot.renderOverlay(graphics);
		});
	}

	public double getRadiusSlot(double angle)
	{
		return entity.getCartSlot()
			.map(s -> new ViewSlot<>(() -> s, () -> getPoint(), () -> 20, () -> 20).getRadius(angle, 0))
			.orElse(getRadiusStation(angle));
	}

}
