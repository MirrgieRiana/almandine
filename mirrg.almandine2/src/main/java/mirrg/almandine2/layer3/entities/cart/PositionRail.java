package mirrg.almandine2.layer3.entities.cart;

import mirrg.almandine2.layer3.entities.cart.station.IWireRail;

public class PositionRail implements IPosition
{

	public IWireRail rail;
	public double position;
	public boolean reverse;

	public PositionRail(IWireRail rail, double position, boolean reverse)
	{
		this.rail = rail;
		this.position = position;
		this.reverse = reverse;
	}

}
