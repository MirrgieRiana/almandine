package mirrg.almandine2.layer3.entities.station;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionAnchor;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.struct.hydrogen.Tuple;

public class EntityStation extends EntityBlock implements IStation
{

	public EntityStation(Connection connection)
	{
		super(connection);
	}

	@Override
	public void move()
	{
		getCarts()
			.collect(Collectors.toList())
			.forEach(t -> {
				IRail[] rails = getRails()
					.filter(r -> r != t.getY().previous)
					.toArray(IRail[]::new);

				if (rails.length > 0) {
					leaveCart(t.getX(), rails[(int) (Math.random() * rails.length)]);
				} else if (t.getY().previous instanceof IRail) {
					leaveCart(t.getX(), (IRail) t.getY().previous);
				}

			});
	}

	@Override
	public CardEntityBlock<?> getCardEntity()
	{
		return CardEntityStation.INSTANCE;
	}

	@Override
	public double getRadiusStation()
	{
		return 20;
	}

	@Override
	public int getFreeOrder()
	{
		return getCarts()
			.mapToInt(t -> t.getY().order)
			.max()
			.orElse(-1) + 1;
	}

	@Override
	public EntityBlock getEntity()
	{
		return this;
	}

	public Stream<Tuple<ICart, ConnectionAnchor>> getCarts()
	{
		return getConnectors()
			.map(c -> {
				if (c instanceof ConnectionAnchor) {
					if (c.getParent() instanceof ICart) {
						if (((ConnectionAnchor) c).entity == this) {
							return new Tuple<>((ICart) c.getParent(), (ConnectionAnchor) c);
						}
					}
				}

				return null;
			})
			.filter(t -> t != null);
	}

	public Stream<IRail> getRails()
	{
		return getConnectors()
			.filter(c -> c.getParent() instanceof IRail)
			.map(c -> (IRail) c.getParent());
	}

	public void leaveCart(ICart cart, IRail rail)
	{
		if (rail.getEntity().getBegin() instanceof ConnectionBlock) {
			if (((ConnectionBlock) rail.getEntity().getBegin()).entity == this) {

				ConnectionTraffic connection = new ConnectionTraffic(rail.getEntity(), 0, false);
				if (cart.getEntity().isConnectable(connection)) {
					cart.getEntity().setConnection(connection);
				}
				updateOrder();
				return;

			}
		}

		ConnectionTraffic connection = new ConnectionTraffic(rail.getEntity(), 1, true);
		if (cart.getEntity().isConnectable(connection)) {
			cart.getEntity().setConnection(connection);
		}
		updateOrder();
	}

	public void updateOrder()
	{
		ConnectionAnchor[] connection = getCarts()
			.sorted((a, b) -> a.getY().order - b.getY().order)
			.map(t -> t.getY())
			.toArray(ConnectionAnchor[]::new);

		for (int i = 0; i < connection.length; i++) {
			connection[i].order = i;
		}
	}

}
