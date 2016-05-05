package mirrg.almandine2.layer2.tool;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Set;
import java.util.stream.Collectors;

import mirrg.almandine2.layer2.entity.CardEntityBlock;
import mirrg.almandine2.layer2.entity.Connection;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.TypeConnection;
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
