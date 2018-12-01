package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.entity.Projectile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy implements Entity {

	private double xPos;
	private double yPos;
	private double velocity;
	private int i = 0;

	public Enemy(double xPos, double yPos, double velocity) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.velocity = velocity;
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
		// TODO remove enemy from space
	}

	public void update(long ns, Game g) {
		// TODO
		// - Make is so that they cannot leave the boarder
		// - Make it so when the enemy is shot it dies
		// - Make it so there are multiple enemies
		// - Make them move in a better manner so its not trash
		
		if (i == 10) {
			xPos += (Math.random() * 50) + (Math.random() * -50);
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
		
	}

}
