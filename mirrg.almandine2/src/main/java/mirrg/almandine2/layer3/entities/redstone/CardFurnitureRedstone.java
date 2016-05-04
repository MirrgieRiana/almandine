package mirrg.almandine2.layer3.entities.redstone;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import mirrg.almandine2.layer2.tool.IPoint;
import mirrg.almandine2.layer2.tool.Point;
import mirrg.almandine2.layer3.entities.HRender;
import mirrg.almandine2.layer3.entities.ICardFurniture;
import mirrg.almandine2.layer3.entities.IFurniture;
import mirrg.almandine2.layer3.entities.redstone.EntityFurnitureRedstoneGate.TypeGate;

public class CardFurnitureRedstone implements ICardFurniture
{

	public static CardFurnitureRedstone AND = new CardFurnitureRedstone(TypeGate.AND);
	public static CardFurnitureRedstone OR = new CardFurnitureRedstone(TypeGate.OR);
	public static CardFurnitureRedstone NAND = new CardFurnitureRedstone(TypeGate.NAND);
	public static CardFurnitureRedstone NOR = new CardFurnitureRedstone(TypeGate.NOR);
	public static CardFurnitureRedstone XOR = new CardFurnitureRedstone(TypeGate.XOR);

	private TypeGate type;

	public CardFurnitureRedstone(TypeGate type)
	{
		this.type = type;
	}

	@Override
	public IFurniture create(Point point)
	{
		return new EntityFurnitureRedstoneGate(point, type);
	}

	@Override
	public void render(Graphics2D graphics, Point point)
	{
		render(graphics, point, type, true);
	}

	public static void render(Graphics2D graphics, IPoint point, TypeGate type, boolean value)
	{
		graphics.setColor(value ? Color.decode("#ff0000") : Color.decode("#440000"));
		graphics.fill(new Ellipse2D.Double(point.getX() - 5, point.getY() - 5, 10, 10));

		graphics.setColor(Color.black);
		graphics.setFont(HRender.getFontDefault());
		HRender.drawString(graphics, type.toString(), point.getX(), point.getY() + 5 + 3, HRender.ALIGN_CENTER, HRender.ALIGN_TOP);
	}

	@Override
	public boolean isSettable(Point point)
	{
		return true;
	}

}
