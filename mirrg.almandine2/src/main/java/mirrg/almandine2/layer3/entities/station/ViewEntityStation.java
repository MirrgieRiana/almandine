package mirrg.almandine2.layer3.entities.station;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.view.ViewSurfaceCircle;

public class ViewEntityStation<E extends EntityStation<E, V>, V extends ViewEntityStation<E, V>> extends ViewSurfaceCircle
{

	protected E entity;

	public ViewEntityStation(E entity)
	{
		this.entity = entity;
	}

	@Override
	public Point2D.Double getPoint()
	{
		return entity.getPoint();
	}

	@Override
	public double getRadius()
	{
		return 20;
	}

	@Override
	public void render(Graphics2D graphics)
	{
		graphics.setColor(Color.decode("#29844D"));
		Stroke stroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(3));
		{
			graphics.draw(getShape(0));
		}
		graphics.setStroke(stroke);
	}

	public double getRadiusStation(double angle)
	{
		return getRadius(angle, 0);
	}

}
