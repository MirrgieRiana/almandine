package mirrg.almandine2.layer3.entities.counter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.view.ViewSurfaceRectangle;
import mirrg.almandine2.layer3.entities.HRender;

public class ViewEntityCounter<E extends EntityCounter<E, V>, V extends ViewEntityCounter<E, V>> extends ViewSurfaceRectangle
{

	protected E entity;

	public ViewEntityCounter(E entity)
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
		return 64;
	}

	@Override
	public double getHeight()
	{
		return 20;
	}

	@Override
	public void render(Graphics2D graphics)
	{
		graphics.setColor(Color.black);
		graphics.setFont(HRender.getFontDefault());
		HRender.drawString(graphics, "" + entity.i, entity.getPoint().x, entity.getPoint().y, HRender.ALIGN_CENTER, HRender.ALIGN_MIDDLE);
	}

}
