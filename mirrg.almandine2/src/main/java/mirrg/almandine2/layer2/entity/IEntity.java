package mirrg.almandine2.layer2.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.stream.Stream;

import mirrg.almandine2.layer2.core.GameAlmandine2;

public interface IEntity
{

	// 末端が実装するメソッド

	public void move();

	public void render(Graphics2D graphics);

	// IEntityの時点で実装されるメソッド

	/**
	 * カーソルが乗っているときなどに呼び出される。
	 */
	public void renderAura(Graphics2D graphics, double width, double margin, Color color);

	/**
	 * 座標がこのエンティティに占有されているか否か。
	 * カーソル判定などに使う。
	 */
	public boolean contains(double x, double y);

	/**
	 * 座標とこのエンティティの中心との距離の二乗。
	 */
	public double getDistanceSq(double x, double y);

	//

	/**
	 * ロード直後及びエンティティが生成されて追加された直後に呼び出されるイベント。
	 */
	public void onLoad(GameAlmandine2 game);

	//

	/**
	 * このエンティティが更新されたときに通知されるエンティティとして登録。
	 */
	public void hookUpdatedNeighborhood(IEntity entity);

	/**
	 * このエンティティが更新されたときに通知されるエンティティの登録を解除。
	 */
	public void unhookUpdatedNeighborhood(IEntity entity);

	/**
	 * 関連するエンティティが更新されたときに通知の為に呼び出されるメソッド。
	 */
	public void onUpdatedNeighborhood(IEntity entity);

	//

	/**
	 * このエンティティが削除されたときに通知されるエンティティとして登録。
	 */
	public void hookRemovedNeighborhood(IEntity entity);

	/**
	 * このエンティティが削除されたときに通知されるエンティティの登録を解除。
	 */
	public void unhookRemovedNeighborhood(IEntity entity);

	/**
	 * 関連するエンティティが削除されたときに通知の為に呼び出されるメソッド。
	 */
	public void onRemovedNeighborhood(IEntity entity);

	//

	/**
	 * このエンティティが解放待ちであるかどうかを取得します。
	 */
	public boolean isDead();

	/**
	 * このエンティティが解放待ちであることをマークします。
	 * また、ハンドラに{@link #onRemovedNeighborhood(IEntity)}メッセージが送られます。
	 * このアクションは連続して呼び出した場合、最初の1回以外は何も行いません。
	 */
	public void markDie();

	//

	public Stream<IHandle> getHandles();

}
