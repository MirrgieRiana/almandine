package mirrg.almandine2.layer2;

import mirrg.almandine2.layer1.FrameAlmandine2Layer1;
import mirrg.almandine2.layer2.core.GameAlmandine2;

public abstract class FrameAlmandine2Layer2 extends FrameAlmandine2Layer1
{

	public FrameAlmandine2Layer2()
	{
		GameAlmandine2 game = new GameAlmandine2();
		initComponents(game);
		registerContents(game);
	}

	public abstract void registerContents(GameAlmandine2 game);

}
