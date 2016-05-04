package mirrg.almandine2.layer3.entities.cart;

import java.awt.Graphics2D;
import java.util.Optional;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.EntityAbstract;
import mirrg.almandine2.layer2.entity.IEntity;
import mirrg.almandine2.layer2.entity.IEntityCircle;
import mirrg.almandine2.layer2.entity.IHandle;
import mirrg.almandine2.layer2.tool.IPoint;
import mirrg.almandine2.layer2.tool.Point;
import mirrg.almandine2.layer3.entities.ICart;
import mirrg.almandine2.layer3.entities.IWire;
import mirrg.almandine2.layer3.entities.cart.station.IFurnitureStation;
import mirrg.almandine2.layer3.entities.cart.station.IWireRail;

public class EntityCartAbstract extends EntityAbstract implements ICart, IEntityCircle
{

	private IPosition position;

	public EntityCartAbstract(IPosition position)
	{
		this.position = position;
	}

	public void setPosition(IPosition position)
	{
		if (this.position instanceof PositionRail) {
			((PositionRail) this.position).rail.unhookRemovedNeighborhood(this);
		} else if (this.position instanceof PositionStation) {
			((PositionStation) this.position).station.unhookRemovedNeighborhood(this);
			((PositionStation) this.position).railPrevious.unhookRemovedNeighborhood(this);
		}
		this.position = position;
		if (this.position instanceof PositionRail) {
			((PositionRail) this.position).rail.hookRemovedNeighborhood(this);
		} else if (this.position instanceof PositionStation) {
			((PositionStation) this.position).station.hookRemovedNeighborhood(this);
			((PositionStation) this.position).railPrevious.hookRemovedNeighborhood(this);
		}
	}

	public IPosition getPosition()
	{
		return position;
	}

	@Override
	public void onLoad(GameAlmandine2 game)
	{
		super.onLoad(game);

		if (this.position instanceof PositionRail) {
			((PositionRail) this.position).rail.unhookRemovedNeighborhood(this);
		} else if (this.position instanceof PositionStation) {
			((PositionStation) this.position).station.unhookRemovedNeighborhood(this);
			((PositionStation) this.position).railPrevious.unhookRemovedNeighborhood(this);
		}
	}

	@Override
	public void onRemovedNeighborhood(IEntity entity)
	{
		super.onRemovedNeighborhood(entity);

		if (this.position instanceof PositionRail) {
			if (((PositionRail) this.position).rail.equals(entity)) {
				markDie();
			}
		} else if (this.position instanceof PositionStation) {
			if (((PositionStation) this.position).station.equals(entity)) {
				markDie();
			}
			if (((PositionStation) this.position).railPrevious.equals(entity)) {
				((PositionStation) this.position).railPrevious = null;
			}
		}
	}

	@Override
	public Stream<IHandle> getHandles()
	{
		return Stream.of();
	}

	@Override
	public void move()
	{
		if (position instanceof PositionRail) {
			PositionRail positionRail = (PositionRail) position;

			if (!positionRail.reverse) {
				positionRail.position += 0.005;
				if (positionRail.position > 1) {

					if (positionRail.rail.getEnd() instanceof IFurnitureStation) {
						IFurnitureStation station = (IFurnitureStation) positionRail.rail.getEnd();
						setPosition(new PositionStation(station, station.getFreeOrder(), Optional.of(positionRail.rail)));
					} else {
						positionRail.reverse = true;
						positionRail.position = 1 - (positionRail.position - 1);
					}

				}
			} else {
				positionRail.position -= 0.005;
				if (positionRail.position < 0) {

					if (positionRail.rail.getBegin() instanceof IFurnitureStation) {
						IFurnitureStation station = (IFurnitureStation) positionRail.rail.getBegin();
						setPosition(new PositionStation(station, station.getFreeOrder(), Optional.of(positionRail.rail)));
					} else {
						positionRail.reverse = false;
						positionRail.position = -positionRail.position;
					}

				}
			}

		}
	}

	@Override
	public void render(Graphics2D graphics)
	{
		CardCart.renderImpl(graphics, position);
	}

	@Override
	public boolean isSettable(IWire wire, double position, boolean reverse)
	{
		return wire instanceof IWireRail;
	}

	@Override
	public boolean isSettable(IPoint point)
	{
		return point instanceof IFurnitureStation || point instanceof Point;
	}

	@Override
	public double getX()
	{
		return CardCart.getCenter(position).x;
	}

	@Override
	public double getY()
	{
		return CardCart.getCenter(position).y;
	}

	@Override
	public double getRadius()
	{
		return 10;
	}

}
