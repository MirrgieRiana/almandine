package mirrg.almandine2.layer3.entities;

import mirrg.almandine2.layer2.entity.IEntitySurface;
import mirrg.almandine2.layer2.tool.Point;

public interface IFurniture extends IEntitySurface
{

	public boolean isSettable(Point point);

}
