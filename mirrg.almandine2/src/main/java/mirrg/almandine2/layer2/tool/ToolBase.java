package mirrg.almandine2.layer2.tool;

import java.awt.geom.Point2D;

import com.sun.glass.events.KeyEvent;

import mirrg.applet.nitrogen.modules.input.NitrogenEventKey;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse.Pressed;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse.Released;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;

public abstract class ToolBase extends Tool
{

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

			onMousePressed(event);

		});
		hook(NitrogenEventMouse.Released.class, event -> {
			update(getCursor(event));

			onMouseReleased(event);

		});
	}

	protected void onMousePressed(Pressed event)
	{

	}

	protected void onMouseReleased(Released event)
	{

	}

	@Override
	protected void reset()
	{
		update(getCursor());
	}

	protected abstract void update(Point2D.Double cursor);

}
