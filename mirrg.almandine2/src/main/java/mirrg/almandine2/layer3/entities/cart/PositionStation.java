package mirrg.almandine2.layer3.entities.cart;

import java.util.Optional;

import mirrg.almandine2.layer3.entities.cart.station.IFurnitureStation;
import mirrg.almandine2.layer3.entities.cart.station.IWireRail;

public class PositionStation implements IPosition
{

	public IFurnitureStation station;
	public int order;

	// Nullable
	public IWireRail railPrevious;

	public PositionStation(IFurnitureStation station, int order, Optional<IWireRail> railPrevious)
	{
		this.station = station;
		this.order = order;
		this.railPrevious = railPrevious.orElse(null);
	}

}
