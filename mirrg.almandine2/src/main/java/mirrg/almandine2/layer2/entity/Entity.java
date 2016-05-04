package mirrg.almandine2.layer2.entity;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import mirrg.almandine2.layer2.core.GameAlmandine2;

public abstract class Entity
{

	@XStreamOmitField
	protected GameAlmandine2 game;

	public Entity()
	{
		reset();
	}

	/**
	 * 読みこまれた直後に呼び出されるイベント。
	 * フィールドの初期化を行う。
	 */
	public void reset()
	{
		listeners = new ArrayList<>();
		isDead = false;
	}

	/**
	 * エンティティが追加された直後に呼び出されるイベント。
	 * 全ての子要素をenableしなければならない。
	 */
	public void enable(GameAlmandine2 game)
	{
		this.game = game;
		getConnections().forEach(connection -> connection.enable(this));
	}

	/**
	 * エンティティが除去される直前に呼び出されるイベント。
	 * 全ての子要素をdisableしなければならない。
	 */
	public void disable()
	{
		getConnections().forEach(connection -> connection.disable());
	}

	public abstract void move();

	/**
	 * 戻り値の型引数はこのエンティティの実装型と同じでなければならない。
	 */
	public abstract CardEntity<?> getCardEntity();

	@SuppressWarnings("unchecked")
	public static <E extends Entity> CardEntity<E> getCardEntity(E entity)
	{
		return (CardEntity<E>) entity.getCardEntity();
	}

	public abstract Stream<Connection> getConnections();

	////////////////////////////// Connection Event

	@XStreamOmitField
	private ArrayList<Connection> listeners;

	public void fire(Event event)
	{
		listeners.forEach(listener -> listener.fire(this, event));
	}

	public void hook(Connection connection)
	{
		listeners.add(connection);
	}

	public void unhook(Connection connection)
	{
		listeners.remove(connection);
	}

	public abstract void onConnectionEvent(Entity owner, Event event);

	////////////////////////////// Die

	@XStreamOmitField
	private boolean isDead;

	/**
	 * このエンティティが解放待ちであるかどうかを取得します。
	 */
	public boolean isDead()
	{
		return isDead;
	}

	/**
	 * このエンティティが解放待ちであることをマークします。
	 * また、ハンドラに{@link EventDied}メッセージが送られます。
	 * このアクションは連続して呼び出した場合、最初の1回以外は何も行いません。
	 */
	public void markDie()
	{
		if (!isDead) {
			isDead = true;
			fire(new EventDied());
		}
	}

	////////////////////////////// Handle

	public abstract Stream<IHandle> getHandles();

}
