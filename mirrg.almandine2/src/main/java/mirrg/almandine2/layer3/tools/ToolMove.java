package mirrg.almandine2.layer3.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Optional;

import com.sun.glass.events.KeyEvent;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.IHandle;
import mirrg.almandine2.layer2.tool.IPoint;
import mirrg.almandine2.layer2.tool.Point;
import mirrg.almandine2.layer2.tool.Tool;
import mirrg.almandine2.layer3.entities.IFurniture;
import mirrg.almandine2.layer3.entities.IWire;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;

public class ToolMove extends Tool
{

	private IHandle handle = null;
	private IPoint end = null;

	@Override
	public void enable(GameAlmandine2 game)
	{
		super.enable(game);

		handle = getHandle();
		end = null;

		hook(NitrogenEventMouseMotion.Moved.class, event -> {
			handle = getHandle();
			end = null;
		});
		hook(NitrogenEventMouse.Pressed.class, event -> {
			handle = getHandle();
			if (handle != null) {
				end = getPoint();
			} else {
				end = null;
			}
		});
		hook(NitrogenEventMouseMotion.Dragged.class, event -> {
			if (handle != null) {
				end = getPoint();
			} else {
				end = null;
			}
		});
		hook(NitrogenEventMouse.Released.class, event -> {
			if (handle != null) {
				end = getPoint();
			} else {
				end = null;
			}

			if (handle != null) {
				if (end != null) {

					handle.set(end);

				}

				handle = null;
				end = null;
			}
		});
	}

	private IHandle getHandle()
	{
		return getHandle(
			game.panel.modulesStandard.moduleInputStatus.getMouseX(),
			game.panel.modulesStandard.moduleInputStatus.getMouseY());
	}

	private IHandle getHandle(int x, int y)
	{
		Optional<IHandle> handle = game.data.getEntities()
			.filter(e -> {
				if (game.panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(KeyEvent.VK_SHIFT) > 0) {
					if (!(e instanceof IWire)) return false;
				}
				if (game.panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(KeyEvent.VK_CONTROL) > 0) {
					if (!(e instanceof IFurniture)) return false;
				}
				return true;
			})
			.flatMap(e -> e.getHandles())
			.filter(h -> h.getShape().contains(x, y))
			.findFirst();

		if (handle.isPresent()) {
			return handle.get();
		} else {
			return null;
		}
	}

	private IPoint getPoint()
	{
		return getPoint(
			game.panel.modulesStandard.moduleInputStatus.getMouseX(),
			game.panel.modulesStandard.moduleInputStatus.getMouseY());
	}

	private IPoint getPoint(int x, int y)
	{
		Optional<IFurniture> furniture = getEntityNearest(getCursor(), IFurniture.class, handle::isConnectable);

		IPoint point;
		if (furniture.isPresent()) {
			point = furniture.get();
		} else {
			point = new Point(x, y);
			if (!handle.isConnectable(point)) point = null;
		}

		return point;
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
		if (handle != null) handle.getOwner().renderAura(graphics, 2, 3, Color.decode("#FF7F00"));

		graphics.setColor(Color.red);
		game.data.getEntities()
			.flatMap(e -> e.getHandles())
			.forEach(h -> {
				graphics.draw(h.getShape());
			});

		if (end != null) {
			graphics.setColor(Color.red);
			graphics.draw(new Ellipse2D.Double(end.getX() - 3, end.getY() - 3, 6, 6));
		}
	}

}
