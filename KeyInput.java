/**
 * 
 */
package com.reductables.mainPackage;

import java.awt.event.*;
;

/**
 * @author tvsan
 *
 */
public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	ExitWindow saveWindow;
	PauseGameSource pg;
	
	private boolean[] keyDown = new boolean[4];
	
	public KeyInput (Handler handler, PauseGameSource pg) {
		this.handler = handler;
		this.pg = pg;
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
	}
	
	
	public void keyPressed (KeyEvent e) {
		int key = e.getKeyCode();
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject =  handler.object.get(i);
			
			if (tempObject.getId() == ID.Player) {
				if (key == KeyEvent.VK_UP) {
					tempObject.setVelY(-5);
					keyDown[0] = true;
				} else if (key == KeyEvent.VK_DOWN) {
					tempObject.setVelY(5);
					keyDown[1] = true;
				} else if (key == KeyEvent.VK_LEFT) {
					tempObject.setVelX(-5);
					keyDown[2] = true;
				} else if (key == KeyEvent.VK_RIGHT) {
					tempObject.setVelX(5);
					keyDown[3] = true;
				}
			}
		}
		if (key == KeyEvent.VK_ESCAPE) {
			pg.fireEvent();
			saveWindow = new ExitWindow(pg);
		}
	}
	
	public void keyReleased (KeyEvent e) {
		int key = e.getKeyCode();
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject =  handler.object.get(i);
			
			if (tempObject.getId() == ID.Player) {
				if (key == KeyEvent.VK_UP) {
					keyDown[0] = false;
				} else if (key == KeyEvent.VK_DOWN) {
					keyDown[1] = false;
				} else if (key == KeyEvent.VK_LEFT) {
					keyDown[2] = false;
				} else if (key == KeyEvent.VK_RIGHT) {
					keyDown[3] = false;
				}
				
				if (!keyDown[0] && !keyDown[1]) {
					tempObject.setVelY(0);
				}
				
				if (!keyDown[2] && !keyDown[3]) {
					tempObject.setVelX(0);
				}
			}
		}
	}
}
