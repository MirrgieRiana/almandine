package mirrg.almandine2.layer3.entities.station;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.view.ViewWire;
import mirrg.almandine2.layer3.entities.HRender;

public class ViewEntityRail extends ViewWire<EntityRail>
{

	@Override
	public Point2D.Double getPointBegin(EntityRail entity)
	{
		return entity.getBegin().getPoint();
	}

	@Override
	public Point2D.Double getPointEnd(EntityRail entity)
	{
		return entity.getEnd().getPoint();
	}

	@Override
	public double getWidth(EntityRail entity)
	{
		return 3;
	}

	@Override
	public void render(EntityRail entity, Graphics2D graphics)
	{
		double marginBegin = 0;
		if (entity.getBegin() instanceof ConnectionBlock) {
			marginBegin = ((IStation) ((ConnectionBlock) entity.getBegin()).entity).getRadiusStation() + 1;
		}

		double marginEnd = 0;
		if (entity.getEnd() instanceof ConnectionBlock) {
			marginEnd = ((IStation) ((ConnectionBlock) entity.getEnd()).entity).getRadiusStation() + 1;
		}

		Point2D.Double[] points = HRender.getPointsMargined(entity.getBegin().getPoint(), entity.getEnd().getPoint(), marginBegin, marginEnd);

		graphics.setColor(Color.decode("#29844D"));
		Stroke stroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(3));
		{
			graphics.draw(new Line2D.Double(points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY()));
		}
		graphics.setStroke(stroke);
	}

}
