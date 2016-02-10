package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Christopher on 2/9/2016.
 */
public class FixedObject {
    // Size of character.
    private int height;
    private int width;

    // Position of the character
    private Vector2 pos;
    // Current angle of the character
    protected float ang;
    private Sprite characterSprite;

    public FixedObject(float x, float y, float ang, int width, int height) {
        // Set the position of this character
        this.pos = new Vector2(x,y);
        this.ang = ang;
        this.height = height;
        this.width = width;
    }
    public int getHeight() { return height; }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
    public int getWidth() { return width; }

    public Vector2 getPosition() {
        return pos;
    }

    public void setPosition(Vector2 value) {
        pos.set(value);
    }

    public float getAngle() {
        return ang;
    }

    public void setAngle(float value) {
        ang = value;
    }
}





