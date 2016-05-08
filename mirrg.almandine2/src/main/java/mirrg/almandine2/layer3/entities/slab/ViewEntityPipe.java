package mirrg.almandine2.layer3.entities.slab;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.view.ViewLine;

public class ViewEntityPipe<E extends EntityPipe<E, V>, V extends ViewEntityPipe<E, V>> extends ViewLine
{

	protected E entity;

	public ViewEntityPipe(E entity)
	{
		this.entity = entity;
	}

	@Override
	public Double getPointBegin()
	{
		return entity.getBegin().getPoint();
	}

	@Override
	public Double getPointEnd()
	{
		return entity.getEnd().getPoint();
	}

	@Override
	public double getWidth()
	{
		return 3;
	}

	@Override
	public double getMarginBegin()
	{
		double angle = getAngle();
		if (entity.getBegin() instanceof ConnectionBlock) {
			return ((IBlockSlot) ((ConnectionBlock) entity.getBegin()).entity).getRadiusSlot(angle) + 1;
		}
		return 0;
	}

	@Override
	public double getMarginEnd()
	{
		double angle = getAngle();
		if (entity.getEnd() instanceof ConnectionBlock) {
			return ((IBlockSlot) ((ConnectionBlock) entity.getEnd()).entity).getRadiusSlot(angle + Math.PI) + 1;
		}
		return 0;
	}

	@Override
	public void render(Graphics2D graphics)
	{
		graphics.setColor(Color.decode("#1E8484"));
		drawArrow(graphics, 8, 30);

		if (entity.amount > 0) {
			Point2D.Double[] points = getPointsMargined();
			Point2D.Double center = new Point2D.Double(
				points[0].x + (points[1].x - points[0].x) * entity.position,
				points[0].y + (points[1].y - points[0].y) * entity.position);

			graphics.setColor(Color.red);
			graphics.fill(new Ellipse2D.Double(center.x - 3, center.y - 3, 6, 6));
		}
	}

}
