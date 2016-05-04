package mirrg.almandine2.layer3.entities;

import mirrg.almandine2.layer2.entity.IEntityLine;
import mirrg.almandine2.layer2.tool.IPoint;

public interface IWire extends IEntityLine
{

	public boolean isSettableBegin(IPoint point);

	public boolean isSettableEnd(IPoint point);

}
