/**
 * 
 */
package imageswarm;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphicsJava2D;
import processing.core.PImage;
import toxi.color.TColor;
import toxi.geom.Rect;
import toxi.geom.Vec2D;

/**
 * Type - PixieSwarm
 * 
 * @author hari Sep 27, 2011
 * 
 * 
 */
public class PixieSwarm extends ArrayList<Pixies> {
	private static final long serialVersionUID = 1L;
	
	final static float SATURATION_MULT = 2f;
	
	PApplet parent;
	PImage image;
	
	Rect bounds;
	
	int[] pixels;
	
	
	public PixieSwarm(PApplet p, PImage img) {
		super();
		parent = p;
		image = img;
		
		image.loadPixels();
		//		pixels = new int[image.pixels.length];
		pixels = image.pixels.clone();
		
		bounds = new Rect(0, 0, img.width - 1, img.height - 1);
	}
	
	public boolean update() {
		Vec2D steer = Vec2D.ZERO.copy();
		TColor c1 = TColor.WHITE.copy();
		
		if (this.size() < 1)
			return false;
		
		for (int i = 0; i < this.size(); i++) {
			Pixies p = this.get(i);
			
			if (!p.position.isInRectangle(bounds)) {
				this.remove(i);
				continue;
			}
			
			c1.setARGB(pixels[(int) (Math.floor(p.position.y) * image.width + Math.floor(p.position.x))]);
			
			// Drift behaviour
			steer = new Vec2D((c1.saturation() - p.color.saturation()), (float) (c1.hue() * Math.PI * 2.0)).toCartesian();
//			steer = new Vec2D((p.color.saturation() - c1.saturation()), (float) (c1.hue() * Math.PI * 2.0)).toCartesian();
			
			p.color.setARGB(c1.toARGB());
			p.update(steer);
		}
		
		return true;
	}
	
	public void draw() {
		for (Pixies p : this)
			p.draw((PGraphicsJava2D) parent.g);
	}
}