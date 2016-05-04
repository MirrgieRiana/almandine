package mirrg.almandine2.layer3.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Optional;

import com.sun.glass.events.KeyEvent;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.tool.ToolAbstract;
import mirrg.almandine2.layer3.entities.ICardCart;
import mirrg.almandine2.layer3.entities.IWire;
import mirrg.applet.nitrogen.modules.input.NitrogenEventKey;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouse;
import mirrg.applet.nitrogen.modules.input.NitrogenEventMouseMotion;
import mirrg.struct.hydrogen.Tuple3;

public class ToolPutCart extends ToolAbstract
{

	private ICardCart cardCart;
	private IWire wire = null;
	private double position;
	private boolean reverse;

	public ToolPutCart(ICardCart cardCart)
	{
		this.cardCart = cardCart;
	}

	@Override
	public void init(GameAlmandine2 game)
	{
		super.init(game);

		update();

		hook(NitrogenEventMouseMotion.Moved.class, event -> {
			update();
		});
		hook(NitrogenEventKey.Pressed.class, event -> {
			update();
		});
		hook(NitrogenEventKey.Released.class, event -> {
			update();
		});
		hook(NitrogenEventMouseMotion.Dragged.class, event -> {
			update();
		});
		hook(NitrogenEventMouse.Pressed.class, event -> {
			if (wire != null) {

				synchronized (game) {
					game.data.addEntity(cardCart.create(wire, position, reverse));
				}

			}
		});
	}

	private void update()
	{
		reverse = game.panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(KeyEvent.VK_SHIFT) > 0;
		Optional<Tuple3<IWire, Double, Double>> entry = getPointOnWire(
			getCursor(),
			IWire.class,
			t -> cardCart.isSettable(t.getX(), t.getY(), reverse));

		if (entry.isPresent()) {
			wire = entry.get().getX();
			position = entry.get().getY();
		}

	}

	@Override
	public void render(Graphics2D graphics)
	{
		if (wire != null) {
			wire.renderAura(graphics, 2, 3, Color.decode("#FF7F00"));
			cardCart.render(graphics, wire, position, reverse);
		}
	}

}
