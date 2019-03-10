package tankgame.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import tankgame.control.Controller;
import tankgame.control.Controller.MapEditorMouseHandler;
import tankgame.model.Map;
import tankgame.model.Tank;
import tankgame.model.Wall;

@SuppressWarnings("serial")
public class MapView extends JComponent
{
	public static final int WIDTH = 600;
	public static final int HEIGHT = 650;
	
	private Map map;
	
	public MapView(Map map)
	{
		this.setSize(WIDTH, HEIGHT);
		
		this.map = map;
	}
	
	@Override
	public void paintComponent(Graphics go)
	{
		Graphics2D g = (Graphics2D) go;
		
		drawWall(g);
		drawBirthPoint(g);
		drawTank(g);
		
		showFrame(g);
	}
	
	public void addMouseActionListener(MapEditorMouseHandler handler)
	{
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
	}
	
	public void setKeyStroke(Controller controller)
	{
		InputMap imap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imap.put(KeyStroke.getKeyStroke("W"), "W");
		imap.put(KeyStroke.getKeyStroke("A"), "A");
		imap.put(KeyStroke.getKeyStroke("S"), "S");
		imap.put(KeyStroke.getKeyStroke("D"), "D");
		
		ActionMap amap = this.getActionMap();
		amap.put("W", controller.new KeyStrokeListener("W"));
		amap.put("A", controller.new KeyStrokeListener("A"));
		amap.put("S", controller.new KeyStrokeListener("S"));
		amap.put("D", controller.new KeyStrokeListener("D"));
	}
	
	private void drawWall(Graphics2D g)
	{
		Set<Wall> walls = map.getWalls();
		
		for(Wall w : walls)
		{
			WallView v = new WallView(w);
			v.draw(g);
		}
	}
	
	private void drawBirthPoint(Graphics2D g)
	{
		Point pa = map.getBirthPointA();
		if(pa == null)
			return;
		
		final int r = 5;	//°ë¾¶
		
		Ellipse2D ea = new Ellipse2D.Double();
		ea.setFrameFromCenter(pa, new Point((int)pa.getX()+r,(int)pa.getY()+r));
		
		g.setColor(Color.YELLOW);
		g.fill(ea);
	}
	
	private void drawTank(Graphics2D g)
	{
		Set<Tank> tanks = map.getTanks();
		
		for(Tank t : tanks)
		{
			TankView v = new TankView(t);
			v.draw(g);
		}
	}
	
	private void showFrame(Graphics2D g)
	{
		g.setColor(Color.RED);
		g.draw(new Rectangle2D.Double(0,1,this.getSize().getWidth()-1,this.getSize().getHeight()-2));
	}

}
