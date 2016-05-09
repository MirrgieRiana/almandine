package mirrg.almandine2.layer3.entities.station;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionAnchor;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.almandine2.layer2.entity.view.ViewSurfaceCircle;

public class ViewEntityCart<E extends EntityCart<E, V>, V extends ViewEntityCart<E, V>> extends ViewSurfaceCircle
{

	protected E entity;

	public ViewEntityCart(E entity)
	{
		this.entity = entity;
	}

	@Override
	public Point2D.Double getPoint()
	{
		return entity.getPoint();
	}

	@Override
	public double getRadius()
	{
		return 10;
	}

	@Override
	public void renderOverlay(Graphics2D graphics)
	{
		graphics.setColor(Color.blue);
		Stroke stroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(3));
		{
			Point2D.Double center = getPoint();
			graphics.draw(new Ellipse2D.Double(center.getX() - 10, center.getY() - 10, 20, 20));

			double angle = getAngle(entity.getConnection());

			graphics.draw(new Line2D.Double(
				center.getX() + (double) 10 * Math.cos(angle),
				center.getY() + (double) 10 * Math.sin(angle),
				center.getX() + (double) 10 * Math.cos(angle + 150 * Math.PI / 180),
				center.getY() + (double) 10 * Math.sin(angle + 150 * Math.PI / 180)));
			graphics.draw(new Line2D.Double(
				center.getX() + (double) 10 * Math.cos(angle),
				center.getY() + (double) 10 * Math.sin(angle),
				center.getX() + (double) 10 * Math.cos(angle - 150 * Math.PI / 180),
				center.getY() + (double) 10 * Math.sin(angle - 150 * Math.PI / 180)));
		}
		graphics.setStroke(stroke);
	}

	protected double getAngle(Connection connection)
	{
		if (connection instanceof ConnectionTraffic) {
			ConnectionTraffic connection2 = (ConnectionTraffic) connection;
			double angle = connection2.entity.getView().getAngle();
			if (connection2.reverse) angle += 180 * Math.PI / 180;
			return angle;
		} else if (connection instanceof ConnectionAnchor) {
			return 0;
		} else {
			return 0;
		}
	}

}
