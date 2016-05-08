package mirrg.almandine2.layer2.entity;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.connection.Connection;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.Event;
import mirrg.almandine2.layer2.entity.connection.EventDied;
import mirrg.almandine2.layer2.entity.view.View;

public abstract class Entity<E extends Entity<E, V>, V extends View>
{

	@XStreamOmitField
	protected GameAlmandine2 game;

	public Entity()
	{
		reset();
	}

	@SuppressWarnings("unchecked")
	public E getThis()
	{
		return (E) this;
	}

	/**
	 * 読みこまれた直後に呼び出されるイベント。
	 * フィールドの初期化を行う。
	 */
	public void reset()
	{
		connectors = new ArrayList<>();
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
	public abstract CardEntity<E, V> getCardEntity();

	public V getView()
	{
		return getCardEntity().getView(getThis());
	}

	public abstract Stream<Connection> getConnections();

	////////////////////////////// Connection Event

	@XStreamOmitField
	private ArrayList<Connection> connectors;

	public Stream<Connection> getConnectors()
	{
		return connectors.stream();
	}

	public void fire(Event event)
	{
		// リスナがイベント内でリスニングを解除できるように配列に取っておく
		Stream.of(connectors.stream().toArray(Connection[]::new))
			.forEach(connector -> connector.fire(this, event));
	}

	public void connect(Connection connection)
	{
		connectors.add(connection);
	}

	public void unconnect(Connection connection)
	{
		connectors.remove(connection);
	}

	public void onConnectionEvent(Entity<?, ?> owner, Event event)
	{

	}

	protected void dieIfNotConnectableToPoint(Entity<?, ?> owner, Event event, Connection connection, Predicate<Connection> predicate, Consumer<Connection> setter)
	{
		if (event instanceof EventDied) {

			if (connection.getEntities()
				.anyMatch(entity -> entity == owner)) {

				Connection connection2 = new ConnectionPoint(new Point2D.Double(
					connection.getPoint().x,
					connection.getPoint().y));
				if (predicate.test(connection2)) {
					setter.accept(connection2);
				} else {
					markDie();
				}

			}

		}
	}

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
