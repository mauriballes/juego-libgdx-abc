package com.losdelcallejon.gamesmachine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.losdelcallejon.gamesmachine.Sprites.Starship;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by HP on 09/12/2016.
 */
public class BackUpNodeJs extends ApplicationAdapter {
    private final float UPDATE_TIME = 1/60f;
    float timer;
    SpriteBatch batch;
    private Socket socket;
    String id;
    Starship player;
    Texture playerShip;
    Texture friendlyShip;
    HashMap<String,Starship> friendlyPlayers;

    @Override
    public void create () {
        batch = new SpriteBatch();
        playerShip = new Texture("playerShip2.png");
        friendlyShip = new Texture("playerShip.png");
        friendlyPlayers = new HashMap<String, Starship>();
        connectSocket();
        configSocketEvents();
    }
    private void connectSocket() {
        try {
            socket= IO.socket("http://localhost:8080");
            socket.connect();
        }catch (Exception ex)
        {
            System.out.printf(ex.getMessage());
        }
    }

    private void configSocketEvents() {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO","Connected");
                player= new Starship(playerShip);
            }
        });
    }
    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput(Gdx.graphics.getDeltaTime());
        updateServer(Gdx.graphics.getDeltaTime());

        batch.begin();
        if(player!=null)
        {
            player.draw(batch);
        }
        for(HashMap.Entry<String,Starship> entry : friendlyPlayers.entrySet()){
            entry.getValue().draw(batch);
        }
        batch.end();
    }

    private void handleInput(float deltaTime) {
        if(player!=null)
        {
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            {
                player.setPosition(player.getX()+(-200*deltaTime),player.getY());
            }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            {
                player.setPosition(player.getX()+(+200*deltaTime),player.getY());
            }
        }
    }
    public void updateServer(float dt)
    {
        timer +=dt;
        if(timer >=UPDATE_TIME && player !=null && player.hasMoved())
        {
            JSONObject data = new JSONObject();
            try
            {
                data.put("x",player.getX());
                data.put("y",player.getY());
                socket.emit("playerMoved",data);
            }
            catch (Exception ex)
            {

            }
        }
    }
    @Override
    public void dispose () {
        batch.dispose();
        playerShip.dispose();
        friendlyShip.dispose();
    }
}
