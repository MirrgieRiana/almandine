package mirrg.almandine2.layer3.entities2.redstone;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import mirrg.almandine2.layer2.entity.ViewSurfaceCircle;
import mirrg.almandine2.layer3.entities.HRender;

public class ViewGateRedstone extends ViewSurfaceCircle<EntityGateRedstone>
{

	@Override
	public double getRadius(EntityGateRedstone entity)
	{
		return 5;
	}

	@Override
	public void render(EntityGateRedstone entity, Graphics2D graphics)
	{
		graphics.setColor(entity.value ? Color.decode("#ff0000") : Color.decode("#440000"));
		graphics.fill(new Ellipse2D.Double(
			entity.getConnection().getPoint().x - 5,
			entity.getConnection().getPoint().y - 5,
			10,
			10));

		graphics.setColor(Color.black);
		graphics.setFont(HRender.getFontDefault());
		HRender.drawString(graphics,
			entity.type.toString(),
			entity.getConnection().getPoint().x,
			entity.getConnection().getPoint().y + 5 + 3,
			HRender.ALIGN_CENTER,
			HRender.ALIGN_TOP);
	}

}