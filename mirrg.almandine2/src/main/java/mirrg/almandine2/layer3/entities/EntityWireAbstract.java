package mirrg.almandine2.layer3.entities;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.EntityAbstract;
import mirrg.almandine2.layer2.entity.IEntity;
import mirrg.almandine2.layer2.entity.IHandle;
import mirrg.almandine2.layer2.tool.IPoint;
import mirrg.almandine2.layer2.tool.Point;

public abstract class EntityWireAbstract extends EntityAbstract implements IWire
{

	private IPoint begin;
	private IPoint end;

	public EntityWireAbstract(IPoint begin, IPoint end)
	{
		this.begin = begin;
		this.end = end;
	}

	@Override
	public IPoint getBegin()
	{
		return begin;
	}

	@Override
	public IPoint getEnd()
	{
		return end;
	}

	public void setBegin(IPoint begin)
	{
		if (this.begin instanceof IEntity) {
			((IEntity) this.begin).unhookRemovedNeighborhood(this);
		}
		this.begin = begin;
		if (this.begin instanceof IEntity) {
			((IEntity) this.begin).hookRemovedNeighborhood(this);
		}
	}

	public void setEnd(IPoint end)
	{
		if (this.end instanceof IEntity) {
			((IEntity) this.end).unhookRemovedNeighborhood(this);
		}
		this.end = end;
		if (this.end instanceof IEntity) {
			((IEntity) this.end).hookRemovedNeighborhood(this);
		}
	}

	@Override
	public void onLoad(GameAlmandine2 game)
	{
		super.onLoad(game);

		if (this.begin instanceof IEntity) {
			((IEntity) this.begin).hookRemovedNeighborhood(this);
		}
		if (this.end instanceof IEntity) {
			((IEntity) this.end).hookRemovedNeighborhood(this);
		}
	}

	@Override
	public void onRemovedNeighborhood(IEntity entity)
	{
		super.onRemovedNeighborhood(entity);

		if (entity.equals(begin)) {
			begin = new Point(begin.getX(), begin.getY());
		}
		if (entity.equals(end)) {
			end = new Point(end.getX(), end.getY());
		}
	}

	@Override
	public Stream<IHandle> getHandles()
	{
		return Stream.of(new IHandle() {

			@Override
			public IEntity getOwner()
			{
				return EntityWireAbstract.this;
			}

			@Override
			public Shape getShape()
			{
				double angle = getAngle();
				return new Ellipse2D.Double(
					begin.getX() + 10 * Math.cos(angle) - 3,
					begin.getY() + 10 * Math.sin(angle) - 3,
					6, 6);
			}

			@Override
			public boolean isConnectable(IPoint point)
			{
				return isSettableBegin(point);
			}

			@Override
			public void set(IPoint point)
			{
				setBegin(point);
			}

		}, new IHandle() {

			@Override
			public IEntity getOwner()
			{
				return EntityWireAbstract.this;
			}

			@Override
			public Shape getShape()
			{
				double angle = getAngle();
				return new Ellipse2D.Double(
					end.getX() - 10 * Math.cos(angle) - 3,
					end.getY() - 10 * Math.sin(angle) - 3,
					6, 6);
			}

			@Override
			public boolean isConnectable(IPoint point)
			{
				return isSettableEnd(point);
			}

			@Override
			public void set(IPoint point)
			{
				setEnd(point);
			}

		});
	}

}
