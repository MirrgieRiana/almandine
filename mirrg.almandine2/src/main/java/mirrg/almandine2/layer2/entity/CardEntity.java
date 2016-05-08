package mirrg.almandine2.layer2.entity;

import java.util.function.Supplier;

import mirrg.almandine2.layer2.entity.view.View;

public abstract class CardEntity<E extends Entity, V extends View<E>>
{

	private Supplier<V> supplierView;

	public CardEntity(Supplier<V> supplierView)
	{
		this.supplierView = supplierView;
	}

	public V getView()
	{
		return supplierView.get();
	}

}
