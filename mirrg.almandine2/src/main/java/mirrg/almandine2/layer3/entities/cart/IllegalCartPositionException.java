package mirrg.almandine2.layer3.entities.cart;

public class IllegalCartPositionException extends RuntimeException
{

	public IllegalCartPositionException(IPosition position)
	{
		super("Illegal cart position: " + position + " is not a PositionRail or PositionStation");
	}

}
