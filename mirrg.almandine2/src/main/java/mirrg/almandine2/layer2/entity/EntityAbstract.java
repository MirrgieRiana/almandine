package mirrg.almandine2.layer2.entity;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import mirrg.almandine2.layer2.core.GameAlmandine2;

public abstract class EntityAbstract implements IEntity
{

	@XStreamOmitField
	protected GameAlmandine2 game;

	@Override
	public void onLoad(GameAlmandine2 game)
	{
		this.game = game;
	}

	//

	@XStreamOmitField
	private ArrayList<IEntity> listenersUpdatedNeighborhood = new ArrayList<>();

	private ArrayList<IEntity> getListenersUpdatedNeighborhoodImpl()
	{
		if (listenersUpdatedNeighborhood == null) {
			listenersUpdatedNeighborhood = new ArrayList<>();
		}
		return listenersUpdatedNeighborhood;
	}

	@Override
	public void hookUpdatedNeighborhood(IEntity entity)
	{
		getListenersUpdatedNeighborhoodImpl().add(entity);
	}

	@Override
	public void unhookUpdatedNeighborhood(IEntity entity)
	{
		getListenersUpdatedNeighborhoodImpl().remove(entity);
	}

	@Override
	public void onUpdatedNeighborhood(IEntity entity)
	{

	}

	public void fireUpdatedNeighborhood()
	{
		getListenersUpdatedNeighborhoodImpl().forEach(entity -> entity.onUpdatedNeighborhood(this));
	}

	public Stream<IEntity> getListenersUpdatedNeighborhood()
	{
		return getListenersUpdatedNeighborhoodImpl().stream();
	}

	//

	@XStreamOmitField
	private ArrayList<IEntity> listenersRemovedNeighborhood;

	private ArrayList<IEntity> getListenersRemovedNeighborhoodImpl()
	{
		if (listenersRemovedNeighborhood == null) {
			listenersRemovedNeighborhood = new ArrayList<>();
		}
		return listenersRemovedNeighborhood;
	}

	@Override
	public void hookRemovedNeighborhood(IEntity entity)
	{
		getListenersRemovedNeighborhoodImpl().add(entity);
	}

	@Override
	public void unhookRemovedNeighborhood(IEntity entity)
	{
		getListenersRemovedNeighborhoodImpl().remove(entity);
	}

	@Override
	public void onRemovedNeighborhood(IEntity entity)
	{

	}

	public void fireRemovedNeighborhood()
	{
		getListenersRemovedNeighborhoodImpl().forEach(entity -> entity.onRemovedNeighborhood(this));
	}

	public Stream<IEntity> getListenersRemovedNeighborhood()
	{
		return getListenersRemovedNeighborhoodImpl().stream();
	}

	//

	@XStreamOmitField
	private boolean isDead = false;

	@Override
	public boolean isDead()
	{
		return isDead;
	}

	@Override
	public void markDie()
	{
		if (!isDead) {
			isDead = true;
			fireRemovedNeighborhood();
		}
	}

}
