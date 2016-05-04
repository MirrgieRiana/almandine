package mirrg.almandine2.layer2.command;

import java.util.Optional;

import mirrg.almandine2.layer2.core.GameAlmandine2;

public abstract class Command
{

	private Integer shortcutKey;

	public Command(Integer shortcutKey)
	{
		this.shortcutKey = shortcutKey;
	}

	public Optional<Integer> getShortcutKey()
	{
		return Optional.of(shortcutKey);
	}

	public abstract void action(GameAlmandine2 game);

}
