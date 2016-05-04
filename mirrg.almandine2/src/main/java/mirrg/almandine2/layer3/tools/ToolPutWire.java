package mirrg.almandine2.layer3.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Optional;

import com.sun.glass.events.KeyEvent;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.tool.IPoint;
import mirrg.almandine2.layer2.tool.Point;
import mirrg.almandine2.layer2.tool.Tool;
import mirrg.almandine2.layer3.entities.ICardWire;
import mirrg.almandine2.layer3.entities.IFurniture;
import mirrg.applet.nitrogen.modules.input.NitrogenEventKey;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;
import mirrg.struct.hydrogen.Tuple;

public class ToolPutWire extends Tool
{

	private ICardWire cardWire;
	private IPoint begin = null;
	private IPoint end = null;

	public ToolPutWire(ICardWire cardWire)
	{
		this.cardWire = cardWire;
	}

	@Override
	public void init(GameAlmandine2 game)
	{
		super.init(game);

		begin = getPoint(true);
		end = null;

		hook(NitrogenEventKey.Pressed.class, event -> {
			if (event.keyEvent.getKeyCode() == KeyEvent.VK_ALT) {
				event.keyEvent.consume();
			}
		});

		hook(NitrogenEventMouseMotion.Moved.class, event -> {
			begin = getPoint(true);
			end = null;
		});
		hook(NitrogenEventMouse.Pressed.class, event -> {
			begin = getPoint(true);
			end = getPoint(false);
		});
		hook(NitrogenEventMouseMotion.Dragged.class, event -> {
			end = getPoint(false);
		});
		hook(NitrogenEventMouse.Released.class, event -> {
			end = getPoint(false);

			if (begin != null) {

				if (!begin.equals(end)) {
					synchronized (game) {
						game.data.addEntity(cardWire.create(begin, end));
					}
				}

				begin = null;
				end = null;
			}
		});
	}

	private IPoint getPoint(boolean isBegin)
	{
		return getPoint(
			game.panel.modulesStandard.moduleInputStatus.getMouseX(),
			game.panel.modulesStandard.moduleInputStatus.getMouseY(),
			isBegin);
	}

	private IPoint getPoint(int x, int y, boolean isBegin)
	{
		if (game.panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(KeyEvent.VK_ALT) <= 0) {
			Optional<IFurniture> furniture = game.data.getEntities()
				.filter(entity -> entity instanceof IFurniture)
				.map(entity -> (IFurniture) entity)
				.filter(f -> isBegin
					? cardWire.isSettableBegin(f)
					: cardWire.isSettableEnd(f))
				.map(f -> new Tuple<>(f, (x - f.getX()) * (x - f.getX()) + (y - f.getY()) * (y - f.getY())))
				.filter(t -> t.getY() < getCursorMargin() * getCursorMargin())
				.min((a, b) -> (int) Math.signum(a.getY() - b.getY()))
				.map(t -> t.getX());

			if (furniture.isPresent()) {
				return furniture.get();
			}
		}

		return new Point(x, y);
	}

	private int getCursorMargin()
	{
		if (game.panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(KeyEvent.VK_CONTROL) > 0) {
			return 1000;
		} else {
			return 8;
		}
	}

	@Override
	public void render(Graphics2D graphics)
	{
		if (begin != null) {
			if (end != null) {
				cardWire.render(graphics, begin, end);
			} else {
				graphics.setColor(Color.red);
				graphics.draw(new Line2D.Double(begin.getX() - 10, begin.getY() - 10, begin.getX() + 10, begin.getY() + 10));
				graphics.draw(new Line2D.Double(begin.getX() - 10, begin.getY() + 10, begin.getX() + 10, begin.getY() - 10));
			}
		}
	}

}
