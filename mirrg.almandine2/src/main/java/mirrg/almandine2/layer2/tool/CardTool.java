package mirrg.almandine2.layer2.tool;

import java.util.Optional;
import java.util.function.Supplier;

public class CardTool implements ICardTool
{

	private Supplier<ITool> supplier;
	private Integer shortcutKey;

	public CardTool(Supplier<ITool> supplier, Integer shortcutKey)
	{
		this.supplier = supplier;
		this.shortcutKey = shortcutKey;
	}

	@Override
	public Optional<ITool> createTool()
	{
		return Optional.ofNullable(supplier.get());
	}

	@Override
	public Optional<Integer> getShortcutKey()
	{
		return Optional.ofNullable(shortcutKey);
	}

}
