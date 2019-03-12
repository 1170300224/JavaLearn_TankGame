package tankgame.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

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
	
	/**
	 * ����ǰ��ͼ���浽�ļ�
	 */
	public void saveMap()
	{
		String mapName = JOptionPane.showInputDialog("�����ļ���(����Ӣ����ĸ�����֣��»���):");
		String file = "map\\" + mapName + ".map";
		
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));) 
		{
			out.writeObject(this.map);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "����ʧ��");
			e.printStackTrace();
			return;
		}
		String filePath = System.getProperty("user.dir") + file;
		JOptionPane.showMessageDialog(null, "����ɹ�:" + filePath);
	}
	
	public void loadMap()
	{	//TODO 	//ֻ����map���ܽ�����⣬��Ҫ������������״̬��Ҫ������
		Path mapPath = Paths.get(System.getProperty("user.dir") + "\\map");
		List<Path> maps = new LinkedList<>();
		
		try(Stream<Path> entries = Files.list(mapPath))
		{
			entries.filter(path -> {
				return path.toString().endsWith(".map");
			}).forEach((p)->{
					maps.add(p);
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		if(maps.isEmpty())
			JOptionPane.showMessageDialog(null, "�޿��õ�ͼ�ļ�");
		String[] mapNames = new String[maps.size()];
		int i = 0;
		for(Path p : maps)
		{
			String s = p.toString();
			mapNames[i++] = s.substring(s.lastIndexOf("\\")+1);
		}
		String choice = (String) JOptionPane.showInputDialog(null, "��ͼ��", "��ͼ��ȡ", JOptionPane.QUESTION_MESSAGE, null, mapNames, mapNames[0]);
		int k = Arrays.binarySearch(mapNames, choice);
		if(k<0)
			return;
		Path theMap = maps.get(k);
		//System.out.println(theMap);
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(theMap.toString())))
		{
			Map newMap = (Map) in.readObject();
			this.map.load(newMap);
			
			this.window.refurbish();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
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
	
	/**
	 * �ڵ�ͼ�༭״̬�¼�������༭״̬�ı仯
	 * @author s
	 *
	 */
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
	
	/**
	 * ������Ϸ״̬�仯
	 * @author s
	 *
	 */
	public class StatusChangeListener implements ActionListener
	{
		/**
		 * Ŀ��״̬
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
	
	/**
	 * ������ͼ�༭״̬�µ�����¼�
	 * @author s
	 *
	 */
	public class MapEditorMouseHandler extends MouseAdapter
	{
		Substance current = null;
		
		@Override
		/**
		 * ˫���հ��ڸ�λ���½�һ����Ӧʵ�������һ��������
		 * ˫��ʵ��ɾ��֮
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
	
	/**
	 * ���������¼�
	 * @author s
	 *
	 */
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
			
			if(Controller.this.status == Status.playing)
			{
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
			}
		}
	}
}









