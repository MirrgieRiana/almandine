package mirrg.almandine2.layer3.entities2.counter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.view.ViewSurfaceRectangle;
import mirrg.almandine2.layer3.entities.HRender;

public class ViewCounter extends ViewSurfaceRectangle<EntityCounter>
{

	@Override
	public Point2D.Double getPoint(EntityCounter entity)
	{
		return entity.getPoint();
	}

	@Override
	public double getWidth(EntityCounter entity)
	{
		return 64;
	}

	@Override
	public double getHeight(EntityCounter entity)
	{
		return 20;
	}

	@Override
	public void render(EntityCounter entity, Graphics2D graphics)
	{
		graphics.setColor(Color.black);
		graphics.setFont(HRender.getFontDefault());
		HRender.drawString(graphics, "" + entity.i, entity.getPoint().x, entity.getPoint().y, HRender.ALIGN_CENTER, HRender.ALIGN_MIDDLE);
	}

}
