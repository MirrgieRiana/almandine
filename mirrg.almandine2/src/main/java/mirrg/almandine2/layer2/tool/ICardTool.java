package mirrg.almandine2.layer2.tool;

import java.util.Optional;

public interface ICardTool
{

	public Optional<ITool> createTool();

	public Optional<Integer> getShortcutKey();

}
