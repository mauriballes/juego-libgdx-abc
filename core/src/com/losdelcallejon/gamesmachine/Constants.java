package com.losdelcallejon.gamesmachine;

import java.util.HashMap;

/**
 * Created by HP on 09/12/2016.
 */
public class Constants {
    //// CONSTANTES DE LA CONEXION SOCKET
    public static final String SOCKET_URL="http://localhost:8080";
    public static final String GET_USER_EVENT="getUser";
    public static final String EJEMPLO_EVENTO="ESTO_ES_UN_EJEMPLO";
    public static final String GET_PALABRA_NIVEL="getNextWord";
    public static final String UPDATE_OPONENTE="updateOponente";

    ////CONSTANTES DE LA CONEXION SOCKET QUE DEVOLVERA MAURICIO PARA SABER QUE DECISION TOMAR

    public static final int GET_PALABRA_NIVEL_RESPUESTA=0;
    public static final int UPDATE_OPONENTE_RESPUESTA=1;



    public static final int PIXELS_IN_METER=90;


    public static HashMap<String,String> OBTENER_ABECEDARIO()
    {
        HashMap<String,String> abecedario=new HashMap<String,String>();
        abecedario.put("A","abc/A.png");
        abecedario.put("B","abc/B.png");
        abecedario.put("C","abc/C.png");
        abecedario.put("D","abc/D.png");
        abecedario.put("E","abc/E.png");
        abecedario.put("F","abc/F.png");
        abecedario.put("G","abc/G.png");
        abecedario.put("H","abc/H.png");
        abecedario.put("I","abc/I.png");
        abecedario.put("J","abc/J.png");
        abecedario.put("K","abc/K.png");
        abecedario.put("L","abc/L.png");
        abecedario.put("M","abc/M.png");
        abecedario.put("N","abc/N.png");
        abecedario.put("Ñ","abc/Ñ.png");
        abecedario.put("O","abc/O.png");
        abecedario.put("P","abc/P.png");
        abecedario.put("Q","abc/Q.png");
        abecedario.put("R","abc/R.png");
        abecedario.put("S","abc/S.png");
        abecedario.put("T","abc/T.png");
        abecedario.put("U","abc/U.png");
        abecedario.put("V","abc/V.png");
        abecedario.put("W","abc/W.png");
        abecedario.put("X","abc/X.png");
        abecedario.put("Y","abc/Y.png");
        abecedario.put("Z","abc/Z.png");
        return abecedario;
    }

}
