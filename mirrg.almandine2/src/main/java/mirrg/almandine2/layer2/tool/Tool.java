package mirrg.almandine2.layer2.tool;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.sun.glass.events.KeyEvent;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.entity.IHandle;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.almandine2.layer2.entity.connection.TypeConnection;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;
import mirrg.struct.hydrogen.Tuple;
import mirrg.todo.HMath;

public abstract class Tool
{

	protected GameAlmandine2 game;
	protected boolean disabled = false;

	public abstract void move();

	public abstract void render(Graphics2D graphics);

	public void enable(GameAlmandine2 game)
	{
		this.game = game;
		initEvents();
		reset();
	}

	protected abstract void reset();

	protected abstract void initEvents();

	public void disable()
	{
		disabled = true;
	}

	protected <T> void hook(Class<T> clazz, Consumer<T> consumer)
	{
		game.panel.getEventManager().registerRemovable(clazz, event -> {
			if (disabled) return false;

			consumer.accept(event);

			return true;
		});
	}

	///////////////////////////////////// Cursor ///////////////////////////////////////

	protected Point2D.Double getCursor()
	{
		return new Point2D.Double(
			game.panel.modulesStandard.moduleInputStatus.getMouseX(),
			game.panel.modulesStandard.moduleInputStatus.getMouseY());
	}

	protected Point2D.Double getCursor(NitrogenEventMouseMotion event)
	{
		return new Point2D.Double(event.mouseEvent.getX(), event.mouseEvent.getY());
	}

	protected Point2D.Double getCursor(NitrogenEventMouse event)
	{
		return new Point2D.Double(event.mouseEvent.getX(), event.mouseEvent.getY());
	}

	protected boolean isKeyDown(int key)
	{
		return game.panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(key) > 0;
	}

	protected boolean isShift()
	{
		return isKeyDown(KeyEvent.VK_SHIFT);
	}

	protected boolean isControl()
	{
		return isKeyDown(KeyEvent.VK_CONTROL);
	}

	protected boolean isAlt()
	{
		return isKeyDown(KeyEvent.VK_ALT);
	}

	///////////////////////////////////// Entity ///////////////////////////////////////

	@SuppressWarnings("unchecked")
	protected <T extends Entity> Stream<T> getEntities(Class<T> clazz)
	{
		return game.data.getEntities()
			.filter(e -> clazz.isInstance(e))
			.map(e -> (T) e);
	}

	protected <T extends Entity> Optional<T> getEntity(Point2D.Double point, double margin, Class<T> clazz, Predicate<T> predicate)
	{
		return getEntities(clazz)
			.filter(predicate)
			.filter(e -> Entity.getCardEntity(e).getView().getDistanceEdge(e, point.x, point.y) <= margin)
			.map(e -> new Tuple<>(e, Entity.getCardEntity(e).getView().getDistanceCenterSq(e, point.x, point.y)))
			.min((a, b) -> (int) Math.signum(a.getY() - b.getY()))
			.map(t -> t.getX());
	}

	protected <T extends Entity> Stream<IHandle> getHandles(Class<T> clazz, Predicate<T> predicateEntity)
	{
		return getEntities(clazz)
			.filter(predicateEntity)
			.flatMap(e -> e.getHandles());
	}

	protected <T extends Entity> Optional<IHandle> getHandle(Point2D.Double point, double margin, Class<T> clazz, Predicate<T> predicateEntity, Predicate<IHandle> predicateHandle)
	{
		return getHandles(clazz, predicateEntity)
			.filter(predicateHandle)
			.filter(h -> h.getView().getDistanceEdge(h, point.x, point.y) <= margin)
			.map(h -> new Tuple<>(h, h.getView().getDistanceCenterSq(h, point.x, point.y)))
			.min((a, b) -> (int) Math.signum(a.getY() - b.getY()))
			.map(t -> t.getX());
	}

	protected Optional<ConnectionPoint> getConnectionPoint(
		Point2D.Double point,
		Predicate<ConnectionPoint> predicate)
	{
		ConnectionPoint connection = new ConnectionPoint(point);
		if (predicate.test(connection)) {
			return Optional.of(connection);
		} else {
			return Optional.empty();
		}
	}

	protected <T extends EntityBlock> Optional<ConnectionBlock> getConnectionBlock(
		Point2D.Double point,
		double margin,
		Class<T> clazz,
		Predicate<ConnectionBlock> predicate)
	{
		return getEntity(point, margin, clazz, e -> predicate.test(new ConnectionBlock(e)))
			.map(e -> new ConnectionBlock(e));
	}

	protected <T extends EntityWire> Optional<ConnectionTraffic> getConnectionTraffic(
		Point2D.Double point,
		double margin,
		Class<T> clazz,
		Predicate<ConnectionTraffic> predicate,
		boolean reverse)
	{
		return getEntity(point, margin, clazz, e -> predicate.test(new ConnectionTraffic(e, HMath.trim(e.getPosition(point.x, point.y), 0, 1), reverse)))
			.map(e -> new ConnectionTraffic(e, HMath.trim(e.getPosition(point.x, point.y), 0, 1), reverse));
	}

	protected Optional<Connection> getConnection(Point2D.Double cursor, double margin, Stream<TypeConnection> connectionTypes, Predicate<Connection> predicate)
	{
		for (TypeConnection connectionType : connectionTypes.toArray(TypeConnection[]::new)) {

			if (!isControl()) {
				if (!isShift()) {
					if (connectionType == TypeConnection.point) {
						Connection connection = getConnectionPoint(cursor, c -> predicate.test(c)).orElse(null);

						if (connection != null) return Optional.of(connection);
					}
				}
			}

			if (!isShift()) {
				if (!isAlt()) {
					if (connectionType == TypeConnection.block) {
						Connection connection = getConnectionBlock(cursor, margin, EntityBlock.class, c -> predicate.test(c)).orElse(null);

						if (connection != null) return Optional.of(connection);
					}
				}
			}

			if (!isControl()) {
				if (!isAlt()) {
					if (connectionType == TypeConnection.traffic) {
						Connection connection = getConnectionTraffic(cursor, margin, EntityWire.class, c -> predicate.test(c), isKeyDown(KeyEvent.VK_SPACE)).orElse(null);

						if (connection != null) return Optional.of(connection);
					}
				}
			}

			// TODO anchor

		}

		return Optional.empty();
	}

}
