package mirrg.almandine2.layer2.entity;

public abstract class CardEntity<E extends Entity>
{

	public abstract View<E> getView();

	public abstract E create();

}
