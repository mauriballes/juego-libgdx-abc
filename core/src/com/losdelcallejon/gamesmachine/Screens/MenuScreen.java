package com.losdelcallejon.gamesmachine.Screens;

import com.losdelcallejon.gamesmachine.AbcGameMain;

/**
 * Created by HP on 09/12/2016.
 */
public class MenuScreen extends BaseScreen {

    /*
    *                           LEEME
    *   TODO AQUI APARECERAN LAS DISTINTAS CATEGORIAS Y EL CABRON PODRA ELEGIR QUE JUGAR Y SI JUEGA SOLO O MULTIUSUARIO
    *
    * */
    public MenuScreen(AbcGameMain g) {
        super(g);
    }

    @Override
    public void show() {
        super.show();
        int EJEMPLO_NIVEL=0;
        int EJEMPLO_USUARIO=0;
        //LO PODES SETTEAR AQUI EL USUARIO O DONDE SEA PERO LA CUESTION ES QUE EN LA PANTALLA
        // GameScreen lo usare por law
        game.usuarioId=EJEMPLO_USUARIO;
        GameScreen gameScreen=new GameScreen(game,EJEMPLO_NIVEL,false);
        game.setScreen(gameScreen);
    }
}
