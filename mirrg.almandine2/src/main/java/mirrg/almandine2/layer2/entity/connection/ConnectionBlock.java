package mirrg.almandine2.layer2.entity.connection;

import java.awt.geom.Point2D;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;

public class ConnectionBlock extends Connection
{

	public EntityBlock entity;

	public ConnectionBlock(EntityBlock entity)
	{
		this.entity = entity;
	}

	@Override
	public void enable(Entity parent)
	{
		super.enable(parent);
		entity.connect(this);
	}

	@Override
	public void disable()
	{
		super.disable();
		entity.unconnect(this);
	}

	@Override
	public Point2D.Double getPoint()
	{
		return entity.getPoint();
	}

	@Override
	public Stream<Entity> getEntities()
	{
		return Stream.of(entity);
	}

}
