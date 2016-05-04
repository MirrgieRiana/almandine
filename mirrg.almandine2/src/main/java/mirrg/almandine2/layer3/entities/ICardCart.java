package mirrg.almandine2.layer3.entities;

import java.awt.Graphics2D;

import mirrg.almandine2.layer2.tool.IPoint;

public interface ICardCart
{

	public ICart create(IWire wire, double position, boolean reverse);

	public void render(Graphics2D graphics, IWire wire, double position, boolean reverse);

	public boolean isSettable(IWire wire, double position, boolean reverse);

	public boolean isSettable(IPoint point);

}
