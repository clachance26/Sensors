package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sensors extends ApplicationAdapter {

	Rectangle bounds = new Rectangle(0, 0, 900, 600);
	List<FixedObject> agentList = new ArrayList<FixedObject>();
	SpriteBatch batch;
	Texture backgroundTexture;
	Texture characterTexture;
	Texture horizontalWallTexture;
	Texture verticalWallTexture;
	Texture agent1Texture;
	Texture agent2Texture;
	TextureRegion characterRegion;

	KeyboardController controller;
	Character character;
	FixedObject horizontalWall;
	FixedObject verticalWall;
	FixedObject agent1;
	FixedObject agent2;

	int a1x, a1y, a2x, a2y;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		backgroundTexture = new Texture("background.png");
		characterTexture = new Texture("character.png");
		horizontalWallTexture = new Texture("horizontalWall.png");
		verticalWallTexture = new Texture("verticalWall.png");
		agent1Texture = new Texture("agent1.png");
		agent2Texture = new Texture("agent2.png");

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

		controller = new KeyboardController();
		character = new Character(800, 100, 270);
		characterRegion = new TextureRegion(characterTexture);
		horizontalWall = new FixedObject(400, 400, 0, 400, 50, false);
		verticalWall = new FixedObject( 100, 100, 0, 100, 450, false);
		agent1 = new FixedObject(a1x, a1y, 0, 100, 100, true);
		agent2 = new FixedObject(a2x, a2y, 0, 100, 100, true);

		agentList.add(agent1);
		agentList.add(agent2);
		agentList.add(horizontalWall);
		agentList.add(verticalWall);


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		controller.readInput();
		character.move(controller.getForward(), controller.getTurn(), bounds, agentList);
		//character.checkForCollisions(agentList);

		batch.draw(backgroundTexture, 0, 0);
		//System.out.println(character.getPosition().x + " " + character.getPosition().y);
		batch.draw(characterRegion, character.getPosition().x, character.getPosition().y,
				character.getSize()/2, character.getSize()/2, character.getSize(), character.getSize(),
				1, 1, (-character.getAngle()+270%360));
		batch.draw(horizontalWallTexture, horizontalWall.getPosition().x, horizontalWall.getPosition().y);
		batch.draw(verticalWallTexture, verticalWall.getPosition().x, verticalWall.getPosition().y);
		batch.draw(agent1Texture, agent1.getPosition().x, agent1.getPosition().y);
		batch.draw(agent2Texture, agent2.getPosition().x, agent2.getPosition().y);
		batch.end();
	}
}
