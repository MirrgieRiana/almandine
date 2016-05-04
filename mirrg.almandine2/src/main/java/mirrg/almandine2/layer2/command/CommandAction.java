package mirrg.almandine2.layer2.command;

import java.util.function.Consumer;

import mirrg.almandine2.layer2.core.GameAlmandine2;

public class CommandAction extends Command
{

	private Consumer<GameAlmandine2> consumer;

	public CommandAction(Consumer<GameAlmandine2> consumer, Integer shortcutKey)
	{
		super(shortcutKey);
		this.consumer = consumer;
	}

	@Override
	public void action(GameAlmandine2 game)
	{
		consumer.accept(game);
	}

}
