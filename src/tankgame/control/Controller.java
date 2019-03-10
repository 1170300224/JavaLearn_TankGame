package tankgame.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;

import tankgame.model.Map;
import tankgame.model.Substance;
import tankgame.model.Tank;
import tankgame.model.Wall;
import tankgame.model.ActiveSubstance.Direction;
import tankgame.view.Window;

public class Controller 
{
	public enum Status{prepare,mapEdit,playing};
	public enum EditorStatus {none,solidWall,fragileWall,birthPointA};
	public static final int DELAY = 50;
	
	
	private Map map;
	
	private Tank tankA;
	
	private ControlPanel controlPanel;
	
	private Window window;

	private Status status;
	private EditorStatus edStat;
	
	public Controller()
	{
		initial();
	}
	
	public void start()
	{
		window.setVisible(true);
	}
	
	private void initial()
	{
		this.map = new Map();
		this.window = new Window();
		this.controlPanel = new ControlPanel(this);
		
		this.window.setComponent(map,controlPanel);
		this.window.addMapViewMouseListener(new MapEditorMouseHandler());
		this.window.setMapViewKeyStroke(this);
		
		statusChange(Status.prepare);
	}
	
	private void playing()
	{
		try {
			
		Thread.sleep(3*DELAY);
		
		addPlayer();
		
		Thread windowThread = new Thread(this.window);
		Thread playAThread = new Thread(this.tankA);
		
		windowThread.start();
		playAThread.start();
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addPlayer()
	{
		this.tankA = new Tank(map.getBirthPointA(),Tank.Kind.player,map);
		this.map.add(tankA);
	}
	
	private void statusChange(Status stat)
	{
		this.status = stat;
		switch(stat)
		{
		case prepare:
			controlPanel.prepareMode();
			break;
		case mapEdit:
			edStat = EditorStatus.none;
			controlPanel.mapEditorMode();
			break;
		case playing:
			controlPanel.playingMode();
			playing();
		}
	}
	
	public class MapEditorListener implements ActionListener
	{
		private EditorStatus stat;
		
		public MapEditorListener(EditorStatus stat)
		{
			this.stat = stat;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Controller.this.edStat = stat;
		}
		
	}
	
	public class StatusChangeListener implements ActionListener
	{
		/**
		 * 目标状态
		 */
		private Status stat;
		
		public StatusChangeListener(Status stat)
		{
			this.stat = stat;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			statusChange(stat);
		}
		
	}
	
	public class MapEditorMouseHandler extends MouseAdapter
	{
		Substance current = null;
		
		@Override
		/**
		 * 双击空白在该位置新建一个相应实体或重设一个出生点
		 * 双击实体删除之
		 */
		public void mouseClicked(MouseEvent event)
		{
			if(Controller.this.status != Status.mapEdit)
				return;
			
			current = map.find(event.getPoint());
			if(current == null && event.getClickCount() >= 2)
			{
				if(Controller.this.edStat == EditorStatus.solidWall)
				{
					map.add(new Wall(Wall.Kind.solid,event.getPoint()));
				}
				else if(Controller.this.edStat == EditorStatus.fragileWall)
				{
					map.add(new Wall(Wall.Kind.fragile,event.getPoint()));
				}
			}
			else if(current != null && event.getClickCount() >= 2)
			{
				map.remove(current);
			}
			
			if(Controller.this.edStat == EditorStatus.birthPointA && event.getClickCount() >= 2)
			{
				map.setBirthPointA(event.getPoint());
			}
			
			window.refurbish();
		}
	}
	
	@SuppressWarnings("serial")
	public class KeyStrokeListener extends AbstractAction
	{
		String key;
		
		public KeyStrokeListener(String key)
		{
			this.key = key;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch(key)
			{
			case "W":
				tankA.setDirection(Direction.up);
				break;
			case "A":
				tankA.setDirection(Direction.left);
				break;
			case "S":
				tankA.setDirection(Direction.down);
				break;
			case "D":
				tankA.setDirection(Direction.right);
				break;
			case "J":
				tankA.shot();
			default:
				break;
			}
			//System.out.println("key " + key + " hit");
		}
	}
}









