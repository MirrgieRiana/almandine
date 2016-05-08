package mirrg.almandine2.layer2.entity.connection;

import java.awt.geom.Point2D;
import java.util.stream.Stream;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;

public class ConnectionBlock extends Connection
{

	public EntityBlock<?, ?> entity;

	public ConnectionBlock(EntityBlock<?, ?> entity)
	{
		this.entity = entity;
	}

	@Override
	public void enable(Entity<?, ?> parent)
	{
		super.enable(parent);
		entity.connect(this);
		calculating = false;
	}

	@Override
	public void disable()
	{
		super.disable();
		entity.unconnect(this);
	}

	@XStreamOmitField
	private boolean calculating;

	@Override
	public Point2D.Double getPoint()
	{
		if (calculating) {
			return new Point2D.Double(0, 0);
		} else {
			calculating = true;
			Point2D.Double point = entity.getPoint();
			calculating = false;
			return point;
		}
	}

	@Override
	public Stream<Entity<?, ?>> getEntities()
	{
		return Stream.of(entity);
	}

}
