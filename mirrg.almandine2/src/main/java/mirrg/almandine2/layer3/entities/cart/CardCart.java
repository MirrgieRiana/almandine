package mirrg.almandine2.layer3.entities.cart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import mirrg.almandine2.layer2.tool.IPoint;
import mirrg.almandine2.layer2.tool.Point;
import mirrg.almandine2.layer3.entities.ICardCart;
import mirrg.almandine2.layer3.entities.ICart;
import mirrg.almandine2.layer3.entities.IWire;
import mirrg.almandine2.layer3.entities.cart.station.IFurnitureStation;
import mirrg.almandine2.layer3.entities.cart.station.IWireRail;

public class CardCart implements ICardCart
{

	public static CardCart INSTANCE = new CardCart();

	@Override
	public ICart create(IWire wire, double position, boolean reverse)
	{
		return new EntityCartSlab(new PositionRail((IWireRail) wire, position, reverse));
	}

	@Override
	public void render(Graphics2D graphics, IWire wire, double position, boolean reverse)
	{
		renderImpl(graphics, new PositionRail((IWireRail) wire, position, reverse));
	}

	public static void renderImpl(Graphics2D graphics, IPosition position)
	{
		graphics.setColor(Color.blue);
		Stroke stroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(3));
		{
			Point center = getCenter(position);
			graphics.draw(new Ellipse2D.Double(center.getX() - 10, center.getY() - 10, 20, 20));

			double angle = getAngle(position);

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

	public static Point getCenter(IPosition position)
	{
		if (position instanceof PositionRail) {
			PositionRail positionRail = (PositionRail) position;

			IPoint begin = positionRail.rail.getBegin();
			IPoint end = positionRail.rail.getEnd();

			double x = end.getX() - begin.getX();
			double y = end.getY() - begin.getY();

			return new Point(begin.getX() + positionRail.position * x, begin.getY() + positionRail.position * y);

		} else if (position instanceof PositionStation) {
			PositionStation positionation = (PositionStation) position;

			return new Point(positionation.station.getX(), positionation.station.getY() - 50 - 30 * 0);

		} else {
			throw new IllegalCartPositionException(position);
		}
	}

	public static double getAngle(IPosition position)
	{
		if (position instanceof PositionRail) {
			double angle = ((PositionRail) position).rail.getAngle();
			if (((PositionRail) position).reverse) angle += 180 * Math.PI / 180;
			return angle;
		} else if (position instanceof PositionStation) {
			return 0;
		} else {
			throw new IllegalCartPositionException(position);
		}
	}

	@Override
	public boolean isSettable(IWire wire, double position, boolean reverse)
	{
		return wire instanceof IWireRail;
	}

	@Override
	public boolean isSettable(IPoint point)
	{
		return point instanceof IFurnitureStation || point instanceof Point;
	}

}
