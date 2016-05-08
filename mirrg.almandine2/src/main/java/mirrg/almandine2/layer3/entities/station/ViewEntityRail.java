package mirrg.almandine2.layer3.entities.station;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.view.ViewLine;

public class ViewEntityRail<E extends EntityRail<E, V>, V extends ViewEntityRail<E, V>> extends ViewLine
{

	protected E entity;

	public ViewEntityRail(E entity)
	{
		this.entity = entity;
	}

	@Override
	public Point2D.Double getPointBegin()
	{
		return entity.getBegin().getPoint();
	}

	@Override
	public Point2D.Double getPointEnd()
	{
		return entity.getEnd().getPoint();
	}

	@Override
	public double getMarginBegin()
	{
		double angle = getAngle();
		if (entity.getBegin() instanceof ConnectionBlock) {
			return ((IStation) ((ConnectionBlock) entity.getBegin()).entity).getRadiusStation(angle) + 1;
		}
		return 0;
	}

	@Override
	public double getMarginEnd()
	{
		double angle = getAngle();
		if (entity.getEnd() instanceof ConnectionBlock) {
			return ((IStation) ((ConnectionBlock) entity.getEnd()).entity).getRadiusStation(angle + Math.PI) + 1;
		}
		return 0;
	}

	@Override
	public double getWidth()
	{
		return 3;
	}

	@Override
	public void render(Graphics2D graphics)
	{
		graphics.setColor(Color.decode("#29844D"));
		Stroke stroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(3));
		{
			graphics.draw(getShape());
		}
		graphics.setStroke(stroke);
	}

}
