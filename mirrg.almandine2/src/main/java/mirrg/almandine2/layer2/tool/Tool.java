package mirrg.almandine2.layer2.tool;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.ConnectionPoint;
import mirrg.almandine2.layer2.entity.ConnectionTraffic;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;
import mirrg.struct.hydrogen.Tuple;
import mirrg.todo.HMath;

public abstract class Tool
{

	protected GameAlmandine2 game;
	protected boolean disposed = false;

	public final void init(GameAlmandine2 game) // TODO remove final
	{
		this.game = game;
		initEvents();
		reset();
	}

	protected abstract void reset();

	protected abstract void initEvents();

	protected <T> void hook(Class<T> clazz, Consumer<T> consumer)
	{
		game.panel.getEventManager().registerRemovable(clazz, event -> {
			if (disposed) return false;

			consumer.accept(event);

			return true;
		});
	}

	public final void dispose() // TODO remove final
	{
		disposed = true;
	}

	public abstract void render(Graphics2D graphics);

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

	///////////////////////////////////// Entity ///////////////////////////////////////

	@SuppressWarnings("unchecked")
	protected <T extends Entity> Stream<T> getEntities(Class<T> clazz)
	{
		return game.data.getEntities()
			.filter(e -> clazz.isInstance(e))
			.map(e -> (T) e);
	}

	protected <T extends Entity> Optional<T> getEntity(Point2D.Double point, Class<T> clazz, Predicate<T> predicate)
	{
		return getEntities(clazz)
			.filter(predicate)
			.map(e -> new Tuple<>(e, Entity.getCardEntity(e).getView().getDistanceEdge(e, point.x, point.y)))
			.filter(t -> t.getY() <= 0)
			.min((a, b) -> (int) Math.signum(a.getY() - b.getY()))
			.map(t -> t.getX());
	}

	protected <T extends EntityWire> Optional<ConnectionTraffic> getConnectionTraffic(
		Point2D.Double point,
		Class<T> clazz,
		Predicate<ConnectionTraffic> predicate,
		boolean reverse)
	{
		return getEntities(clazz)
			.map(w -> new ConnectionTraffic(w, HMath.trim(w.getPosition(point.x, point.y), 0, 1), reverse))
			.filter(t -> predicate.test(t))
			.min((a, b) -> (int) Math.signum(a.entity.getDistanceSq(point.x, point.y) - b.entity.getDistanceSq(point.x, point.y)));
	}

	protected Optional<ConnectionPoint> getConnectionPoint(Point2D.Double point, Predicate<ConnectionPoint> predicate)
	{
		ConnectionPoint connection = new ConnectionPoint(point);
		if (predicate.test(connection)) {
			return Optional.of(connection);
		} else {
			return Optional.empty();
		}
	}

}
