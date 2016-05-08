package mirrg.almandine2.layer2.entity;

import java.util.function.Function;

import mirrg.almandine2.layer2.entity.view.View;

public abstract class CardEntity<E extends Entity<E, V>, V extends View>
{

	private Function<E, V> functionView;

	public CardEntity(Function<E, V> functionView)
	{
		this.functionView = functionView;
	}

	public V getView(E entity)
	{
		return functionView.apply(entity);
	}

}
