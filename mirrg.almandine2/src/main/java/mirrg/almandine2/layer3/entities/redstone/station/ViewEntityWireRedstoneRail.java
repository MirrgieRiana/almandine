package mirrg.almandine2.layer3.entities.redstone.station;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D.Double;

import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.view.ViewLine;
import mirrg.almandine2.layer3.entities.redstone.IBlockRedstone;

public class ViewEntityWireRedstoneRail<E extends EntityWireRedstoneRail<E, V>, V extends ViewEntityWireRedstoneRail<E, V>> extends ViewLine
{

	protected E entity;

	public ViewEntityWireRedstoneRail(E entity)
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
		return 0;
	}

	@Override
	public double getMarginEnd()
	{
		double angle = getAngle();
		if (entity.getEnd() instanceof ConnectionBlock) {
			return ((IBlockRedstone) ((ConnectionBlock) entity.getEnd()).entity).getRadiusRedstone(angle + Math.PI) + 1;
		}
		return 0;
	}

	@Override
	public void render(Graphics2D graphics)
	{
		graphics.setColor(entity.value ? Color.decode("#ff0000") : Color.decode("#440000"));
		drawArrow(graphics, 8, 30);

		graphics.setColor(Color.decode("#434F2A"));
		graphics.fill(new Ellipse2D.Double(
			entity.getBegin().getPoint().x - 5,
			entity.getBegin().getPoint().y - 5,
			10,
			10));
	}

}
