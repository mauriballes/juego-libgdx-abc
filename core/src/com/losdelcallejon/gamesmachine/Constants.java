package com.losdelcallejon.gamesmachine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HP on 09/12/2016.
 */
public class Constants {
    //// CONSTANTES DE LA CONEXION SOCKET
    public static final String SOCKET_URL="http://abcsocket.cf";
    public static final String SOCKET_URL_Local="http://192.168.1.3:3000";
    public static final String GET_USER_EVENT="getUser";

    public static final String EJEMPLO_EVENTO="ESTO_ES_UN_EJEMPLO";
    public static final String GET_NEXT_PALABRA="getNextWord";
    public static final String IDENTIFY_RES="identifyRes";


    public static final String UPDATE_OPONENTE="updateOponente";
    public static final String LETRA_PULSADA ="A" ;
    public static final String RENDERIZAR_LETRAS_POSICIONES="playGame";
    public static final String PALABRA_ACABADA = "endPlay";
    public static final String PALABRA_ACABADA_RES = "endPlayRes";
    public static final String REQUEST_PLAY="requestPlay";
    public static final String INICIAR_PARTIDA_RES = "initGameRes";
    public static final String CANCEL_PLAY_REQUEST="cancelPlayRequest";

    ///Recepcionar respuestas
    public static final int LETRA_PULSADA_RESPUESTA=0;
    public static final String RENDERIZAR_LETRAS_POSICIONES_RESPUESTA="";
    public static final String BUSCANDO_OPONENTE="Buscando Oponente";
    public static final String REQUEST_PLAY_RES="requestPlayRes";
    public static final String RENDERIZAR_LETRAS_RES="playGameRes";
    public static final String TOUCHED="touched";
    public static final String TOUCHED_RES="touchedRes";
    //Otros
    public static final int PIXELS_IN_METER=225;
    public static final String ENVIAR_PALABRA = "DFD";


    public static final int EJEMPLO_NIVEL=1;
    public static final String FINISH_GAME_RES = "finishGameRes";
    public static String PARA_LOCO="Empezo Partida";

    public static String toString(int letra)
    {
        switch (letra)
        {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "G";
            case 7:
                return "H";
            case 8:
                return "I";
            case 9:
                return "J";
            case 10:
                return "K";
            case 11:
                return "L";
            case 12:
                return "M";
            case 13:
                return "N";
            case 14:
                return "Ñ";
            case 15:
                return "O";
            case 16:
                return "P";
            case 17:
                return "Q";
            case 18:
                return "R";
            case 19:
                return "S";
            case 20:
                return "T";
            case 21:
                return "U";
            case 22:
                return "V";
            case 23:
                return "W";
            case 24:
                return "X";
            case 25:
                return "Y";
            case 26:
                return "Z";
        }
        return "A";
    }
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

    public static ArrayList<String> DummyFactoryWords()
    {
        ArrayList<String> words=new ArrayList<String>();
        words.add("A");
        words.add("D");
        words.add("MM");
        return  words;
    }
}
