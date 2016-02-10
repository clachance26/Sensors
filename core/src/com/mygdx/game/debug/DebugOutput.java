package com.mygdx.game.debug;

import com.mygdx.game.Character;
import com.mygdx.game.sensor_implementation.AdjacentObject;
import com.mygdx.game.sensor_implementation.WallSensor;


/**
 * Created by Christopher on 2/10/2016.
 */
public class DebugOutput {

    public static void printOutput(Character character) {

        StringBuilder sb = new StringBuilder();
        sb.append("\n\n");
        sb.append("Subject\t\tPosition: " + character.getPosition() + "\tHeading: " + character.getAngle() + "\n");
        sb.append("-----------------------------------------------------------------------------\n");

        sb.append("Wall Sensors\n");
        for (WallSensor wallSensor : character.getWallSensors()) {
            sb.append(wallSensor.getName() + "\t\t");
            if (wallSensor.getDistToClosestObject() >= 0) {
                sb.append(wallSensor.getDistToClosestObject() + "\n");
            }
            else {
                sb.append("-\n");
            }
        }

        sb.append("\n");
        sb.append("Adjacent Agents\n");
        int count = 1;
        for (AdjacentObject adjacentObject: character.getAaSensor().getAdjacentObjects()) {
            sb.append(count + ") Distance: " + adjacentObject.getDist() + "\tHeading: " + adjacentObject.getDist() + "\n");
            count++;
        }

        System.out.println(sb);
    }
}
