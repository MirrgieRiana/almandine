package mirrg.almandine2.layer3.entities.station;

import java.util.Optional;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.entity.CardEntity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionAnchor;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.struct.hydrogen.Tuple;

public class EntityStation<E extends EntityStation<E, V>, V extends ViewEntityStation<E, V>> extends EntityBlock<E, V> implements IStation
{

	public EntityStation(Connection connection)
	{
		super(connection);
	}

	@Override
	public void move()
	{
		getCart().ifPresent(c -> leaveRandom(c));
	}

	@Override
	public CardEntity<?, ?> getCardEntityImpl()
	{
		return CardEntityStation.INSTANCE;
	}

	@Override
	public double getRadiusStation(double angle)
	{
		return getView().getRadiusStation(angle);
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
	public EntityBlock<?, ?> getEntity()
	{
		return this;
	}

	public Optional<Tuple<ICart, ConnectionAnchor>> getCart()
	{
		return getCarts().min((a, b) -> a.getY().order - b.getY().order);
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

	public void leaveRandom(Tuple<ICart, ConnectionAnchor> cart)
	{
		IRail[] rails = getRails()
			.filter(r -> r != cart.getY().previous)
			.toArray(IRail[]::new);

		if (rails.length > 0) {
			leaveCart(cart.getX(), rails[(int) (Math.random() * rails.length)]);
		} else if (cart.getY().previous instanceof IRail) {
			leaveCart(cart.getX(), (IRail) cart.getY().previous);
		}
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
