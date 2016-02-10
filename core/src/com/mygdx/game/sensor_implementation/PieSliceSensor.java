package com.mygdx.game.sensor_implementation;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.mygdx.game.Character;

import java.util.List;

public class PieSliceSensor {

        private static final int RANGE = 200;
        private com.mygdx.game.Character character;
        private Vector2 adjacent = new Vector2();
        private Vector2 characterCenter = new Vector2();
        private int count = 0;
        private int degreesMin = 0;
        private int degreesMax = 0;

        public PieSliceSensor(Character character, int min, int max) {
            this.character = character;
            degreesMin = min;
            degreesMax = max;
        }

        public void setDegreesMin(int min) {
            degreesMin = min;
        }

        public int getDegreesMin() {
            return degreesMin;
        }

        public void setDegreesMax(int max) {
            degreesMax = max;
        }

        public int getDegreesMax() {
            return degreesMax;
        }

        public int detect(List<FixedObject> objects) {
            count = 0;

            for(FixedObject object : objects)
            {
                if(object.getIsAgent())
                {
                    adjacent.set(object.getPosition().x + (object.getWidth()/2), object.getPosition().y + (object.getHeight()/2));
                    characterCenter.set(character.getPosition().x + (character.getSize()/2), character.getPosition().y + (character.getSize()/2));
                    if(adjacent.dst(character.getPosition()) < RANGE)
                    {
                        double degrees = Math.atan2(
                                characterCenter.y - adjacent.y,
                                characterCenter.x - adjacent.x
                        ) * 180.0d / Math.PI;
                        degrees += 180;
                        degrees += character.getAngle();
                        if(degrees < 0)
                        {
                            degrees += 360;
                        }
                        degrees = degrees % 360;
                        if(degrees > degreesMin && degrees < degreesMax)
                        {
                            count++;
                        }

                    }
                }
            }
            return count;
        }
}
