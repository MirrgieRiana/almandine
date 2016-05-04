package mirrg.almandine2.layer3.tools;

import java.awt.Color;
import java.awt.Graphics2D;

import com.sun.glass.events.KeyEvent;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.IEntity;
import mirrg.almandine2.layer2.tool.ToolAbstract;
import mirrg.almandine2.layer3.entities.IFurniture;
import mirrg.almandine2.layer3.entities.IWire;
import mirrg.applet.nitrogen.modules.input.NitrogenEventKey;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;

public class ToolDelete extends ToolAbstract
{

	private IEntity entity = null;

	@Override
	public void init(GameAlmandine2 game)
	{
		super.init(game);

		updateCursor();

		hook(NitrogenEventKey.Pressed.class, event -> {
			updateCursor();
		});
		hook(NitrogenEventKey.Released.class, event -> {
			updateCursor();
		});
		hook(NitrogenEventMouseMotion.Moved.class, event -> {
			updateCursor();
		});
		hook(NitrogenEventMouse.Pressed.class, event -> {
			updateCursor();
			if (entity != null) {
				entity.markDie();
				entity = null;
			}
		});
	}

	private void updateCursor()
	{
		entity = getEntityNearest(getCursor(), IEntity.class, e -> {
			if (game.panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(KeyEvent.VK_SHIFT) > 0) {
				if (!(e instanceof IWire)) return false;
			}
			if (game.panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(KeyEvent.VK_CONTROL) > 0) {
				if (!(e instanceof IFurniture)) return false;
			}
			return true;
		}).orElse(null);
	}

	@Override
	public void render(Graphics2D graphics)
	{
		if (entity != null) entity.renderAura(graphics, 2, 3, Color.decode("#FF7F00"));
	}

}
