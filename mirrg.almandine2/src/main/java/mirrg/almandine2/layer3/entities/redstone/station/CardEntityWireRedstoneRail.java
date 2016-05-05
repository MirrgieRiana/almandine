package mirrg.almandine2.layer3.entities.redstone.station;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer2.entity.view.ViewWire;
import mirrg.almandine2.layer3.entities.HRender;
import mirrg.almandine2.layer3.entities.redstone.IBlockRedstone;
import mirrg.almandine2.layer3.entities.station.IRail;

public class CardEntityWireRedstoneRail extends CardEntityWire<EntityWireRedstoneRail>
{

	public static final CardEntityWireRedstoneRail INSTANCE = new CardEntityWireRedstoneRail();

	@Override
	public Stream<TypeConnection> getConnectionTypesBegin()
	{
		return Stream.of(TypeConnection.traffic, TypeConnection.point);
	}

	@Override
	public boolean isConnectableBegin(Connection connection)
	{
		return connection instanceof ConnectionPoint
			|| (connection instanceof ConnectionTraffic
				&& ((ConnectionTraffic) connection).entity instanceof IRail);
	}

	@Override
	public Stream<TypeConnection> getConnectionTypesEnd()
	{
		return Stream.of(TypeConnection.block, TypeConnection.traffic, TypeConnection.point);
	}

	@Override
	public boolean isConnectableEnd(Connection connection)
	{
		return connection instanceof ConnectionPoint
			|| (connection instanceof ConnectionBlock
				&& ((ConnectionBlock) connection).entity instanceof IBlockRedstone);
	}

	@Override
	public EntityWireRedstoneRail create(Connection begin, Connection end)
	{
		return new EntityWireRedstoneRail(begin, end);
	}

	@Override
	public View<EntityWireRedstoneRail> getView()
	{
		return new ViewWire<EntityWireRedstoneRail>() {

			@Override
			public Double getPointBegin(EntityWireRedstoneRail entity)
			{
				return entity.getBegin().getPoint();
			}

			@Override
			public Double getPointEnd(EntityWireRedstoneRail entity)
			{
				return entity.getEnd().getPoint();
			}

			@Override
			public double getWidth(EntityWireRedstoneRail entity)
			{
				return 3;
			}

			@Override
			public void render(EntityWireRedstoneRail entity, Graphics2D graphics)
			{
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

				{
					graphics.setColor(Color.decode("#434F2A"));
					graphics.fill(new Ellipse2D.Double(
						entity.getBegin().getPoint().x - 5,
						entity.getBegin().getPoint().y - 5,
						10,
						10));
				}
			}

		};
	}

}
