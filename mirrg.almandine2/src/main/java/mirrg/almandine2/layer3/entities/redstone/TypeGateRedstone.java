package mirrg.almandine2.layer3.entities.redstone;

public enum TypeGateRedstone
{
	AND("AND"),
	OR(""),
	NAND("NAND"),
	NOR("NOT"),
	XOR("XOR"),

	;

	private String label;

	private TypeGateRedstone(String label)
	{
		this.label = label;
	}

	@Override
	public String toString()
	{
		return label;
	}

}
