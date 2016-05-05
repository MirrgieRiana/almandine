package mirrg.almandine2.layer2.tool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse.Pressed;

public class ToolPutBlock extends ToolBase
{

	private CardEntityBlock<?> card;
	private EntityBlock entity = null;

	public ToolPutBlock(CardEntityBlock<?> card)
	{
		this.card = card;
	}

	@Override
	protected void onMousePressed(Pressed event)
	{
		if (entity != null) {
			synchronized (game) {
				game.data.addEntity(entity);
			}

			entity = null;
			update(getCursor(event));
		}
	}

	@Override
	protected void update(Point2D.Double cursor)
	{
		entity = getConnection(cursor, card.getConnectionTypes(), card::isConnectable)
			.map(connection -> card.create(connection))
			.orElse(null);
	}

	@Override
	public void render(Graphics2D graphics)
	{
		if (entity != null) {
			entity.getConnections().forEach(connection -> {
				connection.getEntities().forEach(entity2 -> {
					Entity.getCardEntity(entity2).getView().renderAura(entity2, graphics, 2, 3, Color.decode("#4CDB7C"));
				});
			});
			Entity.getCardEntity(entity).getView().render(entity, graphics);
		}
	}

}
