package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class AdjacentAgentSensor {

    private static final int RANGE = 100;
    private Character character;
    private Vector2 adjacent = new Vector2();
    private Vector2 characterCenter = new Vector2();

    public AdjacentAgentSensor(Character character){
        this.character = character;
    }

    public void detect(List<FixedObject> objects) {

        for(FixedObject object : objects)
        {
            if(object.getIsAgent())
            {
                adjacent.set(object.getPosition().x + (object.getWidth()/2), object.getPosition().y + (object.getHeight()/2));
                characterCenter.set(character.getPosition().x + (character.getSize()/2), character.getPosition().y + (character.getSize()/2));
                if(adjacent.dst(character.getPosition()) < RANGE)
                {
                    float angle = (character.getPosition().angle(adjacent) );// + character.getAngle());
                    double degrees = Math.atan2(
                            characterCenter.y - adjacent.y,
                            characterCenter.x - adjacent.x
                    ) * 180.0d / Math.PI;
                    degrees += 90;
                    if(degrees < 0)
                    {
                        degrees += 360;
                    }
                    degrees = degrees % 360;


                    System.out.println("Adjacent Agent detected at " + degrees + " degrees "
                            + character.getPosition().dst(adjacent) + " pixels away.");
                }
            }
        }
    }
}
