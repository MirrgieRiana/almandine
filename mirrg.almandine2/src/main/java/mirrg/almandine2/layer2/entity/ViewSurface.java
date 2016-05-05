package mirrg.almandine2.layer2.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

public abstract class ViewSurface<T> extends View<T>
{

	public abstract Shape getShape(T entity, double margin);

	@Override
	public void renderAura(T entity, Graphics2D graphics, double width, double margin, Color color)
	{
		Stroke stroke = graphics.getStroke();
		{
			graphics.setStroke(new BasicStroke((float) width));

			graphics.setColor(color);
			graphics.draw(getShape(entity, margin));
		}
		graphics.setStroke(stroke);
	}

}
