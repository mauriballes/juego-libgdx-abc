package com.losdelcallejon.gamesmachine.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by LuiferU on 08/12/2016.
 */
public class Starship extends Sprite {
    Vector2 previousPosition;
    public Starship(Texture texture)
    {
        super(texture);
        previousPosition=new Vector2(getX(),getY());

    }
    public boolean hasMoved(){
        if(previousPosition.x!=getX() || previousPosition.y!=getY())
        {
            previousPosition.x=getX();
            previousPosition.y=getY();
            return true;
        }
        return false;
    }
}
