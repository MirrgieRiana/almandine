package mirrg.almandine2.layer2.tool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.stream.Collectors;

import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse.Pressed;

public class ToolSelect extends ToolBase
{

	private Entity<?, ?> entity = null;

	@Override
	protected void onMousePressed(Pressed event)
	{
		if (entity != null) {

			synchronized (game) {

				if (isControl()) {
					if (game.data.isSelected(entity)) {
						game.data.removeSelection(entity);
					} else {
						game.data.addSelection(entity);
					}
				} else {
					game.data.getSelection()
						.collect(Collectors.toList())
						.forEach(e -> game.data.removeSelection(e));
					game.data.addSelection(entity);
				}

			}

			entity = null;
			update(getCursor(event));
		}
	}

	@Override
	protected void update(Point2D.Double cursor)
	{
		entity = getEntity(cursor, isShift() || isControl() || isAlt() ? 200 : 0, Entity.class, e -> {
			if (isShift()) if (!(e instanceof EntityWire)) return false;
			if (isControl()) if (!(e instanceof EntityBlock)) return false;
			return true;
		}).orElse(null);
	}

	@Override
	public void render(Graphics2D graphics)
	{
		if (entity != null) {
			entity.getView().renderAura(graphics, 2, 3, Color.decode("#FF7F00"));
		}
	}

}
