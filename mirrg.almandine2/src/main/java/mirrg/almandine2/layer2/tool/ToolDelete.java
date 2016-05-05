package mirrg.almandine2.layer2.tool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import com.sun.glass.events.KeyEvent;

import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.EntityCart;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.applet.nitrogen.modules.input.NitrogenEventKey;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;

public class ToolDelete extends Tool
{

	private Entity entity = null;

	@Override
	public void move()
	{
		update(getCursor());
	}

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
				synchronized (game) {
					entity.markDie();
				}

				entity = null;
				update(getCursor(event));
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
		double margin = 0;
		if (isShift()) margin = 200;
		if (isControl()) margin = 200;
		if (isAlt()) margin = 200;

		entity = getEntity(cursor, margin, Entity.class, e -> {
			if (isShift()) if (!(e instanceof EntityWire)) return false;
			if (isControl()) if (!(e instanceof EntityBlock)) return false;
			if (isAlt()) if (!(e instanceof EntityCart)) return false;
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
