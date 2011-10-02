/**
 * 
 */
package imageswarm;

import processing.core.PGraphics;
import toxi.color.TColor;
import toxi.geom.Vec2D;

/**
 * Type - Pixies
 * 
 * @author hari Oct 2, 2011
 * 
 * 
 */
public class Pixies {
	Vec2D position, lastposition, velocity, acceleration;
	float mass, maxforce, maxspeed,maxacceleration = 1f;
	TColor color;
	
	Pixies(Vec2D pos, Vec2D vel, float m, float ms, float mf, int c) {
		position = pos;
		lastposition = position.copy();
		velocity = vel;
		acceleration = Vec2D.ZERO.copy();
		mass = m;
		maxforce = mf;
		maxspeed = ms;
		color = TColor.newARGB(c);
	}
	
	public void update(Vec2D steer) {
		steer.limit(maxforce);
		acceleration.addSelf(steer.scaleSelf(1f/mass)).limit(maxacceleration);
		velocity.addSelf(acceleration).limit(maxspeed);
		
		position.addSelf(velocity);
	}
	
	public void draw(PGraphics p) {
		p.stroke(color.toARGB());
		p.line(lastposition.x, lastposition.y, position.x, position.y);
		
		lastposition.x = position.x;
		lastposition.y = position.y;
	}
}
