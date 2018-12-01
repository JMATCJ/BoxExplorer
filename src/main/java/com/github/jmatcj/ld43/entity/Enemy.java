package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.world.Room;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy implements Entity {

	private double xPos;
	private double yPos;
	private int i = 0;

	public Enemy(double xPos, double yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void draw(GraphicsContext gc, Game g) {
		gc.save();
		gc.setFill(Color.ORANGE);
		gc.fillRect(xPos, yPos, 10, 10);
		gc.setFill(Color.GREEN);
		gc.fillRect(xPos, yPos + 5, 3, 1);
		gc.restore();
	}

	public void killEnemy() {
		//Game.removeListener(Game.enemy);
	}

	public void update(long ns, Game g) {
		// TODO
		// - Make it so when the enemy is shot it dies
		// - Make it so there are multiple enemies
		// - Make them move in a better manner so its not trash
		
		if (i == 10) {
			xPos += (Math.random() * 50) + (Math.random() * -50);  //Make this better probably use this to track the player
			yPos += (Math.random() * 50) + (Math.random() * -50);
			if (xPos > 1260) {
				xPos = 1260;
			}
			if (xPos < 0) {
				xPos = 0;
			}
			if (yPos > 700) {
				yPos = 700;
			}
			if (yPos < 0) {
				yPos = 0;
			}
			//g.addListener(new Projectile(xPos, yPos, xPos, yPos, 20.0)); //Let the enemy shoot
			i = 0;
		} else {
			i++;
		}
		if (Math.abs(xPos - Projectile.getX()) == 0) {
			if (Math.abs(yPos - Projectile.getY()) == 0) {
				killEnemy();
			}
		}
	}

}
