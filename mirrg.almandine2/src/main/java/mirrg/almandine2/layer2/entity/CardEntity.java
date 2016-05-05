package mirrg.almandine2.layer2.entity;

import mirrg.almandine2.layer2.entity.view.View;

public abstract class CardEntity<E extends Entity>
{

	public abstract View<E> getView();

	public abstract E create();

}
