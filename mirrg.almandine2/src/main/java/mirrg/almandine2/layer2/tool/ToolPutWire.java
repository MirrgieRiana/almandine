package mirrg.almandine2.layer2.tool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Optional;

import mirrg.almandine2.layer2.entity.CardEntityWire;
import mirrg.almandine2.layer2.entity.EntityWire;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse.Pressed;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse.Released;

public class ToolPutWire extends ToolBase
{

	private CardEntityWire<? extends EntityWire<?, ?>, ?> card;
	private Connection begin = null;
	private EntityWire<?, ?> entity = null;
	private boolean holding = false; // true => begin != null

	public ToolPutWire(CardEntityWire<? extends EntityWire<?, ?>, ?> card)
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
		double margin = isShift() || isControl() || isAlt() ? 200 : 0;
		if (!holding) {
			begin = getConnection(cursor, margin, card.getConnectionTypesBegin(), card::isConnectableBegin)
				.orElse(null);
		} else {
			entity = getConnection(cursor, margin, card.getConnectionTypesEnd(), card::isConnectableEnd)
				.map(connection -> card.create(begin, connection))
				.orElse(Optional.empty())
				.orElse(null);
		}
	}

	@Override
	public void render(Graphics2D graphics)
	{
		if (entity != null) {
			entity.getConnections().forEach(connection -> {
				connection.getEntities().forEach(entity2 -> {
					entity2.getView().renderAura(graphics, 2, 3, Color.decode("#4CDB7C"));
				});
			});
			entity.getView().render(graphics);
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
