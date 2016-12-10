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


    public static HashMap<Integer,String> OBTENER_ABECEDARIO()
    {
        HashMap<Integer,String> abecedario=new HashMap<Integer, String>();
        abecedario.put(0,"A");
        abecedario.put(1,"B");
        abecedario.put(2,"C");
        abecedario.put(3,"D");
        abecedario.put(4,"E");
        abecedario.put(5,"F");
        abecedario.put(6,"G");
        abecedario.put(7,"H");
        abecedario.put(8,"I");
        abecedario.put(9,"J");
        abecedario.put(10,"K");
        abecedario.put(11,"L");
        abecedario.put(12,"M");
        abecedario.put(13,"N");
        abecedario.put(14,"Ñ");
        abecedario.put(15,"O");
        abecedario.put(16,"P");
        abecedario.put(17,"Q");
        abecedario.put(18,"R");
        abecedario.put(19,"S");
        abecedario.put(20,"T");
        abecedario.put(21,"U");
        abecedario.put(22,"V");
        abecedario.put(23,"W");
        abecedario.put(24,"X");
        abecedario.put(25,"Y");
        abecedario.put(26,"Z");
        return abecedario;
    }

}
