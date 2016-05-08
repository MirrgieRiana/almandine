package mirrg.almandine2.layer2.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Optional;

import com.thoughtworks.xstream.XStream;

import mirrg.almandine2.layer1.IGameAlmandine2;
import mirrg.almandine2.layer1.PanelAlmandine2;
import mirrg.almandine2.layer2.command.Command;
import mirrg.almandine2.layer2.tool.Tool;
import mirrg.event.nitrogen.HNitrogenEvent;
import mirrg.event.nitrogen.api.INitrogenEventManager;

public class GameAlmandine2 implements IGameAlmandine2
{

	public PanelAlmandine2 panel;
	private double width;
	private double height;

	private ArrayList<Command> commands = new ArrayList<>();
	public INitrogenEventManager nitrogen = HNitrogenEvent.createInstance();

	public DataAlmandine2 data;
	private Optional<Tool> oTool = Optional.empty();

	public GameAlmandine2()
	{
		data = new DataAlmandine2();
		data.reset();
		data.onLoad(this);
	}

	@Override
	public void init(PanelAlmandine2 panel, int width, int height)
	{
		this.panel = panel;
		this.width = width;
		this.height = height;
	}

	@Override
	public void resized(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	@Override
	public void move()
	{

		synchronized (this) {

			// select tool
			commands.forEach(command -> {
				command.getShortcutKey().ifPresent(key -> {
					if (panel.modulesStandard.moduleInputStatus.getKeyBoard().getState(key) == 1) {
						command.action(this);
					}
				});
			});

			// move tool
			oTool.ifPresent(Tool::move);

			// move entities
			data.getEntities().forEach(entity -> entity.move());

			// post move
			nitrogen.post(new NitrogenEventPostMove());

			// clean
			data.cleanEntities();

		}

	}

	public void setTool(Optional<Tool> oTool)
	{
		this.oTool.ifPresent(tool -> tool.disable());
		this.oTool = oTool;
		this.oTool.ifPresent(tool -> tool.enable(this));
	}

	@Override
	public void paint(Graphics2D graphics)
	{

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// clear
		graphics.setColor(Color.white);
		graphics.fill(new Rectangle2D.Double(0, 0, width, height));

		// draw FPS
		graphics.setColor(Color.black);
		graphics.drawString("TPS: " + panel.moduleGameThreadGame.objectiveFPS,
			0, (int) height - graphics.getFont().getSize() * 1);
		graphics.drawString("FPS: " + panel.modulesStandard.moduleGameThread.objectiveFPS,
			0, (int) height - graphics.getFont().getSize() * 0);

		synchronized (this) {

			// render entities
			data.getEntities().forEach(entity -> entity.getView().render(graphics));

			// render tool
			oTool.ifPresent(tool -> tool.render(graphics));

		}

	}

	@Override
	public double getTickPerSecond()
	{
		return 60;
	}

	@Override
	public double getFramePerSecond()
	{
		return 30;
	}

	public void registerCommand(Command command)
	{
		commands.add(command);
	}

	public XStream createXStream()
	{
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		xStream.alias("data", DataAlmandine2.class);
		return xStream;
	}

	public class NitrogenEventPostMove
	{

	}

}
