package mirrg.almandine2.layer3.tools;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.Connection;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.tool.Tool;
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
	protected void initEvents()
	{
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
		Connection connection = getConnectionPoint(cursor, card::isConnectable).orElse(null);

		// TODO card.getConnectionTypes();

		if (connection != null) {
			entity = card.create(connection);
		} else {
			entity = null;
		}
	}

	@Override
	public void render(Graphics2D graphics)
	{
		if (entity != null) {
			Entity.getCardEntity(entity).getView().render(entity, graphics);
		}
	}

}
