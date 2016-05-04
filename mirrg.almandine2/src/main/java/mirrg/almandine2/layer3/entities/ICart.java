package mirrg.almandine2.layer3.entities;

import mirrg.almandine2.layer2.entity.IEntity;
import mirrg.almandine2.layer2.tool.IPoint;
import mirrg.almandine2.layer3.entities.cart.IPosition;

public interface ICart extends IEntity
{

	public boolean isSettable(IWire wire, double position, boolean reverse);

	public boolean isSettable(IPoint point);

	public IPosition getPosition();

	public void setPosition(IPosition position);

}
