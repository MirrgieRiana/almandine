package mirrg.almandine2.layer2.entity;

import java.util.HashSet;

public interface IHandle
{

	public Entity getOwner();

	public View<IHandle> getView();

	public HashSet<TypeConnection> getConnectionTypes();

	public boolean isConnectable(Connection connection);

	public void set(Connection connection);

}
