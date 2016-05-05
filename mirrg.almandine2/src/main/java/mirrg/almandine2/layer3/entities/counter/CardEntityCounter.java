package mirrg.almandine2.layer3.entities.counter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer2.entity.view.ViewSurfaceRectangle;
import mirrg.almandine2.layer3.entities.HRender;

public class CardEntityCounter extends CardEntityBlock<EntityCounter>
{

	public static final CardEntityCounter INSTANCE = new CardEntityCounter();

	@Override
	public boolean isConnectable(Connection connection)
	{
		return connection instanceof ConnectionPoint;
	}

	@Override
	public View<EntityCounter> getView()
	{
		return new ViewSurfaceRectangle<EntityCounter>() {

			@Override
			public Point2D.Double getPoint(EntityCounter entity)
			{
				return entity.getPoint();
			}

			@Override
			public double getWidth(EntityCounter entity)
			{
				return 64;
			}

			@Override
			public double getHeight(EntityCounter entity)
			{
				return 20;
			}

			@Override
			public void render(EntityCounter entity, Graphics2D graphics)
			{
				graphics.setColor(Color.black);
				graphics.setFont(HRender.getFontDefault());
				HRender.drawString(graphics, "" + entity.i, entity.getPoint().x, entity.getPoint().y, HRender.ALIGN_CENTER, HRender.ALIGN_MIDDLE);
			}

		};
	}

	@Override
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.point);
	}

	@Override
	public EntityCounter create(Connection connection)
	{
		return new EntityCounter(connection);
	}

}
