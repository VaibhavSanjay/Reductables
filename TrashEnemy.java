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
public class TrashEnemy extends Enemy{
	
	private int bounceCount = 0;
	private boolean keepBouncing = true;
	private int startPointDirection;
	private int startPointX;
	private int startPointY;
	private float startVelX;
	private float startVelY;
	private int rotation = 5;
	private int rendersPerRotation = 5;
	private int timeToRotate = 0;
	private boolean highRotationSpeed = true;
	private int waitBeforeRespawn;
	private boolean notInGame = false;
	private Handler handler;
	public static int enemiesCreated = 0;
	
	public TrashEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velX = 4;
		velY = 4;
		enemiesCreated++;
		totalEnemies++;
	}
	
	public void checkRotationSpeed() {
		if (highRotationSpeed) {
			rendersPerRotation = 75;
			timeToRotate = 0;
			rotation = 0;
			highRotationSpeed = false;
		} else if (highRotationSpeed == false){
			rendersPerRotation = 5;
			timeToRotate = 0;
			rotation = 0;
			highRotationSpeed = true;
		}
	}
	
	@Override
	public void tick() {
		if (notInGame) {
			if (waitBeforeRespawn != 0) {
			waitBeforeRespawn--;
			}
			if (waitBeforeRespawn == 0 && questionDone) {
				notInGame = false;
				this.setX(startPointX);
				this.setY(startPointY);
				velX = startVelX;
				velY = startVelY;
				bounceCount = 0;
				keepBouncing = true;
			}
		} else {
		x += velX;
		y += velY;
		if ((y <= 0 || y >= Game.HEIGHT - 32) && keepBouncing) {
			velY *= -1;
			bounceCount++;
			this.checkRotationSpeed();
		}
		
		if ((x <= 0 || x >= Game.WIDTH - 16) && keepBouncing) {
			velX *= -1;
			bounceCount++;
			this.checkRotationSpeed();
		}
		
		handler.addObject(new TrashEnemyTrail(x, y, ID.Trail, Color.red, 16, 16, 0.01f, handler));
		
		if ((y <= 0 || y >= Game.HEIGHT - 32 || x <= 0 || x >= Game.WIDTH - 16) && keepBouncing == false) {
			startPointDirection = (int) Math.floor(Math.random()*4);
			if (startPointDirection == 0) {
				startPointX = (int) Math.floor(Math.random()*Game.WIDTH*5/7 + Game.WIDTH/7);
				startPointY = 33;
				startVelX = velX;
				startVelY = 4;
				this.setX(-50);
				this.setY(-50);
				this.setVelY(0);
				this.setVelX(0);
				waitBeforeRespawn = 100;
				notInGame = true;
			} else if (startPointDirection == 1) {
				startPointX = (int) Math.floor(Math.random()*Game.WIDTH*5/7 + Game.WIDTH/7);
				startPointY = Game.HEIGHT - 33;
				startVelX = velX;
				startVelY = -4;
				this.setX(-50);
				this.setY(-50);
				this.setVelY(0);
				this.setVelX(0);
				waitBeforeRespawn = 100;
				notInGame = true;
			} else if (startPointDirection == 2) {
				startPointY = (int) Math.floor(Math.random()*Game.HEIGHT*3/5 + Game.HEIGHT/10);
				startPointX = 33;
				startVelX = 5;
				startVelY = velY;
				this.setX(-50);
				this.setY(-50);
				this.setVelY(0);
				this.setVelX(0);
				waitBeforeRespawn = 100;
				notInGame = true;
			} else if (startPointDirection == 3) {
				startPointY = (int) Math.floor(Math.random()*Game.HEIGHT*3/5 + Game.HEIGHT/10);
				startPointX = Game.WIDTH - 33;
				startVelX = -5;
				startVelY = velY;
				this.setX(-50);
				this.setY(-50);
				this.setVelY(0);
				this.setVelX(0);
				waitBeforeRespawn = 100;
				notInGame = true;
			}
			highRotationSpeed = false;
		}
		
		if (bounceCount == 3) {
			keepBouncing = false;
		}
		}
		
	}

	@Override
	public void render(Graphics g) {
				if (timeToRotate == rendersPerRotation) {
		
				Graphics2D g2d = (Graphics2D)g;
				AffineTransform old = g2d.getTransform();
				g2d.rotate(Math.toRadians(rotation), x + 8, y + 8);
				g2d.setColor(Color.red);
				g2d.fillRect((int)x,  (int)y, 16, 16);
				g2d.setTransform(old);
				timeToRotate = 0;
				rotation += 10;
				} else {
					Graphics2D g2d = (Graphics2D)g;
					AffineTransform old = g2d.getTransform();
					g2d.rotate(Math.toRadians(rotation), x + 8, y + 8);
					g2d.setColor(Color.red);
					g2d.fillRect((int)x,  (int)y, 16, 16);
					g2d.setTransform(old);
					timeToRotate++;
				}
			}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}
}
