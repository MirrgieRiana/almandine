package mirrg.almandine2.layer3.entities.redstone;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer2.entity.view.ViewWire;
import mirrg.almandine2.layer3.entities.HRender;

public class CardEntityWireRedstone extends CardEntityWire<EntityWireRedstone>
{

	public static final CardEntityWireRedstone INSTANCE = new CardEntityWireRedstone();

	@Override
	public Stream<TypeConnection> getConnectionTypesBegin()
	{
		return Stream.of(TypeConnection.block, TypeConnection.point);
	}

	@Override
	public boolean isConnectableBegin(Connection connection)
	{
		return connection instanceof ConnectionPoint
			|| (connection instanceof ConnectionBlock
				&& ((ConnectionBlock) connection).entity instanceof IBlockRedstone);
	}

	@Override
	public Stream<TypeConnection> getConnectionTypesEnd()
	{
		return Stream.of(TypeConnection.block, TypeConnection.point);
	}

	@Override
	public boolean isConnectableEnd(Connection connection)
	{
		return connection instanceof ConnectionPoint
			|| (connection instanceof ConnectionBlock
				&& ((ConnectionBlock) connection).entity instanceof IBlockRedstone);
	}

	@Override
	public EntityWireRedstone create(Connection begin, Connection end)
	{
		return new EntityWireRedstone(begin, end);
	}

	@Override
	public View<EntityWireRedstone> getView()
	{
		return new ViewWire<EntityWireRedstone>() {

			@Override
			public Double getPointBegin(EntityWireRedstone entity)
			{
				return entity.getBegin().getPoint();
			}

			@Override
			public Double getPointEnd(EntityWireRedstone entity)
			{
				return entity.getEnd().getPoint();
			}

			@Override
			public double getWidth(EntityWireRedstone entity)
			{
				return 3;
			}

			@Override
			public void render(EntityWireRedstone entity, Graphics2D graphics)
			{
				double marginBegin = 0;
				if (entity.getBegin() instanceof ConnectionBlock) {
					marginBegin = ((IBlockRedstone) ((ConnectionBlock) entity.getBegin()).entity).getRadiusRedstone() + 1;
				}

				double marginEnd = 0;
				if (entity.getEnd() instanceof ConnectionBlock) {
					marginEnd = ((IBlockRedstone) ((ConnectionBlock) entity.getEnd()).entity).getRadiusRedstone() + 1;
				}

				Point2D.Double[] points = HRender.getPointsMargined(entity.getBegin().getPoint(), entity.getEnd().getPoint(), marginBegin, marginEnd);

				graphics.setColor(entity.value ? Color.decode("#ff0000") : Color.decode("#440000"));
				HRender.drawArrow(graphics, points[0], points[1], 8, 30);
			}

		};
	}

}
