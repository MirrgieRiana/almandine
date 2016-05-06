package mirrg.almandine2.layer3.entities.redstone;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Optional;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer2.entity.view.ViewSurfaceCircle;
import mirrg.almandine2.layer3.entities.HRender;

public class CardEntityGateRedstone extends CardEntityBlock<EntityGateRedstone>
{

	public static CardEntityGateRedstone AND = new CardEntityGateRedstone(TypeGateRedstone.AND);
	public static CardEntityGateRedstone OR = new CardEntityGateRedstone(TypeGateRedstone.OR);
	public static CardEntityGateRedstone NAND = new CardEntityGateRedstone(TypeGateRedstone.NAND);
	public static CardEntityGateRedstone NOR = new CardEntityGateRedstone(TypeGateRedstone.NOR);
	public static CardEntityGateRedstone XOR = new CardEntityGateRedstone(TypeGateRedstone.XOR);

	private TypeGateRedstone type;

	public CardEntityGateRedstone(TypeGateRedstone type)
	{
		this.type = type;
	}

	@Override
	public boolean isConnectable(Connection connection)
	{
		return true;
	}

	@Override
	public View<EntityGateRedstone> getView()
	{
		return new ViewSurfaceCircle<EntityGateRedstone>() {

			@Override
			public Point2D.Double getPoint(EntityGateRedstone entity)
			{
				return entity.getPoint();
			}

			@Override
			public double getRadius(EntityGateRedstone entity)
			{
				return 5;
			}

			@Override
			public void render(EntityGateRedstone entity, Graphics2D graphics)
			{
				graphics.setColor(entity.value ? Color.decode("#ff0000") : Color.decode("#440000"));
				graphics.fill(new Ellipse2D.Double(
					entity.getConnection().getPoint().x - 5,
					entity.getConnection().getPoint().y - 5,
					10,
					10));

				graphics.setColor(Color.black);
				graphics.setFont(HRender.getFontDefault());
				HRender.drawString(graphics,
					entity.type.toString(),
					entity.getConnection().getPoint().x,
					entity.getConnection().getPoint().y + 5 + 3,
					HRender.ALIGN_CENTER,
					HRender.ALIGN_TOP);
			}

		};
	}

	@Override
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.point);
	}

	@Override
	public Optional<EntityGateRedstone> create(Connection connection)
	{
		return Optional.of(new EntityGateRedstone(connection, type));
	}

}
