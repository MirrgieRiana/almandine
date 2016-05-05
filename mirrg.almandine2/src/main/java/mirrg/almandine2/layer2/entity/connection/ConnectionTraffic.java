package mirrg.almandine2.layer2.entity.connection;

import java.awt.geom.Point2D;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityWire;

public class ConnectionTraffic extends Connection
{

	public EntityWire entity;
	public double position;
	public boolean reverse;

	public ConnectionTraffic(EntityWire entity, double position, boolean reverse)
	{
		this.entity = entity;
		this.position = position;
		this.reverse = reverse;
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
		return entity.getPoint(position);
	}

	@Override
	public Stream<Entity> getEntities()
	{
		return Stream.of(entity);
	}

}
