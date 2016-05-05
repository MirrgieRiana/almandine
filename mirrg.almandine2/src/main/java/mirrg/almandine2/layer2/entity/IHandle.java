package mirrg.almandine2.layer2.entity;

import java.util.stream.Stream;

public interface IHandle
{

	public Entity getOwner();

	public View<IHandle> getView();

	public Stream<TypeConnection> getConnectionTypes();

	public boolean isConnectable(Connection connection);

	public void set(Connection connection);

}
