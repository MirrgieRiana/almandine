package mirrg.almandine2.layer2.entity;

import java.awt.geom.Point2D;

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
		entity.hook(this);
	}

	@Override
	public void disable()
	{
		super.disable();
		entity.unhook(this);
	}

	@Override
	public Point2D.Double getPoint()
	{
		return entity.getPoint(position);
	}

}
