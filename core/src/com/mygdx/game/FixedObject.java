package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class FixedObject {
    // Size of object.
    private int height;
    private int width;
    private boolean isAgent;

    // Position of the object
    private Vector2 pos;
    // Current angle of the object
    protected float ang;

    public FixedObject(float x, float y, float ang, int width, int height, boolean isAgent) {
        // Set the position of this character
        this.pos = new Vector2(x,y);
        this.ang = ang;
        this.height = height;
        this.width = width;
        this.isAgent = isAgent;
    }

    public boolean getIsAgent() { return isAgent; }

    public int getHeight() { return height; }

    public int getWidth() { return width; }

    public Vector2 getPosition() {
        return pos;
    }

    public float getAngle() {
        return ang;
    }

    public void setAngle(float value) {
        ang = value;
    }
}





