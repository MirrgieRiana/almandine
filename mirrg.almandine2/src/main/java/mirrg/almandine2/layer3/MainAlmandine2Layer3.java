package mirrg.almandine2.layer3;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.thoughtworks.xstream.XStream;

import mirrg.almandine2.layer1.IGameAlmandine2;
import mirrg.almandine2.layer2.FrameAlmandine2Layer2;
import mirrg.almandine2.layer2.command.CommandAction;
import mirrg.almandine2.layer2.command.CommandTool;
import mirrg.almandine2.layer2.core.DataAlmandine2;
import mirrg.almandine2.layer2.core.GameAlmandine2;
import mirrg.almandine2.layer2.entity.connection.ConnectionAnchor;
import mirrg.almandine2.layer2.entity.connection.ConnectionBlock;
import mirrg.almandine2.layer2.entity.connection.ConnectionPoint;
import mirrg.almandine2.layer2.entity.connection.ConnectionTraffic;
import mirrg.almandine2.layer2.tool.ToolDelete;
import mirrg.almandine2.layer2.tool.ToolMove;
import mirrg.almandine2.layer2.tool.ToolPutBlock;
import mirrg.almandine2.layer2.tool.ToolPutWire;
import mirrg.almandine2.layer3.entities.counter.CardEntityCounter;
import mirrg.almandine2.layer3.entities.counter.EntityCounter;
import mirrg.almandine2.layer3.entities.redstone.CardEntityGateRedstone;
import mirrg.almandine2.layer3.entities.redstone.CardEntityWireRedstone;
import mirrg.almandine2.layer3.entities.redstone.EntityGateRedstone;
import mirrg.almandine2.layer3.entities.redstone.EntityWireRedstone;
import mirrg.almandine2.layer3.entities.station.CardEntityCart;
import mirrg.almandine2.layer3.entities.station.CardEntityRail;
import mirrg.almandine2.layer3.entities.station.CardEntityStation;
import mirrg.almandine2.layer3.entities.station.EntityCart;
import mirrg.almandine2.layer3.entities.station.EntityRail;
import mirrg.almandine2.layer3.entities.station.EntityStation;

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

				game.registerCommand(new CommandTool(() -> null, KeyEvent.VK_ESCAPE));
				game.registerCommand(new CommandTool(ToolDelete::new, KeyEvent.VK_B));
				game.registerCommand(new CommandTool(ToolMove::new, KeyEvent.VK_M));

				game.registerCommand(new CommandTool(() -> new ToolPutBlock(CardEntityCounter.INSTANCE), KeyEvent.VK_Z));

				game.registerCommand(new CommandTool(() -> new ToolPutBlock(CardEntityGateRedstone.AND), KeyEvent.VK_A));
				game.registerCommand(new CommandTool(() -> new ToolPutBlock(CardEntityGateRedstone.OR), KeyEvent.VK_S));
				game.registerCommand(new CommandTool(() -> new ToolPutBlock(CardEntityGateRedstone.NAND), KeyEvent.VK_D));
				game.registerCommand(new CommandTool(() -> new ToolPutBlock(CardEntityGateRedstone.NOR), KeyEvent.VK_F));
				game.registerCommand(new CommandTool(() -> new ToolPutBlock(CardEntityGateRedstone.XOR), KeyEvent.VK_G));
				game.registerCommand(new CommandTool(() -> new ToolPutWire(CardEntityWireRedstone.INSTANCE), KeyEvent.VK_H));

				game.registerCommand(new CommandTool(() -> new ToolPutBlock(CardEntityStation.INSTANCE), KeyEvent.VK_Q));
				game.registerCommand(new CommandTool(() -> new ToolPutWire(CardEntityRail.INSTANCE), KeyEvent.VK_W));
				game.registerCommand(new CommandTool(() -> new ToolPutBlock(CardEntityCart.INSTANCE), KeyEvent.VK_E));

				game.registerCommand(new CommandAction(game2 -> {

					openDialogXML();
					synchronized (game2) {
						textAreaXML.setText(createXStream(game2).toXML(game2.data));
					}

				} , KeyEvent.VK_F1));
				game.registerCommand(new CommandAction(game2 -> {

					DataAlmandine2 data = null;
					try {
						data = (DataAlmandine2) createXStream(game2).fromXML(textAreaXML.getText());
						data.onLoad(game2);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (data != null) {
						synchronized (game2) {
							game2.data = data;
						}
					}

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
		xStream.alias("point", Point2D.Double.class);
		xStream.alias("connection::point", ConnectionPoint.class);
		xStream.alias("connection::block", ConnectionBlock.class);
		xStream.alias("connection::traffic", ConnectionTraffic.class);
		xStream.alias("connection::anchor", ConnectionAnchor.class);
		xStream.alias("entity::counter", EntityCounter.class);
		xStream.alias("entity::resdtone::gate", EntityGateRedstone.class);
		xStream.alias("entity::resdtone::wire", EntityWireRedstone.class);
		xStream.alias("entity::station::station", EntityStation.class);
		xStream.alias("entity::station::rail", EntityRail.class);
		xStream.alias("entity::station::cart", EntityCart.class);

		/*
		xStream.alias("entity::counter", CardFurnitureCounter.Entity.class);
		
		xStream.alias("entity::redstone::gate", EntityFurnitureRedstoneGate.class);
		xStream.alias("entity::redstone::wire", CardWireRedstoneWire.Entity.class);
		
		xStream.alias("entity::station", CardFurnitureStation.Entity.class);
		xStream.alias("entity::rail", CardWireRail.Entity.class);
		xStream.alias("entity::cart", EntityCartSlab.class);
		*/

		// TODO
		return xStream;
	}

}
