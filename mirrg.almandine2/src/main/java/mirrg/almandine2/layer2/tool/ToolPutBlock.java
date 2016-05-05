package mirrg.almandine2.layer2.tool;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Set;
import java.util.stream.Collectors;

import com.sun.glass.events.KeyEvent;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.Connection;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.TypeConnection;
import mirrg.applet.nitrogen.modules.input.NitrogenEventKey;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;

public class ToolPutBlock extends Tool
{

	private CardEntityBlock<?> card;
	private EntityBlock entity = null;

	public ToolPutBlock(CardEntityBlock<?> card)
	{
		this.card = card;
	}

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
					game.data.addEntity(entity);
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
		Set<TypeConnection> set = card.getConnectionTypes()
			.collect(Collectors.toSet());

		if (!isAlt()) {
			if (set.contains(TypeConnection.block)) {
				Connection connection = getConnectionBlock(cursor, isControl() ? 200 : 0, EntityBlock.class, card::isConnectable).orElse(null);

				if (connection != null) {
					entity = card.create(connection);
					return;
				}
			}
		}

		if (set.contains(TypeConnection.point)) {
			Connection connection = getConnectionPoint(cursor, card::isConnectable).orElse(null);

			if (connection != null) {
				entity = card.create(connection);
				return;
			}
		}

		entity = null;
	}

	@Override
	public void render(Graphics2D graphics)
	{
		if (entity != null) {
			Entity.getCardEntity(entity).getView().render(entity, graphics);
		}
	}

}
