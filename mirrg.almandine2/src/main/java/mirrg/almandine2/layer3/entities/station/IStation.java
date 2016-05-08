package mirrg.almandine2.layer3.entities.station;

import mirrg.almandine2.layer2.entity.EntityBlock;

public interface IStation
{

	public EntityBlock<?, ?> getEntity();

	public double getRadiusStation(double angle);

	public int getFreeOrder();

}
