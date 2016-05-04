package mirrg.almandine2.layer3.tools;

import java.awt.Graphics2D;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.tool.Point;
import mirrg.almandine2.layer2.tool.ToolAbstract;
import mirrg.almandine2.layer3.entities.ICardFurniture;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;

public class ToolPutFurniture extends ToolAbstract
{

	private ICardFurniture cardFurniture;
	private Point point = null;

	public ToolPutFurniture(ICardFurniture cardFurniture)
	{
		this.cardFurniture = cardFurniture;
	}

	@Override
	public void init(GameAlmandine2 game)
	{
		super.init(game);

		point = getPoint(getCursor(), cardFurniture::isSettable).orElse(null);

		hook(NitrogenEventMouseMotion.Moved.class, event -> {
			point = getPoint(getCursor(event), cardFurniture::isSettable).orElse(null);
		});
		hook(NitrogenEventMouseMotion.Dragged.class, event -> {
			point = getPoint(getCursor(event), cardFurniture::isSettable).orElse(null);
		});
		hook(NitrogenEventMouse.Pressed.class, event -> {
			if (point != null) {

				synchronized (game) {
					game.data.addEntity(cardFurniture.create(point));
				}

			}
		});
	}

	@Override
	public void render(Graphics2D graphics)
	{
		if (point != null) {
			cardFurniture.render(graphics, point);
		}
	}

}
