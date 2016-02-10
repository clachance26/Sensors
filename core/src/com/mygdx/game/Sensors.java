package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sensors extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture subject;
	Texture horizontalWall;
	Texture verticalWall;
	Texture agent1;
	Texture agent2;
	int a1x, a1y, a2x, a2y;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		subject = new Texture("subject.png");
		horizontalWall = new Texture("horizontalWall.png");
		verticalWall = new Texture("verticalWall.png");
		agent1 = new Texture("agent1.png");
		agent2 = new Texture("agent2.png");

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("agentLocations.txt"));
			a1x = scanner.nextInt();
			a1y = scanner.nextInt();
			a2x = scanner.nextInt();
			a2y = scanner.nextInt();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(subject, 800, 100);
		batch.draw(horizontalWall, 400, 400);
		batch.draw(verticalWall, 100, 100);
		batch.draw(agent1, a1x, a1y);
		batch.draw(agent2, a2x, a2y);
		batch.end();
	}
}
