package mirrg.almandine2.layer3.entities.cart.station;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import mirrg.almandine2.layer2.tool.IPoint;
import mirrg.almandine2.layer2.tool.Point;
import mirrg.almandine2.layer3.entities.EntityWireAbstract;
import mirrg.almandine2.layer3.entities.HRender;
import mirrg.almandine2.layer3.entities.ICardWire;
import mirrg.almandine2.layer3.entities.IWire;

public class CardWireRail implements ICardWire
{

	public static CardWireRail INSTANCE = new CardWireRail();

	@Override
	public IWire create(IPoint begin, IPoint end)
	{
		return new Entity(begin, end);
	}

	@Override
	public void render(Graphics2D graphics, IPoint begin, IPoint end)
	{
		double marginBegin = 0;
		if (begin instanceof IFurnitureStation) {
			marginBegin = ((IFurnitureStation) begin).getRadiusStation() + 1;
		}

		double marginEnd = 0;
		if (end instanceof IFurnitureStation) {
			marginEnd = ((IFurnitureStation) end).getRadiusStation() + 1;
		}

		Point[] points = HRender.getPointsMargined(begin, end, marginBegin, marginEnd);

		graphics.setColor(Color.decode("#29844D"));
		Stroke stroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(2));
		{
			graphics.draw(new Line2D.Double(points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY()));
		}
		graphics.setStroke(stroke);
	}

	@Override
	public boolean isSettableBegin(IPoint point)
	{
		return point instanceof IFurnitureStation || point instanceof Point;
	}

	@Override
	public boolean isSettableEnd(IPoint point)
	{
		return point instanceof IFurnitureStation || point instanceof Point;
	}

	public static class Entity extends EntityWireAbstract implements IWireRail
	{

		public Entity(IPoint begin, IPoint end)
		{
			super(begin, end);
		}

		public void move()
		{

		}

		@Override
		public void render(Graphics2D graphics)
		{
			INSTANCE.render(graphics, getBegin(), getEnd());
		}

		@Override
		public boolean isSettableBegin(IPoint point)
		{
			return (!point.equals(getEnd())) && (point instanceof IFurnitureStation || point instanceof Point);
		}

		@Override
		public boolean isSettableEnd(IPoint point)
		{
			return (!point.equals(getBegin())) && (point instanceof IFurnitureStation || point instanceof Point);
		}

	}

}
