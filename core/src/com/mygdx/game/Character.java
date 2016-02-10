package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;

import java.util.ArrayList;
import java.util.List;

public class Character {
    // Size of character.
    private static final int SIZE  = 25;
    // Speed factor.
    private static final float SPEED_FACTOR = 0.1f;
    // Decay speed.
    private static final float FORWARD_DECAY = 0.6f;

    private AdjacentAgentSensor aaSensor = new AdjacentAgentSensor(this);

    // Position of the character
    private Vector2 pos;
    // Velocity of the character
    private Vector2 vel;
    // Current angle of the character
    protected float ang;

    public Vector2 getPosition() {
        return pos;
    }

    public void setPosition(Vector2 value) {
        pos.set(value);
    }

    public Vector2 getVelocity() {
        return vel;
    }

    public void setVelocity(Vector2 value) {
        vel.set(value);
    }

    public float getAngle() {
        return ang;
    }

    public void setAngle(float value) {
        ang = value;
    }

    public float getSize() {
        return SIZE;
    }

    public Character(float x, float y, float ang) {
        // Set the position of this character
        this.pos = new Vector2(x,y);
        this.ang = ang;

        // Set starting velocity to 0;
        vel = new Vector2();
    }

    public void move(float forward, float turn, Rectangle bounds, List objects){
        processTurn(turn);
        if (forward != 0.0f) {
            // Forward key pressed. increase speed.
            vel.add(forward * (float)Math.cos(Math.toRadians (ang)) * SPEED_FACTOR,
                    forward * (float)-Math.sin (Math.toRadians (ang)) * SPEED_FACTOR);
        } else {
            // Slow the ship down over time.
            vel.scl(FORWARD_DECAY);
        }

        if (ang > 360)
            ang -= 360;
        if (ang < 0)
            ang += 360;

        // Move the character
        if(!checkForCollisions(objects)) {
            pos.add(vel);
        }
        else{
            vel.x = vel.x*-1;
            vel.y = vel.y*-1;
            pos.add(vel);
        }
        adjustToBounds(bounds);
        evaluateAASensor(objects);
    }

    private void processTurn(float turn) {
		this.ang -= turn*5;
    }

    private void adjustToBounds(Rectangle bounds) {
        if (pos.x <= bounds.x) {
            vel.x = 0;
            pos.x = bounds.x;
        } else if (pos.x + SIZE >= bounds.width) {
            vel.x = 0;
            pos.x = bounds.width - SIZE - 1.0f;
        }

        if (pos.y <= bounds.y) {
            vel.y = 0;
            pos.y = bounds.y;
        } else if (pos.y + SIZE >= bounds.height) {
            vel.y = 0;
            pos.y = bounds.height - SIZE - 1.0f;
        }
    }

    private void adjustToFixedObjectBounds(Rectangle bounds) {

        if (pos.x >= bounds.x && pos.x <= bounds.x + bounds.width
                && pos.y >= bounds.y && pos.y <= pos.y + bounds.height) {

        }
    }

    public boolean checkForCollisions(List<FixedObject> objects) {
        for(FixedObject object : objects)
        {
            Rectangle characterBounds = new Rectangle(this.getPosition().x, this.getPosition().y, this.getSize(), this.getSize());
            Rectangle objectBounds = new Rectangle(object.getPosition().x, object.getPosition().y, object.getWidth(), object.getHeight());
            if(Intersector.overlaps(characterBounds, objectBounds))
            {
                return true;
            }
        }
        return false;
    }

    private void evaluateAASensor(List<FixedObject> objects)
    {
        aaSensor.detect(objects);
    }
}
