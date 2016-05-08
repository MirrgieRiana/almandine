package mirrg.almandine2.layer3.entities.slab;

public interface ISlot
{

	public int getAmount();

	public void push(int amount);

	public void pop(int amount);

	public int getAmountMax();

}
