/**
 * 
 */
package com.reductables.mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.Timer;

/**
 * @author tvsan
 *
 */
public class FallEnemy extends Enemy {

	private int timeToRotate= 0;
	private int rotation = 5;
	private int rendersPerRotation = 40;
	private int rendersPerRotationCounter = 0;
	private boolean keepRotating = true;
	public static int enemiesCreated = 0;
	private int speedIncrease = 100;
	private boolean notInGame = false;
	private int waitBeforeRespawn = 0;
	private Handler handler;
	
	public FallEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		enemiesCreated++;
		totalEnemies++;
		velY = 3;
		this.handler = handler;
	}

	/* (non-Javadoc)
	 * @see com.reductables.mainPackage.GameObject#tick()
	 */
	@Override
	public void tick() {
		if (notInGame) {
			if (waitBeforeRespawn != 0) {
			waitBeforeRespawn--;
			}
			if (waitBeforeRespawn == 0 && questionDone) {
				x = (int)(Math.floor(Math.random()*450 + 70));
				y = 0;
				velY = 1;
				notInGame = false;
			}
			} else {
		
				y += velY;
				speedIncrease--;
				handler.addObject(new TrashEnemyTrail((int)x + 5, (int)y - 10, ID.TrailEnemy, Color.yellow, 16, 16, 0.03f, handler));
				if (speedIncrease == 0) {
					speedIncrease = 5;
					velY++;
				}
		
				if (y > 450) {
					x = -50;
					y = -50;
					velY = 0;
					timeToRotate = 0;
					rotation = 5;
					rendersPerRotation = 40;
					rendersPerRotationCounter = 0;
					keepRotating = true;
					notInGame = true;
					waitBeforeRespawn = 100;
				}
			}
	}

	/* (non-Javadoc)
	 * @see com.reductables.mainPackage.GameObject#render(java.awt.Graphics)
	 */
	@Override
	public void render(Graphics g) {
		if (timeToRotate == rendersPerRotation) {
			Graphics2D g2d = (Graphics2D)g;
			AffineTransform old = g2d.getTransform();
			g2d.rotate(Math.toRadians(rotation), x + 16, y + 16);
			g2d.setColor(Color.yellow);
			g2d.fillRect((int)x, (int)y, 32, 32);
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
				g2d.setColor(Color.yellow);
				g2d.fillRect((int)x, (int)y, 32, 32);
				g2d.setTransform(old);
				timeToRotate++;
			}

	}

	/* (non-Javadoc)
	 * @see com.reductables.mainPackage.GameObject#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x, (int)y, 32, 32);
	}

}
