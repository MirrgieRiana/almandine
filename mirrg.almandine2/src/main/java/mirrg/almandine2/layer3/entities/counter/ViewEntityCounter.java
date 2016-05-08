package mirrg.almandine2.layer3.entities.counter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.view.ViewSurfaceRectangle;
import mirrg.almandine2.layer3.entities.HRender;

public class ViewEntityCounter<E extends EntityCounter> extends ViewSurfaceRectangle<E>
{

	@Override
	public Point2D.Double getPoint(E entity)
	{
		return entity.getPoint();
	}

	@Override
	public double getWidth(E entity)
	{
		return 64;
	}

	@Override
	public double getHeight(E entity)
	{
		return 20;
	}

	@Override
	public void render(E entity, Graphics2D graphics)
	{
		graphics.setColor(Color.black);
		graphics.setFont(HRender.getFontDefault());
		HRender.drawString(graphics, "" + entity.i, entity.getPoint().x, entity.getPoint().y, HRender.ALIGN_CENTER, HRender.ALIGN_MIDDLE);
	}

}
