package mirrg.almandine2.layer3.entities.station;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.view.ViewSurfaceCircle;

public class ViewEntityStation<E extends EntityStation> extends ViewSurfaceCircle<E>
{

	@Override
	public Point2D.Double getPoint(E entity)
	{
		return entity.getPoint();
	}

	@Override
	public double getRadius(E entity)
	{
		return 20;
	}

	@Override
	public void render(E entity, Graphics2D graphics)
	{
		graphics.setColor(Color.decode("#29844D"));
		Stroke stroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(3));
		{
			graphics.draw(getShape(entity, 0));
		}
		graphics.setStroke(stroke);
	}

}
