package mirrg.almandine2.layer3.entities.slab;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.view.ViewWire;
import mirrg.almandine2.layer3.entities.HRender;

public class ViewEntityPipe<E extends EntityPipe> extends ViewWire<E>
{

	@Override
	public Double getPointBegin(E entity)
	{
		return entity.getBegin().getPoint();
	}

	@Override
	public Double getPointEnd(E entity)
	{
		return entity.getEnd().getPoint();
	}

	@Override
	public double getWidth(E entity)
	{
		return 3;
	}

	@Override
	public void render(E entity, Graphics2D graphics)
	{
		{
			double angle = entity.getAngle();

			double marginBegin = 0;
			if (entity.getBegin() instanceof ConnectionBlock) {
				marginBegin = ((IBlockSlot) ((ConnectionBlock) entity.getBegin()).entity).getRadiusSlot(angle) + 1;
			}

			double marginEnd = 0;
			if (entity.getEnd() instanceof ConnectionBlock) {
				marginEnd = ((IBlockSlot) ((ConnectionBlock) entity.getEnd()).entity).getRadiusSlot(angle + Math.PI) + 1;
			}

			Point2D.Double[] points = HRender.getPointsMargined(entity.getBegin().getPoint(), entity.getEnd().getPoint(), marginBegin, marginEnd);

			graphics.setColor(Color.decode("#1E8484"));
			HRender.drawArrow(graphics, points[0], points[1], 8, 30);
		}
		if (entity.amount > 0) {
			Point2D.Double center = entity.getPoint(entity.position);

			graphics.setColor(Color.red);
			graphics.fill(new Ellipse2D.Double(center.x - 3, center.y - 3, 6, 6));
		}
	}

}
