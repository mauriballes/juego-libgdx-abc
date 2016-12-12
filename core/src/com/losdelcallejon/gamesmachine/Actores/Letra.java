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
import com.losdelcallejon.gamesmachine.InputControllers.ControlVirtual;

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
    public ControlVirtual tecladoVirtual;
    public String iden;
    public Letra(World world, Texture texture, Vector2 position, ControlVirtual tecladoVirtual,String ide)
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
        iden=ide;
        letraShape.dispose();
        setSize(Constants.PIXELS_IN_METER*1,Constants.PIXELS_IN_METER*1);
        velocidadBajada=generarVelocidadBajada()*1.07f;
        this.tecladoVirtual=tecladoVirtual;
    }

    public String getUserData(){return iden;}
    public Body getBody()
    {
        return body;
    }

    private float generarVelocidadBajada() {
        Random r = new Random();
        int t = r.nextInt(21 - 16) + 16;
        return t;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
            setPosition((body.getPosition().x-0.5f)*Constants.PIXELS_IN_METER,
                    (body.getPosition().y-0.5f)*Constants.PIXELS_IN_METER);
            batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }

    public float getXBody()
    {
        return body.getPosition().x;
    }
    public float getYBody()
    {
        return body.getPosition().y;
    }
    @Override
    public void act(float delta) {

        if(body.getType()==BodyDef.BodyType.DynamicBody) {
            body.applyTorque(0.5f, true);
        }
       // body.applyForceToCenter(0,velocidadBajada,true);
    }

    public void detach()
    {

        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public boolean pasoLaPantalla() {
        return (getY()+getHeight())<0;
    }

    public boolean pasoLaMitad()
    {
        return (((body.getPosition().y-0.5f)*Constants.PIXELS_IN_METER))<0;
    }

    public boolean haSidoPulsada() {
        return tecladoVirtual.esTocado;
    }
}

