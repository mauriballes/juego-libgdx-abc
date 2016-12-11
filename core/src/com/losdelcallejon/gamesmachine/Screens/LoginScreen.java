package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;
import com.losdelcallejon.gamesmachine.ActionResolver;
import com.losdelcallejon.gamesmachine.Constants;

import io.socket.emitter.Emitter;

/**
 * Created by HP on 09/12/2016.
 */
public class LoginScreen extends BaseScreen {
    GL20 gl;
    ActionResolver actionResolver;

    private Stage stage;
<<<<<<< HEAD
    private String nombre,sexo;
=======
   // private Skin skin;
>>>>>>> 8543b1b659be3d1da0f5f70265bea52d5cb614da
    private FondoLogin fl;
    public LoginScreen(AbcGameMain g,ActionResolver actionResolver) {
        super(g);
        this.actionResolver = actionResolver;
        gl = Gdx.app.getGraphics().getGL20();
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
<<<<<<< HEAD
=======
       // skin = new Skin(Gdx.files.internal("Scene2D/uiskin.json"));

>>>>>>> 8543b1b659be3d1da0f5f70265bea52d5cb614da
        Gdx.input.setInputProcessor(stage);
        fl = new FondoLogin();
        fl.setPosition(Gdx.graphics.getWidth()/4,0);
        stage.addActor(fl);
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
                if (nombre1.equals(nombre2)) {
                    actionResolver.tryTTS("Confirmado "+nombre1);
                    this.nombre = nombre1;
                    Thread.sleep(3000);
                    while(true) {
                        actionResolver.tryTTS("Eres niño o niña?");
                        Thread.sleep(3000);
                        actionResolver.showSpeechPopup();
                        Thread.sleep(4000);
                        String sexo = actionResolver.obtenerResponse();
                        if (sexo.equals("niño") || sexo.equals("niña")) {
                            if (sexo.equals("niño")){
                                this.sexo="M";
                            }else{
                                this.sexo="N";
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
            if (sexo.equals("M")){
                actionResolver.tryTTS("tu nombre es "+this.nombre+" y eres un niño");
            }else{
                actionResolver.tryTTS("tu nombre es "+this.nombre+" y eres una niña");
            }
            //EJEMPLO DE COMO REGISTRAR EVENTOS
            game.socket.on(Constants.EJEMPLO_EVENTO, new Emitter.Listener() {
                @Override
                public void call(Object... args) {

                }
            });
        }else{
            this.nombre = actionResolver.obtenerNombreUsuario();
            actionResolver.tryTTS("Bienvenido "+this.nombre+", espera un momento mientras cargamos tus progeso por favor");
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
<<<<<<< HEAD
      //  game.setScreen(game.menuScreen);
=======

      game.goToMenuScreen();
>>>>>>> 8543b1b659be3d1da0f5f70265bea52d5cb614da
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        // EJEMPLO DE COMO MANDAR UN EJEMPLO AL SERVIDOR
       // game.socket.emit(Constants.EJEMPLO_EVENTO,Object);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    @Override
    public void call(Object... args) {

        // UI SE CAPTURAN LOS EVENTOS Y CON PUROS IF EJECUTAMOS DISTINTAS ACCIONES TENEMOS QUE PONERNOS DE ACUERDO CON MAURICIO
        // game.socket.on()
    }
}
