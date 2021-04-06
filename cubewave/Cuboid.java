package cubewave;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Cuboid {
	
	private static final Color color = Color.CYAN.darker();
	private static final int width = 12, maxHeight = 150, minHeight = 50;
	private static final double angle = Math.toRadians(32);
	private static final int horWidth = (int)Math.round(width*Math.cos(angle)), 
			verWidth = (int)Math.round(width*Math.sin(angle));
	
	private int x, y, height;
	private double offset;
	
	public Cuboid(int x, int y){
		this.x = x;
		this.y = y;
		offset = Math.sqrt(x*x + y*y);
	}
	
	public void update(double time){
		height = (int)Math.round((maxHeight-minHeight)*(Math.sin(2.5*time - 0.6*offset) + 1)/2 + minHeight);
	}
	
	public void render(Graphics2D g) {
		int rx = (int)(x*horWidth + y*horWidth) + Panel.size/2;
		int ry = (int)(x*verWidth - y*verWidth - height/2) + Panel.size/2;
		
		g.setColor(color);
		
		// Top quad
		Polygon p = new Polygon();
		p.addPoint(rx-horWidth, ry);
		p.addPoint(rx, ry-verWidth);
		p.addPoint(rx+horWidth, ry);
		p.addPoint(rx, ry+verWidth);
		g.fill(p);
		
		g.setColor(color.darker());
		
		// Left quad
		p = new Polygon();
		p.addPoint(rx-horWidth, ry);
		p.addPoint(rx-horWidth, ry + height);
		p.addPoint(rx, ry + height + verWidth);
		p.addPoint(rx, ry + verWidth);
		g.fill(p);
		
		g.setColor(color.darker().darker());
		
		// Right quad
		p = new Polygon();
		p.addPoint(rx+horWidth, ry);
		p.addPoint(rx+horWidth, ry + height);
		p.addPoint(rx, ry + height + verWidth);
		p.addPoint(rx, ry + verWidth);
		g.fill(p);
	}
}
