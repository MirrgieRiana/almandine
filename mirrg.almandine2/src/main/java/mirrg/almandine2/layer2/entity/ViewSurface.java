package mirrg.almandine2.layer2.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

public abstract class ViewSurface<E extends EntitySurface> extends View<E>
{

	public abstract Shape getShape(E entity, double margin);

	@Override
	public void renderAura(E entity, Graphics2D graphics, double width, double margin, Color color)
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
