package mirrg.almandine2.layer2.entity.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class ViewSurface extends View
{

	public abstract Point2D.Double getPoint();

	public abstract Shape getShape(double margin);

	@Override
	public void renderAura(Graphics2D graphics, double width, double margin, Color color)
	{
		Stroke stroke = graphics.getStroke();
		{
			graphics.setStroke(new BasicStroke((float) width));

			graphics.setColor(color);
			graphics.draw(getShape(margin));
		}
		graphics.setStroke(stroke);
	}

	@Override
	public Rectangle2D.Double getBound()
	{
		return (Rectangle2D.Double) getShape(0).getBounds2D();
	}

}
