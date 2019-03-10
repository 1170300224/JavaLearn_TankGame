package tankgame.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import tankgame.control.ControlPanel;
import tankgame.control.Controller;
import tankgame.control.Controller.MapEditorMouseHandler;
import tankgame.model.Map;


@SuppressWarnings("serial")
public class Window extends JFrame  implements Runnable
{
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;
	
	private MapView mapView;
	private GameMessageView messageView;
	private ControlPanel controlPanel;
	
	public Window()
	{
		this.setSize(WIDTH,HEIGHT);
		this.setLocationByPlatform(true);
		this.setResizable(false);
		
		this.setLayout(null);
	
		this.setTitle("Tank Game");
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("icon.gif");
		this.setIconImage(img);
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setComponent(Map map,ControlPanel controlPanel)
	{
		this.mapView = new MapView(map);
		this.messageView = new GameMessageView();
		this.controlPanel = controlPanel;
		
		this.add(mapView);
		mapView.setLocation(0, 0);
		this.add(messageView);
		messageView.setLocation(mapView.WIDTH+2, 0);
		this.add(controlPanel);
		controlPanel.setLocation(mapView.WIDTH+2, messageView.HEIGHT+2);
	}
	
	public void addMapViewMouseListener(MapEditorMouseHandler handler)
	{
		this.mapView.addMouseActionListener(handler);
	}
	
	public void setMapViewKeyStroke(Controller controller)
	{
		this.mapView.setKeyStroke(controller);
	}
	
	public void refurbish()
	{
		this.mapView.repaint();
		this.messageView.repaint();
	}
	
	@Override
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(Controller.DELAY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.refurbish();
		}
	}
	
}
