package mirrg.almandine2.layer3.entities.redstone.station;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.view.ViewWire;
import mirrg.almandine2.layer3.entities.HRender;
import mirrg.almandine2.layer3.entities.redstone.IBlockRedstone;

public class ViewEntityWireRedstoneRail extends ViewWire<EntityWireRedstoneRail>
{

	@Override
	public Double getPointBegin(EntityWireRedstoneRail entity)
	{
		return entity.getBegin().getPoint();
	}

	@Override
	public Double getPointEnd(EntityWireRedstoneRail entity)
	{
		return entity.getEnd().getPoint();
	}

	@Override
	public double getWidth(EntityWireRedstoneRail entity)
	{
		return 3;
	}

	@Override
	public void render(EntityWireRedstoneRail entity, Graphics2D graphics)
	{
		{
			double marginBegin = 0;
			if (entity.getBegin() instanceof ConnectionBlock) {
				marginBegin = ((IBlockRedstone) ((ConnectionBlock) entity.getBegin()).entity).getRadiusRedstone() + 1;
			}

			double marginEnd = 0;
			if (entity.getEnd() instanceof ConnectionBlock) {
				marginEnd = ((IBlockRedstone) ((ConnectionBlock) entity.getEnd()).entity).getRadiusRedstone() + 1;
			}

			Point2D.Double[] points = HRender.getPointsMargined(entity.getBegin().getPoint(), entity.getEnd().getPoint(), marginBegin, marginEnd);

			graphics.setColor(entity.value ? Color.decode("#ff0000") : Color.decode("#440000"));
			HRender.drawArrow(graphics, points[0], points[1], 8, 30);
		}

		{
			graphics.setColor(Color.decode("#434F2A"));
			graphics.fill(new Ellipse2D.Double(
				entity.getBegin().getPoint().x - 5,
				entity.getBegin().getPoint().y - 5,
				10,
				10));
		}
	}

}
