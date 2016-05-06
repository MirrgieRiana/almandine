package mirrg.almandine2.layer3.entities.station;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.Optional;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;
import mirrg.almandine2.layer2.entity.view.ViewSurfaceCircle;

public class CardEntityStation extends CardEntityBlock<EntityStation>
{

	public static final CardEntityStation INSTANCE = new CardEntityStation();

	@Override
	public boolean isConnectable(Connection connection)
	{
		return connection instanceof ConnectionPoint;
	}

	@Override
	public View<EntityStation> getView()
	{
		return new ViewSurfaceCircle<EntityStation>() {

			@Override
			public Point2D.Double getPoint(EntityStation entity)
			{
				return entity.getPoint();
			}

			@Override
			public double getRadius(EntityStation entity)
			{
				return 20;
			}

			@Override
			public void render(EntityStation entity, Graphics2D graphics)
			{
				graphics.setColor(Color.decode("#29844D"));
				Stroke stroke = graphics.getStroke();
				graphics.setStroke(new BasicStroke(3));
				{
					graphics.draw(getShape(entity, 0));
				}
				graphics.setStroke(stroke);
			}

		};
	}

	@Override
	public Stream<TypeConnection> getConnectionTypes()
	{
		return Stream.of(TypeConnection.point);
	}

	@Override
	public Optional<EntityStation> create(Connection connection)
	{
		return Optional.of(new EntityStation(connection));
	}

}
