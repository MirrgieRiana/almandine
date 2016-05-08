package mirrg.almandine2.layer3.entities.redstone;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D.Double;

import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.view.ViewLine;

public class ViewEntityWireRedstone<E extends EntityWireRedstone<E, V>, V extends ViewEntityWireRedstone<E, V>> extends ViewLine
{

	protected E entity;

	public ViewEntityWireRedstone(E entity)
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
			return ((IBlockRedstone) ((ConnectionBlock) entity.getBegin()).entity).getRadiusRedstone(angle) + 1;
		}
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
	}

}
