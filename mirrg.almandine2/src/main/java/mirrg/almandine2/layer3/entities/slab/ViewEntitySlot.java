package mirrg.almandine2.layer3.entities.slab;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.view.ViewSurfaceRectangle;

public class ViewEntitySlot<E extends EntitySlot<E, V>, V extends ViewEntitySlot<E, V>> extends ViewSurfaceRectangle
{

	protected E entity;
	protected ViewSlot<ISlot> viewSlot = new ViewSlot<>(() -> entity, this::getPoint, this::getWidth, this::getHeight);

	public ViewEntitySlot(E entity)
	{
		this.entity = entity;
	}

	@Override
	public Point2D.Double getPoint()
	{
		return entity.getPoint();
	}

	@Override
	public double getWidth()
	{
		return 20;
	}

	@Override
	public double getHeight()
	{
		return 20;
	}

	@Override
	public void renderBackground(Graphics2D graphics)
	{
		super.renderBackground(graphics);
		viewSlot.renderBackground(graphics);
	}

	@Override
	public void render(Graphics2D graphics)
	{
		super.render(graphics);
		viewSlot.render(graphics);
	}

	@Override
	public void renderOverlay(Graphics2D graphics)
	{
		super.renderOverlay(graphics);
		viewSlot.renderOverlay(graphics);
	}

	public double getRadiusSlot(double angle)
	{
		return viewSlot.getRadius(angle, 0);
	}

}
