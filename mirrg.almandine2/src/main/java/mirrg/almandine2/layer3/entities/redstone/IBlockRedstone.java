package mirrg.almandine2.layer3.entities.redstone;

import mirrg.almandine2.layer2.entity.EntityBlock;

public interface IBlockRedstone
{

	public EntityBlock getEntity();

	public double getRadiusRedstone(double angle);

	public boolean getValueRedstone();

}
