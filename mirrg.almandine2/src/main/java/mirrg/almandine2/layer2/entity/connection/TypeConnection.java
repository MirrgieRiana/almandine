package mirrg.almandine2.layer2.entity.connection;

public class TypeConnection
{

	/**
	 * 世界座標での1点に関連付けられる接続。
	 */
	public static final TypeConnection point = new TypeConnection("point");

	/**
	 * 特定のブロックに関連付けられる接続。
	 */
	public static final TypeConnection block = new TypeConnection("block");

	/**
	 * 特定のワイヤー及びワイヤー上の位置と向きによる接続。
	 */
	public static final TypeConnection traffic = new TypeConnection("traffic");

	/**
	 * 特定のブロック・前に居たワイヤー・待機順序によって関連付けられる接続。
	 */
	public static final TypeConnection anchor = new TypeConnection("anchor");

	public final String name;

	public TypeConnection(String name)
	{
		this.name = name;
	}

}
