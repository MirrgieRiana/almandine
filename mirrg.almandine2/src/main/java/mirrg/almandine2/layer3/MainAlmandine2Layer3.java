package mirrg.almandine2.layer3;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.thoughtworks.xstream.XStream;

import mirrg.almandine2.layer1.IGameAlmandine2;
import mirrg.almandine2.layer2.FrameAlmandine2Layer2;
import mirrg.almandine2.layer2.core.DataAlmandine2;
import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.tool.CardTool;
import mirrg.almandine2.layer2.tool.Point;
import mirrg.almandine2.layer3.entities.CardFurnitureCounter;
import mirrg.almandine2.layer3.entities.cart.CardCart;
import mirrg.almandine2.layer3.entities.cart.EntityCartSlab;
import mirrg.almandine2.layer3.entities.cart.station.CardFurnitureStation;
import mirrg.almandine2.layer3.entities.cart.station.CardWireRail;
import mirrg.almandine2.layer3.entities.redstone.CardFurnitureRedstone;
import mirrg.almandine2.layer3.entities.redstone.CardWireRedstoneWire;
import mirrg.almandine2.layer3.entities.redstone.EntityFurnitureRedstoneGate;
import mirrg.almandine2.layer3.tools.ToolDelete;
import mirrg.almandine2.layer3.tools.ToolMove;
import mirrg.almandine2.layer3.tools.ToolPutCart;
import mirrg.almandine2.layer3.tools.ToolPutFurniture;
import mirrg.almandine2.layer3.tools.ToolPutWire;

public class MainAlmandine2Layer3
{

	private static FrameAlmandine2Layer2 frameMain;
	private static JDialog dialogXML;
	private static JTextArea textAreaXML;

	public static void main(String[] args)
	{
		frameMain = new FrameAlmandine2Layer2() {

			@Override
			public void registerContents(GameAlmandine2 game)
			{

				game.registerCardTool(new CardTool(() -> null, KeyEvent.VK_ESCAPE));
				game.registerCardTool(new CardTool(ToolDelete::new, KeyEvent.VK_B));
				game.registerCardTool(new CardTool(ToolMove::new, KeyEvent.VK_M));

				game.registerCardTool(new CardTool(() -> new ToolPutFurniture(CardFurnitureCounter.INSTANCE), KeyEvent.VK_Z));

				game.registerCardTool(new CardTool(() -> new ToolPutFurniture(CardFurnitureRedstone.AND), KeyEvent.VK_A));
				game.registerCardTool(new CardTool(() -> new ToolPutFurniture(CardFurnitureRedstone.OR), KeyEvent.VK_S));
				game.registerCardTool(new CardTool(() -> new ToolPutFurniture(CardFurnitureRedstone.NAND), KeyEvent.VK_D));
				game.registerCardTool(new CardTool(() -> new ToolPutFurniture(CardFurnitureRedstone.NOR), KeyEvent.VK_F));
				game.registerCardTool(new CardTool(() -> new ToolPutFurniture(CardFurnitureRedstone.XOR), KeyEvent.VK_G));
				game.registerCardTool(new CardTool(() -> new ToolPutWire(CardWireRedstoneWire.INSTANCE), KeyEvent.VK_H));

				game.registerCardTool(new CardTool(() -> new ToolPutFurniture(CardFurnitureStation.INSTANCE), KeyEvent.VK_Q));
				game.registerCardTool(new CardTool(() -> new ToolPutWire(CardWireRail.INSTANCE), KeyEvent.VK_W));
				game.registerCardTool(new CardTool(() -> new ToolPutCart(CardCart.INSTANCE), KeyEvent.VK_E));

				game.registerCardTool(new CardTool(() -> {

					openDialogXML();
					synchronized (game) {
						textAreaXML.setText(createXStream(game).toXML(game.data));
					}

					return null;
				} , KeyEvent.VK_F1));
				game.registerCardTool(new CardTool(() -> {

					synchronized (game) {
						DataAlmandine2 data;
						try {
							data = (DataAlmandine2) createXStream(game).fromXML(textAreaXML.getText());
							data.onLoad(game);
							game.data = data;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					return null;
				} , KeyEvent.VK_F2));

			}

			@Override
			public void initComponents(IGameAlmandine2 game)
			{
				JMenuBar menuBar = new JMenuBar();
				JMenu menu = new JMenu("ファイル(F)");
				menu.setMnemonic('F');
				JMenuItem menuItem = new JMenuItem("XMLウィンドウ(X)");
				menuItem.setMnemonic('X');
				menuItem.addActionListener(e -> {
					openDialogXML();
				});
				menu.add(menuItem);
				menuBar.add(menu);
				setJMenuBar(menuBar);

				super.initComponents(game);
			}

		};

		frameMain.setTitle("Almandine2");
		frameMain.start();
	}

	private static void openDialogXML()
	{
		if (dialogXML == null) {
			dialogXML = new JDialog(frameMain);
			textAreaXML = new JTextArea();
			dialogXML.add(new JScrollPane(textAreaXML));
			dialogXML.setLayout(new CardLayout());
			dialogXML.setSize(400, 400);
			dialogXML.setLocationByPlatform(true);
		}

		dialogXML.setVisible(true);
	}

	private static XStream createXStream(GameAlmandine2 game)
	{
		XStream xStream = game.createXStream();
		xStream.alias("point", Point.class);

		xStream.alias("entity::counter", CardFurnitureCounter.Entity.class);

		xStream.alias("entity::redstone::gate", EntityFurnitureRedstoneGate.class);
		xStream.alias("entity::redstone::wire", CardWireRedstoneWire.Entity.class);

		xStream.alias("entity::station", CardFurnitureStation.Entity.class);
		xStream.alias("entity::rail", CardWireRail.Entity.class);
		xStream.alias("entity::cart", EntityCartSlab.class);

		// TODO
		return xStream;
	}

}
