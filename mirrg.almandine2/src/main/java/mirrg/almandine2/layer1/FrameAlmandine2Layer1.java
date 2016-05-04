package mirrg.almandine2.layer1;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class FrameAlmandine2Layer1 extends JFrame
{

	public PanelAlmandine2 panel;

	public FrameAlmandine2Layer1()
	{
		setLocationByPlatform(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new CardLayout());
		setSize(600, 600);
	}

	public void initComponents(IGameAlmandine2 game)
	{
		panel = createPanel(game);
		add(panel);
	}

	public PanelAlmandine2 createPanel(IGameAlmandine2 game)
	{
		return new PanelAlmandine2(game);
	}

	public void start()
	{
		panel.init();
		setVisible(true);
		panel.start();
	}

}
