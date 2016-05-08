package mirrg.almandine2.layer3.entities.slab;

public enum TypeStationSlot
{
	NORMAL(""),
	LOAD("Load"),
	UNLOAD("Unload"),

	;

	private final String label;

	private TypeStationSlot(String label)
	{
		this.label = label;
	}

	@Override
	public String toString()
	{
		return label;
	}

}
