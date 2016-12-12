package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;
import com.losdelcallejon.gamesmachine.ActionResolver;
import com.losdelcallejon.gamesmachine.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

/**
 * Created by HP on 09/12/2016.
 */
public class LoginScreen extends BaseScreen {
    public static  float DURATION = 6.0f;
    GL20 gl;
    ActionResolver actionResolver;

    private Stage stage;
    private String nombre,sexo;
    private FondoLogin fl;
    private boolean endHilo = false;
    public LoginScreen(AbcGameMain g,ActionResolver actionResolver) {
        super(g);
        this.actionResolver = actionResolver;
        gl = Gdx.app.getGraphics().getGL20();
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        fl = new FondoLogin();
        fl.setPosition(Gdx.graphics.getWidth()/4,0);
        stage.addActor(fl);
        game.socket.on(Constants.IDENTIFY_RES,this);
        Thread loginThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EjecutarLogin();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        loginThread.start();
    }

    private void EjecutarLogin() throws InterruptedException {
        if (actionResolver.esNuevoUsuario()){
            while(true) {
                actionResolver.tryTTS("Bienvenido, cual es tu nombre?");
                Thread.sleep(3000);
                actionResolver.showSpeechPopup();
                Thread.sleep(4000);
                String nombre1 = actionResolver.obtenerResponse();
                actionResolver.tryTTS("Confirma tu nombre por favor");
                Thread.sleep(3000);
                actionResolver.showSpeechPopup();
                Thread.sleep(4000);
                String nombre2 = actionResolver.obtenerResponse();
//                String nombre1="hola";
//                String nombre2="hola";
                if (nombre1.equals(nombre2)) {
                    actionResolver.tryTTS("Confirmado "+nombre1);
                    this.nombre = nombre1;
                    Thread.sleep(3000);
                    while(true) {
                        actionResolver.tryTTS("Eres hombre o mujer?");
                        Thread.sleep(3000);
                        actionResolver.showSpeechPopup();
                        Thread.sleep(4000);
                        String sexo = actionResolver.obtenerResponse();
                      //  String sexo = "niño";
                        if (sexo.equals("hombre") || sexo.equals("mujer")) {
                            if (sexo.equals("hombre")){
                                this.sexo="M";
                            }else{
                                this.sexo="F";
                            }
                            break;
                        } else {
                            actionResolver.tryTTS("Vuelve a intentarlo por favor");
                            Thread.sleep(3000);
                        }
                    }
                    break;
                } else {
                    actionResolver.tryTTS("Los nombres no coinciden vuelve a intentarlo por favor");
                    Thread.sleep(3000);
                }
            }
            JSONObject data = new JSONObject();
            try {
                data.put("username",this.nombre);
                data.put("sexo",this.sexo);
                game.socket.emit("identify",data);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
            this.nombre = actionResolver.obtenerNombreUsuario();
            this.sexo = actionResolver.obtenerSexoUsuario();
           actionResolver.tryTTS("Bienvenido "+this.nombre+", espera un momento mientras cargamos tu progreso por favor");


            JSONObject data = new JSONObject();
            try {
                data.put("user_id",actionResolver.obtenerMongoId());
                game.socket.emit("identify",data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class FondoLogin extends Actor {
        Texture texture = new Texture(Gdx.files.internal("ABC.png"));

        public FondoLogin() {
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
                    getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0,
                    texture.getWidth(), texture.getHeight(), false, false);
        }
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        if (endHilo){
            endHilo=false;
            stage.addAction(
                    Actions.sequence(
                            Actions.delay(DURATION),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    game.goToMenuScreen(sexo);
                                }
                            })
                    )
            );
        }
        stage.draw();
    }

//    @Override
//    public void dispose() {
////        super.dispose();
//        stage.dispose();
//    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void call(Object... args) {
        JSONObject data = (JSONObject) args[0];
        String evt="";
        try {
            evt = data.get("evento").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch (evt){
            case "identifyRes":
                try {
                    JSONObject cliente = data.getJSONObject("cliente");
                    actionResolver.insertarUsuario(this.nombre,this.sexo,cliente.getString("_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray arrayUnidades = data.getJSONArray("unidades");
                    for (int i = 0; i < arrayUnidades.length(); i++) {
                       String descripcion = arrayUnidades.getJSONObject(i).getString("descripcion");
                       String nombre = arrayUnidades.getJSONObject(i).getString("nombre");
                       int nivel = arrayUnidades.getJSONObject(i).getInt("nivel");
                       actionResolver.insertarUnidad(i,nivel,nombre,descripcion);
                    }
                    JSONArray arrayPalabras = data.getJSONArray("palabras");
                    for (int i = 0; i < arrayPalabras.length(); i++) {
                    String letra = arrayPalabras.getJSONObject(i).getString("letras");
                    String nombreUnidad = arrayPalabras.getJSONObject(i).getJSONObject("unidad").getString("nombre");
                    int id_unidad = actionResolver.obtenerIdUnidad(nombreUnidad);
                    actionResolver.insertarPalabra(i,letra,id_unidad);
                        DURATION = 1.0f;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                endHilo = true;
               // actionResolver.showToast("cargo db",3000);

                break;
        }
    }
}
