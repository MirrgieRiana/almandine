package mirrg.almandine2.layer1;

import mirrg.applet.nitrogen.AppletNitrogen;
import mirrg.applet.nitrogen.NitrogenEventApplet;
import mirrg.applet.nitrogen.NitrogenEventComponent;
import mirrg.applet.nitrogen.modules.threading.ModuleGameThread;
import mirrg.applet.nitrogen.modules.threading.NitrogenEventGameThread;
import mirrg.todo.ModuleStandard;

public class PanelAlmandine2 extends AppletNitrogen
{

	private static final long serialVersionUID = 6370904732290917883L;

	public ModuleStandard modulesStandard;
	public ModuleGameThread moduleGameThreadGame = null;

	public PanelAlmandine2(IGameAlmandine2 game)
	{
		setFocusable(true);

		modulesStandard = new ModuleStandard(this) {

			@Override
			public void initModuleThreading(AppletNitrogen applet)
			{
				moduleGameThread = new ModuleGameThread(applet);
				moduleGameThreadGame = new ModuleGameThread(applet);
			}

		};

		//

		getEventManager().register(NitrogenEventApplet.Init.class, event -> {
			game.init(this, getWidth(), getHeight());

			moduleGameThreadGame.objectiveFPS = game.getTickPerSecond();
			modulesStandard.moduleGameThread.objectiveFPS = game.getFramePerSecond();
		});
		getEventManager().register(NitrogenEventGameThread.Init.class, event -> {
			game.resized(getWidth(), getHeight());
		});
		getEventManager().register(NitrogenEventComponent.Resized.class, event -> {
			game.resized(getWidth(), getHeight());
		});
		getEventManager().register(NitrogenEventGameThread.Tick.class, event -> {
			if (event.moduleGameThread == moduleGameThreadGame) {
				game.move();
				moduleGameThreadGame.objectiveFPS = game.getTickPerSecond();
			}
		});
		getEventManager().register(NitrogenEventGameThread.PostTick.class, event -> {
			if (event.moduleGameThread == moduleGameThreadGame) {
				modulesStandard.moduleInputStatus.spend();
			}
		});
		getEventManager().register(NitrogenEventGameThread.Render.class, event -> {
			if (event.moduleGameThread == modulesStandard.moduleGameThread) {

				game.paint(modulesStandard.moduleTripleBuffer.getBufferDirty().getGraphics());
				modulesStandard.moduleGameThread.objectiveFPS = game.getFramePerSecond();

				modulesStandard.moduleTripleBuffer.flip();

				repaint();
			}
		});
		getEventManager().register(NitrogenEventApplet.Paint.class, event -> {
			event.graphics.drawImage(modulesStandard.moduleTripleBuffer.getBufferSafety().getBuffer(), 0, 0, this);
		});

	}

}
