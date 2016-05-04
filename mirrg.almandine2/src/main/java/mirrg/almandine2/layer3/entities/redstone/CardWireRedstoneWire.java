package mirrg.almandine2.layer3.entities.redstone;

import java.awt.Color;
import java.awt.Graphics2D;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.tool.IPoint;
import mirrg.almandine2.layer2.tool.Point;
import mirrg.almandine2.layer3.entities.EntityWireAbstract;
import mirrg.almandine2.layer3.entities.HRender;
import mirrg.almandine2.layer3.entities.ICardWire;
import mirrg.almandine2.layer3.entities.IWire;

public class CardWireRedstoneWire implements ICardWire
{

	public static CardWireRedstoneWire INSTANCE = new CardWireRedstoneWire();

	@Override
	public IWire create(IPoint begin, IPoint end)
	{
		return new Entity(begin, end);
	}

	@Override
	public void render(Graphics2D graphics, IPoint begin, IPoint end)
	{
		render(graphics, begin, end, true);
	}

	public void render(Graphics2D graphics, IPoint begin, IPoint end, boolean value)
	{
		double marginBegin = 0;
		if (begin instanceof IFurnitureRedstone) {
			marginBegin = ((IFurnitureRedstone) begin).getRadiusRedstone() + 1;
		}

		double marginEnd = 0;
		if (end instanceof IFurnitureRedstone) {
			marginEnd = ((IFurnitureRedstone) end).getRadiusRedstone() + 1;
		}

		Point[] points = HRender.getPointsMargined(begin, end, marginBegin, marginEnd);

		graphics.setColor(value ? Color.decode("#ff0000") : Color.decode("#440000"));
		HRender.drawArrow(graphics, points[0], points[1], 8, 30);
	}

	@Override
	public boolean isSettableBegin(IPoint point)
	{
		return point instanceof IFurnitureRedstone || point instanceof Point;
	}

	@Override
	public boolean isSettableEnd(IPoint point)
	{
		return point instanceof IFurnitureRedstone || point instanceof Point;
	}

	public static class Entity extends EntityWireAbstract implements IWireRedstone
	{

		private boolean value;

		public Entity(IPoint begin, IPoint end)
		{
			super(begin, end);
		}

		public void move()
		{
			boolean value = getBegin() instanceof IFurnitureRedstone ? ((IFurnitureRedstone) getBegin()).getValueRedstone() : false;

			game.nitrogen.registerRemovable(GameAlmandine2.NitrogenEventPostMove.class, event -> {

				this.value = value;

				return false;
			});
		}

		@Override
		public void render(Graphics2D graphics)
		{
			INSTANCE.render(graphics, getBegin(), getEnd(), value);
		}

		@Override
		public boolean getValueRedstone()
		{
			return value;
		}

		@Override
		public boolean isSettableBegin(IPoint point)
		{
			return (!point.equals(getEnd())) && (point instanceof IFurnitureRedstone || point instanceof Point);
		}

		@Override
		public boolean isSettableEnd(IPoint point)
		{
			return (!point.equals(getBegin())) && (point instanceof IFurnitureRedstone || point instanceof Point);
		}

	}

}
