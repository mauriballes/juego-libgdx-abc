package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;
import com.losdelcallejon.gamesmachine.ActionResolver;
import com.losdelcallejon.gamesmachine.Constants;

/**
 * Created by HP on 09/12/2016.
 */
public class LoginScreen extends BaseScreen {

    /*
                                   LEEME
            TODO        NUEVO USUARIO                                                 LOGEAR
        PONELE QUE HABLE SU NOMBRE DE USUARIO                    QUE DIGA SU NOMBRE DE USUARIO Y LO MANDAS
        DOS VECES Y SI COINCIDEN LO MANDAS CON                   EN EL EVENTO TE LLEGARA EL RESULTADO Y SI
        UN EVENTO AL SERVIDOR Y LUEGO LO MANDAS                  TE LLEGAN SUS DATOS PERSONALES LO MANDAS A LA SIGUIENTE PANTALLA(A LA PANTALLA DEL JUEGO =D )
        A LA PANTALLA DE PERSONALIZACION(SI ES NINHO O NINHA)
    * */
    GL20 gl;
    ActionResolver actionResolver;

    private Stage stage;
    private Skin skin;
    public LoginScreen(AbcGameMain g,ActionResolver actionResolver) {
        super(g);
        this.actionResolver = actionResolver;
        gl = Gdx.app.getGraphics().getGL20();
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Gdx.input.setInputProcessor(stage);

        //EJEMPLO DE COMO REGISTRAR EVENTOS
         //game.socket.on(Constants.EJEMPLO_EVENTO,this);
    }

    @Override
    public void show() {

      //  game.setScreen(game.menuScreen);
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
