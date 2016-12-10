package com.losdelcallejon.gamesmachine.Screens;

import com.losdelcallejon.gamesmachine.AbcGameMain;
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
    public LoginScreen(AbcGameMain g) {
        super(g);
        //EJEMPLO DE COMO REGISTRAR EVENTOS
         //game.socket.on(Constants.EJEMPLO_EVENTO,this);
    }

    @Override
    public void show() {

        game.setScreen(game.menuScreen);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        // EJEMPLO DE COMO MANDAR UN EJEMPLO AL SERVIDOR
       // game.socket.emit(Constants.EJEMPLO_EVENTO,Object);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void call(Object... args) {

        // UI SE CAPTURAN LOS EVENTOS Y CON PUROS IF EJECUTAMOS DISTINTAS ACCIONES TENEMOS QUE PONERNOS DE ACUERDO CON MAURICIO
    }
}
