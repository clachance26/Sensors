package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyboardController {
    // Forward speed
    private float forward;

    // Turning speed
    private float turning;

    public float getForward() {
        return forward;
    }

    public float getTurn() {
        return turning;
    }

    public KeyboardController() {
    }

    public void readInput() {
        int up, left, right, down;
        up    = Input.Keys.UP;
        down  = Input.Keys.DOWN;
        left  = Input.Keys.LEFT;
        right = Input.Keys.RIGHT;

        // Start with zero
        forward = turning = 0;

        // Movement forward/backward
        if (Gdx.input.isKeyPressed(up) && !Gdx.input.isKeyPressed(down)) {
            forward = 1;
        } else if (Gdx.input.isKeyPressed(down) && !Gdx.input.isKeyPressed(up)) {
            forward = -1;
        }

        // Movement left/right
        if (Gdx.input.isKeyPressed(left) && !Gdx.input.isKeyPressed(right)) {
            turning = 1;
        } else if (Gdx.input.isKeyPressed(right) && !Gdx.input.isKeyPressed(left)) {
            turning = -1;
        }
    }
}