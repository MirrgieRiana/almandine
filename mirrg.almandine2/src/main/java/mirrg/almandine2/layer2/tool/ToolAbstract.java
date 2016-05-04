package mirrg.almandine2.layer2.tool;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.IEntity;
import mirrg.almandine2.layer3.entities.IWire;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;
import mirrg.struct.hydrogen.Tuple;
import mirrg.struct.hydrogen.Tuple3;
import mirrg.todo.HMath;

public abstract class ToolAbstract implements ITool
{

	protected GameAlmandine2 game;
	protected boolean disposed = false;

	@Override
	public void init(GameAlmandine2 game)
	{
		this.game = game;
	}

	protected <T> void hook(Class<T> clazz, Consumer<T> consumer)
	{
		game.panel.getEventManager().registerRemovable(clazz, event -> {
			if (disposed) return false;

			consumer.accept(event);

			return true;
		});
	}

	@Override
	public void dispose()
	{
		disposed = true;
	}

	@SuppressWarnings("unchecked")
	public <T extends IEntity> Stream<T> getEntities(Class<T> clazz)
	{
		return game.data.getEntities()
			.filter(e -> clazz.isInstance(e))
			.map(e -> (T) e);
	}

	public Point getCursor()
	{
		return new Point(
			game.panel.modulesStandard.moduleInputStatus.getMouseX(),
			game.panel.modulesStandard.moduleInputStatus.getMouseY());
	}

	public Point getCursor(NitrogenEventMouseMotion event)
	{
		return new Point(event.mouseEvent.getX(), event.mouseEvent.getY());
	}

	public Point getCursor(NitrogenEventMouse event)
	{
		return new Point(event.mouseEvent.getX(), event.mouseEvent.getY());
	}

	/**
	 * @return Tuple3(wire, position, distanceSq)
	 */
	public <T extends IWire> Optional<Tuple3<T, Double, Double>> getPointOnWire(Point point, Class<T> clazz, Predicate<Tuple3<T, Double, Double>> predicate)
	{
		return getEntities(clazz)
			.map(w -> new Tuple3<>(w,
				HMath.trim(w.getPosition(point.x, point.y), 0, 1),
				w.getDistanceSq(
					game.panel.modulesStandard.moduleInputStatus.getMouseX(),
					game.panel.modulesStandard.moduleInputStatus.getMouseY())))
			.filter(t -> predicate.test(t))
			.min((a, b) -> (int) Math.signum(a.getZ() - b.getZ()));
	}

	public <T extends IEntity> Optional<T> getEntityNearest(Point point, Class<T> clazz, Predicate<T> predicate)
	{
		return getEntities(clazz)
			.filter(predicate)
			.map(p -> new Tuple<>(p, p.getDistanceSq(point.x, point.y)))
			.min((a, b) -> (int) Math.signum(a.getY() - b.getY()))
			.map(t -> t.getX());
	}

	public Optional<Point> getPoint(Point point, Predicate<Point> predicate)
	{
		if (predicate.test(point)) {
			return Optional.of(point);
		} else {
			return Optional.empty();
		}
	}

}
