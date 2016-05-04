package mirrg.almandine2.layer3.entities.cart.station;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.IEntityCircle;
import mirrg.almandine2.layer2.tool.Point;
import mirrg.almandine2.layer3.entities.EntityFurnitureAbstract;
import mirrg.almandine2.layer3.entities.ICardFurniture;
import mirrg.almandine2.layer3.entities.ICart;
import mirrg.almandine2.layer3.entities.IFurniture;
import mirrg.almandine2.layer3.entities.cart.PositionRail;
import mirrg.almandine2.layer3.entities.cart.PositionStation;

public class CardFurnitureStation implements ICardFurniture
{

	public static CardFurnitureStation INSTANCE = new CardFurnitureStation();

	@Override
	public IFurniture create(Point point)
	{
		return new Entity(point);
	}

	@Override
	public void render(Graphics2D graphics, Point point)
	{
		graphics.setColor(Color.decode("#29844D"));
		Stroke stroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(2));
		{
			graphics.draw(new Ellipse2D.Double(point.getX() - 20, point.getY() - 20, 40, 40));
		}
		graphics.setStroke(stroke);
	}

	@Override
	public boolean isSettable(Point point)
	{
		return point instanceof Point;
	}

	public static class Entity extends EntityFurnitureAbstract implements IFurnitureStation, IEntityCircle
	{

		public Entity(Point point)
		{
			super(point);
		}

		public void move()
		{
			ICart[] carts = getCarts()
				.toArray(ICart[]::new);

			for (ICart cart : carts) {
				IWireRail[] rails = getRails()
					.filter(r -> !r.equals(((PositionStation) cart.getPosition()).railPrevious))
					.toArray(IWireRail[]::new);

				if (rails.length != 0) {
					leaveCart(cart, rails[(int) (Math.random() * rails.length)]);
				} else if (((PositionStation) cart.getPosition()).railPrevious != null) {
					leaveCart(cart, ((PositionStation) cart.getPosition()).railPrevious);
				}
			}
		}

		@Override
		public void render(Graphics2D graphics)
		{
			INSTANCE.render(graphics, getPoint());
		}

		@Override
		public Shape getShape()
		{
			return new Ellipse2D.Double(getX() - 20, getY() - 20, 40, 40);
		}

		@Override
		public double getRadiusStation()
		{
			return 20;
		}

		public Stream<ICart> getCarts()
		{
			return getListenersRemovedNeighborhood()
				.filter(e -> e instanceof ICart)
				.map(e -> (ICart) e);
		}

		public Stream<IWireRail> getRails()
		{
			return getListenersRemovedNeighborhood()
				.filter(e -> e instanceof IWireRail)
				.map(e -> (IWireRail) e);
		}

		@Override
		public int getFreeOrder()
		{
			return getCarts()
				.mapToInt(c -> ((PositionStation) c.getPosition()).order)
				.max()
				.orElse(-1) + 1;
		}

		public void leaveCart(ICart cart, IWireRail rail)
		{
			if (rail.getBegin().equals(this)) {
				cart.setPosition(new PositionRail(rail, 0, false));
			} else {
				cart.setPosition(new PositionRail(rail, 1, true));
			}
			updateOrder();
		}

		public void updateOrder()
		{
			ICart[] carts = getCarts()
				.sorted((a, b) -> ((PositionStation) a.getPosition()).order - ((PositionStation) b.getPosition()).order)
				.toArray(ICart[]::new);

			for (int i = 0; i < carts.length; i++) {
				((PositionStation) carts[i].getPosition()).order = i;
			}
		}

		@Override
		public boolean isSettable(Point point)
		{
			return true;
		}

		@Override
		public double getRadius()
		{
			return 20;
		}

	}

}
