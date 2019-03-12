package tankgame.control;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JPanel;

import tankgame.control.Controller.EditorStatus;
import tankgame.control.Controller.Status;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel 
{
	public static final int WIDTH = 280;
	public static final int HEIGHT = 300;
	
	//private List<JButton> buttons = new ArrayList<>();
	private Controller controller;
	
	private JPanel[] rows = new JPanel[4];
	
	public ControlPanel(Controller controller)
	{
		this.setSize(WIDTH, HEIGHT);
		this.controller = controller;
		
		this.setLayout(new GridLayout(4,1,0,10));
		for(int i = 0; i<4; i++)
		{
			this.rows[i] = new JPanel();
			this.add(rows[i]);
		}
	}
	
	public void prepareMode()
	{
		clearButtons();
		
		JButton editorModeButton = new JButton("��ͼ�༭");
		editorModeButton.addActionListener(controller.new StatusChangeListener(Status.mapEdit));
		
		addButton(0,editorModeButton);
		
		JButton loadMapButton = new JButton("��ȡ��ͼ");
		loadMapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadMap();
			}
		});
		
		addButton(1,loadMapButton);
		
		JButton playingModeButton = new JButton("��ʼ��Ϸ");
		playingModeButton.addActionListener(controller.new StatusChangeListener(Status.playing));
		
		addButton(3,playingModeButton);
		
		this.repaint();
	}
	
	public void mapEditorMode()
	{
		clearButtons();
		
		JButton solidWallButton = new JButton("��ǽ");
		solidWallButton.addActionListener(controller.new MapEditorListener(EditorStatus.solidWall));
		JButton fragileWallButton = new JButton("��ǽ");
		fragileWallButton.addActionListener(controller.new MapEditorListener(EditorStatus.fragileWall));
		
		addButton(0,solidWallButton,fragileWallButton);
		
		JButton birthPointButton = new JButton("������A");
		birthPointButton.addActionListener(controller.new MapEditorListener(EditorStatus.birthPointA));
		
		addButton(1,birthPointButton);
		
		JButton saveMapButton = new JButton("�����ͼ");
		saveMapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveMap();
			}
		});
				
		JButton confirmButton = new JButton("ȷ��");
		confirmButton.addActionListener(controller.new StatusChangeListener(Status.prepare));
		
		addButton(3,saveMapButton,confirmButton);

		this.repaint();
	}
	
	public void playingMode()
	{
		clearButtons();
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics go)
	{
		Graphics2D g = (Graphics2D) go;
		
		showFrame(g);
	}
	
	private void addButton(int row,JButton... buttons)
	{
		for(JButton button:buttons)
		{
			//this.buttons.add(button);
			this.rows[row].add(button);
		}
	}
	
	private void clearButtons()
	{
		for(int i = 0; i<4; i++)
		{
			this.rows[i].removeAll();
		}
		//buttons.clear();
		
		this.setVisible(false);	//ˢ�£�������ɾ��������ʾ������ӵ��޷���ʾ
		this.setVisible(true);
	}
	
	private void showFrame(Graphics2D g)
	{
		g.setColor(Color.YELLOW);
		g.draw(new Rectangle2D.Double(0,1,this.getSize().getWidth()-1,this.getSize().getHeight()-2));
	}
	
}












