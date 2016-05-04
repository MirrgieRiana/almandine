package mirrg.almandine2.layer3.entities;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.EntityAbstract;
import mirrg.almandine2.layer2.entity.IEntity;
import mirrg.almandine2.layer2.entity.IHandle;
import mirrg.almandine2.layer2.tool.IPoint;
import mirrg.almandine2.layer2.tool.Point;

public abstract class EntityFurnitureAbstract extends EntityAbstract implements IFurniture
{

	private Point point;

	public EntityFurnitureAbstract(Point point)
	{
		this.point = point;
	}

	public Point getPoint()
	{
		return point;
	}

	@Override
	public double getX()
	{
		return point.getX();
	}

	@Override
	public double getY()
	{
		return point.getY();
	}

	public abstract Shape getShape();

	@Override
	public Stream<IHandle> getHandles()
	{
		return Stream.of(new IHandle() {

			@Override
			public IEntity getEntity()
			{
				return EntityFurnitureAbstract.this;
			}

			@Override
			public Shape getShape()
			{
				return new Ellipse2D.Double(getX() - 3, getY() - 3, 6, 6);
			}

			@Override
			public boolean isSettable(IPoint point)
			{
				return point instanceof Point;
			}

			@Override
			public void set(IPoint point)
			{
				EntityFurnitureAbstract.this.point = (Point) point;
			}

		});
	}

}
