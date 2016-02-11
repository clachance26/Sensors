package com.mygdx.game.debug;

import com.mygdx.game.Character;
import com.mygdx.game.sensor_implementation.AdjacentObject;
import com.mygdx.game.sensor_implementation.PieSliceResults;
import com.mygdx.game.sensor_implementation.WallSensor;


/**
 * Prints the output for the sensors
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
                sb.append(String.format("%.4f", wallSensor.getDistToClosestObject()) + "\n");
            }
            else {
                sb.append("-\n");
            }
        }

        sb.append("\n");
        sb.append("Adjacent Agents\n");
        int count = 1;
        for (AdjacentObject adjacentObject: character.getAaSensor().getAdjacentObjects()) {
            sb.append(count + ") Distance: " + String.format("%.4f", adjacentObject.getDist()) + "\t\tHeading: "
                    + String.format("%.4f",adjacentObject.getDist()) + "\n");
            count++;
        }

        sb.append("\n");
        sb.append("Pie Slice Sensor\n");
        for (PieSliceResults results : character.getPsSensor().getResults()) {
            sb.append("Range: " + String.format("%3d", results.getDegreesMin()) + "  -  " + String.format("%3d", results.getDegreesMax()) +
                      "\t\tActivation Level: " + results.getCount() + "\n");
        }

        System.out.println(sb);
    }
}
