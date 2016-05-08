package mirrg.almandine2.layer2.entity;

import java.util.function.Supplier;

import mirrg.almandine2.layer2.entity.view.View;

public abstract class CardEntity<E extends Entity>
{

	private Supplier<View<E>> supplierView;

	public CardEntity(Supplier<View<E>> supplierView)
	{
		this.supplierView = supplierView;
	}

	public View<E> getView()
	{
		return supplierView.get();
	}

}
