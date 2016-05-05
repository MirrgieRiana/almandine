package mirrg.almandine2.layer2.entity;

import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.almandine2.layer2.entity.view.View;

public interface IHandle
{

	public Entity getOwner();

	public View<IHandle> getView();

	public Stream<TypeConnection> getConnectionTypes();

	public boolean isConnectable(Connection connection);

	public void set(Connection connection);

}
