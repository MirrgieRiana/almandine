package mirrg.almandine2.layer3.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import com.sun.glass.events.KeyEvent;

import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.EntityCart;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.tool.Tool;
import mirrg.applet.nitrogen.modules.input.NitrogenEventKey;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;

public class ToolDelete extends Tool
{

	private Entity entity = null;

	@Override
	protected void initEvents()
	{
		hook(NitrogenEventKey.Pressed.class, event -> {
			if (event.keyEvent.getKeyCode() == KeyEvent.VK_ALT) {
				event.keyEvent.consume();
			}
		});
		hook(NitrogenEventKey.Pressed.class, event -> {
			update(getCursor());
		});
		hook(NitrogenEventKey.Released.class, event -> {
			update(getCursor());
		});
		hook(NitrogenEventMouseMotion.Moved.class, event -> {
			update(getCursor(event));
		});
		hook(NitrogenEventMouseMotion.Dragged.class, event -> {
			update(getCursor(event));
		});
		hook(NitrogenEventMouse.Pressed.class, event -> {
			update(getCursor(event));
			if (entity != null) {
				entity.markDie();
				entity = null;
			}
		});
	}

	@Override
	protected void reset()
	{
		update(getCursor());
	}

	private void update(Point2D.Double cursor)
	{
		entity = getEntity(cursor, Entity.class, e -> {
			if (game.panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(KeyEvent.VK_SHIFT) > 0) {
				if (!(e instanceof EntityWire)) return false;
			}
			if (game.panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(KeyEvent.VK_CONTROL) > 0) {
				if (!(e instanceof EntityBlock)) return false;
			}
			if (game.panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(KeyEvent.VK_ALT) > 0) {
				if (!(e instanceof EntityCart)) return false;
			}
			return true;
		}).orElse(null);
	}

	@Override
	public void render(Graphics2D graphics)
	{
		if (entity != null) {
			Entity.getCardEntity(entity).getView().renderAura(entity, graphics, 2, 3, Color.decode("#FF7F00"));
		}
	}

}
