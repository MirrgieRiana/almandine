package mirrg.almandine2.layer3.entities;

import java.awt.Graphics2D;

import mirrg.almandine2.layer2.tool.IPoint;

public interface ICardWire
{

	public IWire create(IPoint a, IPoint b);

	public void render(Graphics2D graphics, IPoint a, IPoint b);

	public boolean isSettableBegin(IPoint point);

	public boolean isSettableEnd(IPoint point);

}
