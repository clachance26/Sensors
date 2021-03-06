package com.mygdx.game;

import com.badlogic.gdx.math.*;
import com.mygdx.game.debug.DebugOutput;
import com.mygdx.game.sensor_implementation.AdjacentAgentSensor;
import com.mygdx.game.sensor_implementation.PieSliceSensor;
import com.mygdx.game.sensor_implementation.WallSensor;

import java.util.ArrayList;
import java.util.List;

/**
 * The character is the movable agent that is controlled by the user
 */
public class Character {
    // Size of character.
    private static final int SIZE  = 25;
    // Speed factor.
    private static final float SPEED_FACTOR = 0.1f;
    //Max speed
    private static final float SPEED_LIMIT = 15;
    // Decay speed.
    private static final float FORWARD_DECAY = 0.6f;

    private PieSliceSensor psSensor = new PieSliceSensor(this, 0, 0);
    private AdjacentAgentSensor aaSensor;
    private List<WallSensor> wallSensors;

    // Position of the character
    private Vector2 pos;
    // Velocity of the character
    private Vector2 vel;
    // Current angle of the character
    protected float ang;

    /**
     * creates the character and initializes its sensors
     * @param x position
     * @param y position
     * @param ang its angle heading
     */
    public Character(float x, float y, float ang) {
        // Set the position of this character
        this.pos = new Vector2(x,y);
        this.ang = ang;

        // Set starting velocity to 0;
        vel = new Vector2();

        //Initialize Sensors
        aaSensor = new AdjacentAgentSensor(this);
        wallSensors = new ArrayList<WallSensor>();
        wallSensors.add(new WallSensor(0, this, "front"));
        wallSensors.add((new WallSensor(90, this, "right")));
        wallSensors.add(new WallSensor(270, this, "left"));

    }

    /**
     * This reads input and moves the character based on the input
     * It will also perform a scan with each of its sensors
     * @param forward whether or not to move forward or backward
     * @param turn whether we should turn
     * @param bounds bounds of the game world (edges of the world)
     * @param objects game objects
     */
    public void move(float forward, float turn, Rectangle bounds, List objects){
        processTurn(turn);
        if (forward != 0.0f) {
            // Forward key pressed. increase speed.
            vel.add(forward * (float)Math.cos(Math.toRadians (ang)) * SPEED_FACTOR,
                    forward * (float)-Math.sin (Math.toRadians (ang)) * SPEED_FACTOR);
            if (vel.x > SPEED_LIMIT) {
                vel.x = SPEED_LIMIT;
            }
            if (vel.x < -SPEED_LIMIT) {
                vel.x = -SPEED_LIMIT;
            }
            if (vel.y > SPEED_LIMIT) {
                vel.y = SPEED_LIMIT;
            }
            if (vel.y < -SPEED_LIMIT) {
                vel.y = -SPEED_LIMIT;
            }
        } else {
            // Slow the character down over time.
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
            vel.x = vel.x*-2;
            vel.y = vel.y*-2;
            if (vel.x > SPEED_LIMIT) {
                vel.x = SPEED_LIMIT;
            }
            if (vel.x < -SPEED_LIMIT) {
                vel.x = -SPEED_LIMIT;
            }
            if (vel.y > SPEED_LIMIT) {
                vel.y = SPEED_LIMIT;
            }
            if (vel.y < -SPEED_LIMIT) {
                vel.y = -SPEED_LIMIT;
            }

            pos.add(vel);
        }
        adjustToBounds(bounds);
        evaluateAASensor(objects);
        int count = 0;

        psSensor.resetSensorResults();
        evaluatePieSliceSensor(objects, 0, 90);
        evaluatePieSliceSensor(objects, 90, 180);
        evaluatePieSliceSensor(objects, 180, 270);
        evaluatePieSliceSensor(objects, 270, 360);

        for (int i=0; i<wallSensors.size(); i++) {
            evaluateWallSensor(wallSensors.get(i), objects);
        }
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

    /**
     * makes sure we do not go through agents and walls
     */
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

    /**
     * performs a scan with the adjacent agent sensor
     */
    private void evaluateAASensor(List<FixedObject> objects) {aaSensor.detect(objects);
    }

    /**
     * performs a scan with the wall sensor
     */
    public void evaluateWallSensor(WallSensor wallSensor, List<FixedObject> objects) {
        wallSensor.Sense(objects);
    }

    /**
     * performs a scan with the pie slice sensor
     */
    public void evaluatePieSliceSensor(List<FixedObject> objects, int min, int max){
        psSensor.setDegreesMin(min);
        psSensor.setDegreesMax(max);
        psSensor.detect(objects);
    }

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

    public List<WallSensor> getWallSensors() {
        return wallSensors;
    }

    public AdjacentAgentSensor getAaSensor() {
        return aaSensor;
    }

    public PieSliceSensor getPsSensor() {
        return psSensor;
    }
}
