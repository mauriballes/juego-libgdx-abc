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

/**
 * Created by HP on 09/12/2016.
 */
public class LoginScreen extends BaseScreen {
    GL20 gl;
    ActionResolver actionResolver;

    private Stage stage;
   // private Skin skin;
    private FondoLogin fl;
    public LoginScreen(AbcGameMain g,ActionResolver actionResolver) {
        super(g);
        this.actionResolver = actionResolver;
        gl = Gdx.app.getGraphics().getGL20();
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
       // skin = new Skin(Gdx.files.internal("Scene2D/uiskin.json"));

        Gdx.input.setInputProcessor(stage);
        fl = new FondoLogin();

        fl.setPosition(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        stage.addActor(fl);

        //EJEMPLO DE COMO REGISTRAR EVENTOS
         //game.socket.on(Constants.EJEMPLO_EVENTO,this);
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

      game.goToMenuScreen();
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
    }
}
