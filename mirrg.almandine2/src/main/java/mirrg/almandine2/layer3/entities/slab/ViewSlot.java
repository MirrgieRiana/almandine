package mirrg.almandine2.layer3.entities.slab;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import mirrg.almandine2.layer2.entity.view.ViewSurfaceRectangle;
import mirrg.almandine2.layer3.entities.HRender;

public class ViewSlot<E extends ISlot> extends ViewSurfaceRectangle
{

	protected Supplier<E> entity;
	private Supplier<Point2D.Double> point;
	private DoubleSupplier width;
	private DoubleSupplier height;

	public ViewSlot(Supplier<E> entity, Supplier<Point2D.Double> point, DoubleSupplier width, DoubleSupplier height)
	{
		this.entity = entity;
		this.point = point;
		this.width = width;
		this.height = height;
	}

	@Override
	public Point2D.Double getPoint()
	{
		return point.get();
	}

	@Override
	public double getWidth()
	{
		return width.getAsDouble();
	}

	@Override
	public double getHeight()
	{
		return height.getAsDouble();
	}

	@Override
	public void renderBackground(Graphics2D graphics)
	{
		graphics.setColor(Color.white);
		graphics.fill(getShape(0));

		graphics.setColor(Color.red);
		{
			Rectangle2D.Double shape = getShape(0);
			double ratio = entity.get().getAmountMax() != 0 ? (double) entity.get().getAmount() / entity.get().getAmountMax() : 0;
			shape.y += shape.height * (1 - ratio);
			shape.height *= ratio;
			graphics.fill(shape);
		}

		graphics.setColor(Color.decode("#1E8484"));
		graphics.draw(getShape(0));

		graphics.setColor(Color.black);
		graphics.setFont(HRender.getFontDefault().deriveFont(Font.BOLD));
		{
			Rectangle2D.Double shape = getShape(0);
			HRender.drawString(graphics, "" + entity.get().getAmount(), shape.getCenterX(), shape.getCenterY(), HRender.ALIGN_CENTER, HRender.ALIGN_MIDDLE);
		}
	}

	public double getRadiusSlot(double angle)
	{
		return getRadius(angle, 0);
	}

}
