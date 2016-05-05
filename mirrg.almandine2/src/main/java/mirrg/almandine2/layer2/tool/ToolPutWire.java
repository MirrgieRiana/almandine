package mirrg.almandine2.layer2.tool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Set;
import java.util.stream.Collectors;

import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.Connection;
import mirrg.almandine2.layer2.entity.Entity;
import mirrg.almandine2.layer2.entity.EntityBlock;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.entity.TypeConnection;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse.Pressed;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse.Released;

public class ToolPutWire extends ToolBase
{

	private CardEntityWire<?> card;
	private Connection begin = null;
	private EntityWire entity = null;
	private boolean holding = false; // true => begin != null

	public ToolPutWire(CardEntityWire<?> card)
	{
		this.card = card;
	}

	@Override
	protected void onMousePressed(Pressed event)
	{
		if (begin != null) {
			holding = true;
			update(getCursor(event));
		}
	}

	@Override
	protected void onMouseReleased(Released event)
	{
		if (holding) {
			holding = false;

			if (entity != null) {
				synchronized (game) {
					game.data.addEntity(entity);
				}

				begin = null;
				entity = null;
				update(getCursor(event));
			}
		}
	}

	@Override
	protected void update(Point2D.Double cursor)
	{
		if (!holding) {
			Set<TypeConnection> set = card.getConnectionTypesBegin()
				.collect(Collectors.toSet());

			if (!isAlt()) {
				if (set.contains(TypeConnection.block)) {
					Connection connection = getConnectionBlock(cursor, isControl() ? 200 : 0, EntityBlock.class, card::isConnectableBegin).orElse(null);

					if (connection != null) {
						begin = connection;
						return;
					}
				}
			}

			if (set.contains(TypeConnection.point)) {
				Connection connection = getConnectionPoint(cursor, card::isConnectableBegin).orElse(null);

				if (connection != null) {
					begin = connection;
					return;
				}
			}

			begin = null;
		} else {
			Set<TypeConnection> set = card.getConnectionTypesEnd()
				.collect(Collectors.toSet());

			if (!isAlt()) {
				if (set.contains(TypeConnection.block)) {
					Connection connection = getConnectionBlock(cursor, isControl() ? 200 : 0, EntityBlock.class, card::isConnectableEnd).orElse(null);

					if (connection != null) {
						entity = card.create(begin, connection);
						return;
					}
				}
			}

			if (set.contains(TypeConnection.point)) {
				Connection connection = getConnectionPoint(cursor, card::isConnectableEnd).orElse(null);

				if (connection != null) {
					entity = card.create(begin, connection);
					return;
				}
			}

			entity = null;
		}
	}

	@Override
	public void render(Graphics2D graphics)
	{
		if (entity != null) {
			Entity.getCardEntity(entity).getView().render(entity, graphics);
		} else if (begin != null) {
			graphics.setColor(Color.red);
			graphics.draw(new Line2D.Double(
				begin.getPoint().x - 10,
				begin.getPoint().y - 10,
				begin.getPoint().x + 10,
				begin.getPoint().y + 10));
			graphics.draw(new Line2D.Double(
				begin.getPoint().x - 10,
				begin.getPoint().y + 10,
				begin.getPoint().x + 10,
				begin.getPoint().y - 10));
		}
	}

}