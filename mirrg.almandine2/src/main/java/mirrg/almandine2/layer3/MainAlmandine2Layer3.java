package mirrg.almandine2.layer3;

import static java.awt.event.KeyEvent.*;

import java.awt.CardLayout;
import java.awt.geom.Point2D;
import java.util.function.Supplier;

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
import mirrg.almandine2.layer2.tool.Tool;
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
import mirrg.almandine2.layer3.entities.redstone.station.CardEntityWireRedstoneRail;
import mirrg.almandine2.layer3.entities.redstone.station.EntityWireRedstoneRail;
import mirrg.almandine2.layer3.entities.slab.CardEntityCartSlot;
import mirrg.almandine2.layer3.entities.slab.CardEntityPipeSlab;
import mirrg.almandine2.layer3.entities.slab.CardEntitySlot;
import mirrg.almandine2.layer3.entities.slab.CardEntityStationSlot;
import mirrg.almandine2.layer3.entities.slab.EntitySlot;
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

			GameAlmandine2 game;

			private void r(Supplier<Tool> supplier, Integer shortcutKey)
			{
				game.registerCommand(new CommandTool(supplier, shortcutKey));
			}

			@Override
			public void registerContents(GameAlmandine2 game)
			{
				this.game = game;

				r(() -> null, VK_ESCAPE);
				r(ToolDelete::new, VK_B);
				r(ToolMove::new, VK_M);

				r(() -> new ToolPutBlock(CardEntityCounter.INSTANCE), VK_Z);

				r(() -> new ToolPutBlock(CardEntityGateRedstone.AND), VK_A);
				r(() -> new ToolPutBlock(CardEntityGateRedstone.OR), VK_S);
				r(() -> new ToolPutBlock(CardEntityGateRedstone.NAND), VK_D);
				r(() -> new ToolPutBlock(CardEntityGateRedstone.NOR), VK_F);
				r(() -> new ToolPutBlock(CardEntityGateRedstone.XOR), VK_G);
				r(() -> new ToolPutWire(CardEntityWireRedstone.INSTANCE), VK_H);

				r(() -> new ToolPutWire(CardEntityWireRedstoneRail.INSTANCE), VK_J);

				r(() -> new ToolPutBlock(CardEntityStation.INSTANCE), VK_Q);
				r(() -> new ToolPutWire(CardEntityRail.INSTANCE), VK_W);
				r(() -> new ToolPutBlock(CardEntityCart.INSTANCE), VK_E);

				r(() -> new ToolPutBlock(CardEntitySlot.INSTANCE), VK_R);
				r(() -> new ToolPutBlock(CardEntityCartSlot.INSTANCE), VK_T);
				r(() -> new ToolPutBlock(CardEntityStationSlot.INSTANCE), VK_Y);
				r(() -> new ToolPutWire(CardEntityPipeSlab.INSTANCE), VK_U);

				game.registerCommand(new CommandAction(game2 -> {

					openDialogXML();
					synchronized (game2) {
						textAreaXML.setText(createXStream(game2).toXML(game2.data));
					}

				} , VK_F1));
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

				} , VK_F2));

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
		xStream.alias("entity::redstone::station::rail", EntityWireRedstoneRail.class);

		xStream.alias("entity::slab::slot", EntitySlot.class);

		// TODO
		return xStream;
	}

}
