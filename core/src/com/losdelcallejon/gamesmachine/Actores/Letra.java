package com.losdelcallejon.gamesmachine.Actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.losdelcallejon.gamesmachine.Constants;

import java.util.Random;

/**
 * Created by HP on 10/12/2016.
 */
public class Letra extends Actor {
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    private static float velocidadBajada;
    ControlVirtual tecladoVirtual;
    public Letra(World world, Texture texture, Vector2 position,ControlVirtual tecladoVirtual)
    {
        this.world=world;
        this.texture=texture;

        BodyDef def=new BodyDef();
        def.position.set(position);
        def.type= BodyDef.BodyType.DynamicBody;
        body=world.createBody(def);

        PolygonShape letraShape= new PolygonShape();
        letraShape.setAsBox(0.5f,0.5f);
        fixture= body.createFixture(letraShape,3);
        fixture.setUserData("Letra");
        letraShape.dispose();
        setSize(Constants.PIXELS_IN_METER*1,Constants.PIXELS_IN_METER*1);
        velocidadBajada=generarVelocidadBajada()*1.19f;
        this.tecladoVirtual=tecladoVirtual;
    }

    private float generarVelocidadBajada() {
        Random r = new Random();
        int t = r.nextInt(28 - 22) + 22;
        return t;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!tecladoVirtual.esTocado){
            setPosition((body.getPosition().x-0.5f)*Constants.PIXELS_IN_METER,
                    (body.getPosition().y-0.5f)*Constants.PIXELS_IN_METER);
            batch.draw(texture,getX(),getY(),getWidth(),getHeight());
        }
    }

    @Override
    public void act(float delta) {

        body.applyForceToCenter(0,velocidadBajada,true);
    }

    public void detach()
    {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}

