package mirrg.almandine2.layer2.entity;

import java.awt.Shape;

import mirrg.almandine2.layer2.tool.IPoint;

public interface IHandle
{

	public IEntity getEntity();

	public Shape getShape();

	public boolean isSettable(IPoint point);

	public void set(IPoint point);

}
