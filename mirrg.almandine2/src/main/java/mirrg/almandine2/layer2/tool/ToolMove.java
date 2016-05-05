package mirrg.almandine2.layer2.tool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.entity.IHandle;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse.Pressed;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse.Released;

public class ToolMove extends ToolBase
{

	private IHandle handle = null;
	private boolean holding = false; // true => handle != null

	@Override
	public void move()
	{
		super.move();

	}

	@Override
	protected void onMousePressed(Pressed event)
	{
		if (handle != null) {
			holding = true;
			update(getCursor(event));
		}
	}

	@Override
	protected void onMouseReleased(Released event)
	{
		if (holding) {
			holding = false;
		}
	}

	@Override
	protected void update(Point2D.Double cursor)
	{
		double margin = isShift() || isControl() || isAlt() ? 200 : 0;
		if (!holding) {
			handle = getHandle(cursor, margin,
				Entity.class, this::testEntity, h -> true)
					.orElse(null);
		} else {
			getConnection(cursor, margin, handle.getConnectionTypes(), handle::isConnectable)
				.ifPresent(c -> {
					synchronized (game) {
						handle.set(c);
					}
				});
		}
	}

	@Override
	public void render(Graphics2D graphics)
	{
		getHandles(Entity.class, this::testEntity).forEach(h -> {
			h.getView().render(h, graphics);
		});

		if (handle != null) {
			if (holding) {
				handle.getView().renderAura(handle, graphics, 2, 3, Color.decode("#FF7F00"));
			} else {
				handle.getView().renderAura(handle, graphics, 2, 3, Color.decode("#4CDB7C"));
			}
		}
	}

	private boolean testEntity(Entity entity)
	{
		if (isShift()) if (!(entity instanceof EntityWire)) return false;
		if (isControl()) if (!(entity instanceof EntityBlock)) return false;
		return true;
	}

}
