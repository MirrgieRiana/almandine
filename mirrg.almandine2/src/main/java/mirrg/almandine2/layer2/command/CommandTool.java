package mirrg.almandine2.layer2.command;

import java.util.Optional;
import java.util.function.Supplier;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.tool.Tool;

public class CommandTool extends Command
{

	private Supplier<Tool> supplier;

	public CommandTool(Supplier<Tool> supplier, Integer shortcutKey)
	{
		super(shortcutKey);
		this.supplier = supplier;
	}

	public Optional<Tool> createTool()
	{
		return Optional.ofNullable(supplier.get());
	}

	@Override
	public void action(GameAlmandine2 game)
	{
		game.setTool(createTool());
	}

}
