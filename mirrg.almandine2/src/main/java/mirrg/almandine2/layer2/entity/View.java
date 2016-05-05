package mirrg.almandine2.layer2.entity;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class View<T>
{

	public abstract void render(T entity, Graphics2D graphics);

	public abstract void renderAura(T entity, Graphics2D graphics, double width, double margin, Color color);

	/**
	 * 座標とエンティティの中心との距離の2乗。
	 */
	public abstract double getDistanceCenterSq(T entity, double x, double y);

	/**
	 * 座標とエンティティの端との距離。
	 * 座標が内部にめり込んでいる場合、0以下の何かしらの値を返す。
	 */
	public abstract double getDistanceEdge(T entity, double x, double y);

}
