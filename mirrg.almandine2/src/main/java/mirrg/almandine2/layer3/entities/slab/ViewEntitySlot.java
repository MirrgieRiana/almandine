package mirrg.almandine2.layer3.entities.slab;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.function.Supplier;

import mirrg.almandine2.layer2.entity.view.ViewSurfaceRectangle;
import mirrg.almandine2.layer3.entities.HRender;

public class ViewEntitySlot extends ViewSurfaceRectangle<EntitySlot>
{

	@Override
	public Point2D.Double getPoint(EntitySlot entity)
	{
		return entity.getPoint();
	}

	@Override
	public double getWidth(EntitySlot entity)
	{
		return 20;
	}

	@Override
	public double getHeight(EntitySlot entity)
	{
		return 20;
	}

	@Override
	public void render(EntitySlot entity, Graphics2D graphics)
	{
		render(entity, graphics, () -> getShape(entity, 0));
	}

	public static void render(ISlot entity, Graphics2D graphics, Supplier<Rectangle2D.Double> supplier)
	{
		graphics.setColor(Color.white);
		graphics.fill(supplier.get());

		graphics.setColor(Color.red);
		{
			Rectangle2D.Double shape = supplier.get();
			double ratio = entity.getAmountMax() != 0 ? (double) entity.getAmount() / entity.getAmountMax() : 0;
			shape.y += shape.height * (1 - ratio);
			shape.height *= ratio;
			graphics.fill(shape);
		}

		graphics.setColor(Color.black);
		graphics.draw(supplier.get());

		graphics.setColor(Color.black);
		graphics.setFont(HRender.getFontDefault().deriveFont(Font.BOLD));
		{
			Rectangle2D.Double shape = supplier.get();
			HRender.drawString(graphics, "" + entity.getAmount(), shape.getCenterX(), shape.getCenterY(), HRender.ALIGN_CENTER, HRender.ALIGN_MIDDLE);
		}
	}

}
