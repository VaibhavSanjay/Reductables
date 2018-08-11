/**
 * 
 */
package com.reductables.mainPackage;

/**
 * @author tvsan
 *
 */
public abstract class Enemy extends GameObject {

	public static int totalEnemies = 0;
	public static boolean questionDone = true;
	
	public Enemy(float x, float y, ID id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}

}
