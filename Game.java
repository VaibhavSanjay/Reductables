package com.reductables.mainPackage;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.EventObject;
import java.util.Random;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 7122895571377438558L;
	public static final int WIDTH = 600, HEIGHT = WIDTH/12 * 9;
	//public static final int WIDTH = 250, HEIGHT = 400;
	private Thread thread;
	private boolean running = false;
	private boolean notPaused = true;
	private PauseGameSource pg;
	private Handler handler;
	private Object pauseLock = new Object();
	private boolean paused = false;
	private Spawn spawner;
	
	private HeadsUpDisplay hud;
	public Game () {
		handler = new Handler();
		hud = new HeadsUpDisplay();
		QuestionRender qr = new QuestionRender(300, -50, ID.TextQuestion);
		spawner = new Spawn(handler, hud, qr);
		pg = new PauseGameSource();
		pg.addActionListener(new PauseGameListener());
		this.addKeyListener(new KeyInput(handler, pg));
		new Window(WIDTH, HEIGHT, "Reductables!", this);
		Random r = new Random();
		handler.addObject(qr);
		handler.addObject(new Player(WIDTH/2 - 32, HEIGHT/2 - 32, ID.Player, handler, hud));
		handler.addObject(new TrashEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.TrashEnemy, handler));
		handler.addObject(new TrashEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.TrashEnemy, handler));
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startPause() {
		
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			/*try {
				Thread.sleep(0, 1);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			if (notPaused) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
			}
			frames++;	
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
			}
		}
		this.stop();
	}
	
	private void tick() {
		handler.tick();
		hud.tick();
		spawner.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		hud.render(g);
		
		g.dispose();
		bs.show();
		
	}
	
	public static float clamp (float var, float min, float max) {
		if (var >= max) {
			return max;
		} else if (var <= min) {
			return min;
		} else {
			return var;
		}
	}
	
	public class PauseGameListener implements PauseGameClassListener {

		@Override
		public void actionPerformed(EventObject e) {
			//running = !running;
			notPaused = !notPaused;
			System.out.println("change in pause");
		}
		
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
