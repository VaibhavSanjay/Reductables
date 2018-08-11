/**
 * 
 */
package com.reductables.mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 * @author tvsan
 *
 */
public class Player extends GameObject{
	  private long rotation = 5;
	  private int timeToRotate = 0;
	  private int rendersPerRotation = 40;
	  private int rendersPerRotationCounter = 0;
	  private boolean keepRotating = true;
	  private Handler handler;
	  private HeadsUpDisplay hud;
	  private int invincibilityFrames;
	  public Player(int x, int y, ID id, Handler handler, HeadsUpDisplay hud) {
		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
	}
	
	
	public void tick () {
		x += velX;
		y += velY;
		x = Game.clamp(x,  0,  Game.WIDTH - 40);
		y = Game.clamp(y,  0,  Game.HEIGHT - 65);
		
		if (hud.getCollisionCounter() == 0) {
			if (invincibilityFrames == 0) {
				collision();
			} else {
				invincibilityFrames--;
			}
		}
	}
		
		
	
	public void render (Graphics g) {
		if (timeToRotate == rendersPerRotation) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform old = g2d.getTransform();
		g2d.rotate(Math.toRadians(rotation), x + 16, y + 16);
		g2d.setColor(Color.white);
		g2d.fillRect((int)x,  (int)y, 32, 32);
		g2d.setTransform(old);
		rotation += 10;
		timeToRotate = 0;
		rendersPerRotationCounter++;
		if (rendersPerRotationCounter == 2 && keepRotating) {
			rendersPerRotation--;
			rendersPerRotationCounter = 0;
			if (rendersPerRotation == 5) {
				keepRotating = false;
			}
		}
		} else {
			Graphics2D g2d = (Graphics2D)g;
			AffineTransform old = g2d.getTransform();
			g2d.rotate(Math.toRadians(rotation), x + 16, y + 16);
			g2d.setColor(Color.white);
			g2d.fillRect((int)x,  (int)y, 32, 32);
			g2d.setTransform(old);
			timeToRotate++;
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public int getAnswer () {
		if (x <= 300 && y <= 225) {
			return 0;
		} else if (x <= 300 && y > 225) {
			return 1;
		} else if (x > 300 && y <= 225) {
			return 2;
		} else if (x > 300 && y > 225) {
			return 3;
		} else {
			return 0;
		}
	}
	
	private void collision () {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ID.TrailEnemy || tempObject.getId() == ID.TrashEnemy || tempObject.getId() == ID.FollowEnemy || tempObject.getId() == ID.FallEnemy) {
				if (getBounds().intersects(tempObject.getBounds())) {
					hud.subtractHealth();
					hud.setCollisionCounter();
					invincibilityFrames = 100;
					break;
				}
			}
		}
	}

}
