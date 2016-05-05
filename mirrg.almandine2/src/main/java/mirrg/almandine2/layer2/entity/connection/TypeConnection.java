package mirrg.almandine2.layer2.entity.connection;

public class TypeConnection
{

	public static final TypeConnection point = new TypeConnection("point");
	public static final TypeConnection block = new TypeConnection("block");
	public static final TypeConnection traffic = new TypeConnection("traffic");

	public final String name;

	public TypeConnection(String name)
	{
		this.name = name;
	}

}
