package mirrg.almandine2.layer2.core;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import mirrg.almandine2.layer2.entity.IEntity;

public class DataAlmandine2
{

	@XStreamOmitField
	private GameAlmandine2 game;

	private ArrayList<IEntity> entities;

	public void reset()
	{
		entities = new ArrayList<>();
	}

	public void cleanEntities()
	{
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isDead()) {
				entities.remove(i);
				i--;
			}
		}
	}

	public void addEntity(IEntity entity)
	{
		entities.add(entity);
		entity.onLoad(game);
	}

	public Stream<IEntity> getEntities()
	{
		return entities.stream();
	}

	public void onLoad(GameAlmandine2 game)
	{
		this.game = game;
		entities.forEach(entity -> entity.onLoad(game));
	}

}
