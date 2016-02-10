package com.mygdx.game.sensor_implementation;

/**
 * Created by Christopher on 2/10/2016.
 */
public class AdjacentObject {

    double degrees;
    double dist;

    public AdjacentObject(double degrees, double dist) {
        this.degrees = degrees;
        this.dist = dist;
    }

    public double getDegrees() {
        return degrees;
    }

    public double getDist() {
        return dist;
    }
}
