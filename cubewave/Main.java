package cubewave;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Main {
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeAndWait(new Runnable(){
			public void run() {
				init();
			}});
	}
	
	static void init(){
		JFrame frame = new JFrame("Cube Wave");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		Panel panel = new Panel();
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final int n = 19;
	private static final double fps = 30;
	public static final int size = 400;
	
	private double time = 0;
	private Cuboid[][] cubes = new Cuboid[n][n];
	
	public Panel(){
		setBackground(new Color(51, 51, 51));
		setPreferredSize(new Dimension(size, size));
		for(int i = -n/2; i <= n/2; i++)
			for(int j = -n/2; j <= n/2; j++)
				cubes[i + n/2][j + n/2] = new Cuboid(i, j);
		Repeater r = new Repeater();
		r.execute();
	}
	
	public void update(){
		time += 1/fps;
		for(Cuboid[] a:cubes)
			for(Cuboid c:a)
				c.update(time);
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		for(int y = n-1; y >= 0; y--)
			for(int x = 0; x < n; x++)
				cubes[x][y].render(g2d);
	}
	
	class Repeater extends SwingWorker<Void, Void> {
		
		protected Void doInBackground() throws InterruptedException {
			long downTime = 0;
			int frames = 0;
			while(true){
				long start = System.currentTimeMillis();
				update();
				repaint();
				long dt = System.currentTimeMillis() - start;
				downTime += (long) (1000/fps - dt);
				frames++;
				if(frames >= fps) {
					frames = 0;
					System.out.println("Downtime: " + downTime/10 + "%");
					downTime = 0;
				}
				if(dt < 1000/fps)
					try {
						Thread.sleep((long) (1000/fps - dt));
					} catch (InterruptedException e) {}
			}
		}
	};
	
}
