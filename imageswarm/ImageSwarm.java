package imageswarm;

import javax.media.opengl.GL;

import processing.core.PApplet;
import processing.core.PImage;
import toxi.geom.Vec2D;

public class ImageSwarm extends PApplet {
	private static final long serialVersionUID = 1L;
	
	GL gl;
	PixieSwarm swarm;
	
	String basePath, fname;
	
	long st;
	
	// TODO May be the particle could move to a familiar territory. Out of all the pixels in front, choose the one which is closer to the color
	// and the fear factor. and learning by daring
	// also leaning about daring or playing safe by remembering colours!
	
	public void setup() {
		int wid = 600, hei = 600;
		
		basePath = "/Users/hari/Work/code/ImageSwarm/src/data/Vangogh/";
		fname = "Vincent_Van_Gogh_0013.jpg";
		PImage img = loadImage(basePath + fname);
		
		if (img.width > img.height) {
			wid = 1000;
			hei = (int) (1000 * img.height / (float) img.width);
		} else {
			hei = 1000;
			wid = (int) (1000 * img.width / (float) img.height);
		}
		
		size(wid, hei, JAVA2D);
		smooth();
		img.resize(width, height);
		
		swarm = new PixieSwarm(this, img);
		img.loadPixels();
		for (float i = 0; i < img.width; i += 0.25) {
			swarm.add(new Pixies(new Vec2D(i, 0), new Vec2D(0, 2f), 0.1f, 1, 0.1f, img.pixels[(int) i]));
		}
		
		strokeWeight(0.25f);
		background(0);
		
		st = System.nanoTime();
	}
	
	public void draw() {
		//		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
		//		strokeWeight(0.25f);
		if (swarm.update())
			swarm.draw();
		else {
			this.get().save(basePath + "S_" + fname);
			exit();
		}
		
		if ((System.nanoTime() - st) >= 5000000000l) {
			println(swarm.size());
			st = System.nanoTime();
		}
	}
	
	public void keyPressed() {
		switch (key) {
		case 'S':
			this.get().save(basePath + "S_" + fname);
			break;
		
		default:
			break;
		}
		
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { imageswarm.ImageSwarm.class.getName() });
	}
	
}
