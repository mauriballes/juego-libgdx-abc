package com.losdelcallejon.gamesmachine.InputControllers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.losdelcallejon.gamesmachine.Actores.Letra;
import com.losdelcallejon.gamesmachine.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import io.socket.client.Socket;

/**
 * Created by HP on 10/12/2016.
 */
public class AbcController {
    private HashMap<String,String> Abecedario;
    private String palabra;
    private ArrayList<Letra> letraList;
    private Stage stage;
    private World world;
    private AssetManager assetManager;
    private String miPuntaje;
    private int elQueToca;
    private ArrayList<String> palabras;
    private int laPalabraQueToca;

    private Sound touchSound;
    public AbcController(HashMap<String, String> abecedario, String palabra, ArrayList<Letra> letraList, Stage stage, World world, AssetManager assetManager,ArrayList<String> palabras) {
        Abecedario = abecedario;
        this.palabra = palabra;
        this.letraList = letraList;
        this.stage = stage;
        this.world = world;
        this.assetManager=assetManager;
        this.palabras=palabras;
        miPuntaje="";
        elQueToca=0;
        laPalabraQueToca=0;
        touchSound=assetManager.get("audio/jump.ogg");
    }
//<editor-fold desc="Setters y getters">
    public HashMap<String, String> getAbecedario() {
        return Abecedario;
    }

    public void setAbecedario(HashMap<String, String> abecedario) {
        Abecedario = abecedario;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public ArrayList<Letra> getLetraList() {
        return letraList;
    }

    public void setLetraList(ArrayList<Letra> letraList) {
        this.letraList = letraList;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public String getMiPuntaje() {
        return miPuntaje;
    }

    public void setMiPuntaje(String miPuntaje) {
        this.miPuntaje = miPuntaje;
    }
    //</editor-fold>
    public JSONObject toJson(int idPartida)
    {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idPartida", idPartida);
            jsonObject.put("letras",generateJSon());
            return jsonObject;
        }catch (Exception ex)
        {

        }
        return null;
    }

    private JSONArray generateJSon() {
        try {
            JSONArray jsonObject = new JSONArray();
            for(int i=0;i<letraList.size();i++)
            {
                Letra letra=letraList.get(i);
                JSONObject JSONLetra=new JSONObject();
                JSONLetra.put("letra",letra.tecladoVirtual.letra);
                JSONLetra.put("x",letra.getXBody());
                JSONLetra.put("y",letra.getYBody());
                jsonObject.put(i,JSONLetra);
            }
            return jsonObject;
        }catch (Exception ex)
        {

        }
        return null;
    }

    public ArrayList<Integer> generarLetras()
    {
        int cant=generarCantidadLetras();
        ArrayList<Integer> arrayList=new ArrayList<Integer>();
        Random r = new Random();
        for(int i=0;i<cant;i++)
        {
            arrayList.add(r.nextInt(26));
        }
        return arrayList;
    }
    public void cargarRecursosDeLetras(ArrayList<Integer> letras) {
        float incrementoY=1.1160493827f;
        float y=5;
        for(int i=0;i<letras.size();i++)
        {
            String c= Constants.toString(letras.get(i));
            Texture letraTextura=assetManager.get(this.Abecedario.get(c));
            ControlVirtual controlVirtual=new ControlVirtual();
            controlVirtual.letra=c;
            Letra letrita=new Letra(world,letraTextura,new Vector2(generarX(),y),controlVirtual);
            ProcesadorEntrada procesadorEntrada=new ProcesadorEntrada(controlVirtual);
            y+=incrementoY;
            letrita.addCaptureListener(procesadorEntrada);
            this.letraList.add(letrita);
            stage.addActor(letrita);
        }
    }

    private float generarY() {
        Random r = new Random();
        int t = r.nextInt(6 - 5) + 6;
        return t;
    }

    private float generarX() {
        Random r = new Random();
        int t = r.nextInt(6 - 0) + 0;
        return t;
    }

    private int generarCantidadLetras() {
        Random r = new Random();
        int t = r.nextInt(10 - 5) + 5;
        return t;
    }

    public void pintar(float delta)
    {
        stage.act();
        world.step(delta,6,2);
        stage.draw();
    }
    public void hide()
    {
        stage.clear();
        for(Letra letrita: letraList)
        {
            letrita.detach();
        }
    }
    public void dispose()
    {
        stage.dispose();
        world.dispose();
    }

    public void validarCreador(Socket socket,int idPartida) {
        ArrayList<Letra> letrasAEliminar=new ArrayList<Letra>();
        for(int i=0;i<letraList.size();i++)
        {
            Letra letrita =letraList.get(i);
            boolean haSidoPulsada=letrita.haSidoPulsada();
            if(letrita.pasoLaPantalla()|| haSidoPulsada)
            {
                if(haSidoPulsada)
                {

                    socket.emit(Constants.TOUCHED_RES,letraToJson(i,idPartida));
                    touchSound.play();
                    if(elQueToca<palabra.length() && String.valueOf(palabra.charAt(elQueToca)).equals(letrita.tecladoVirtual.letra))
                    {
                        miPuntaje+=letrita.tecladoVirtual.letra;
                        elQueToca++;
                    }
                    if(elQueToca==palabra.length())
                    {
                        //socket.emit(Constants.PALABRA_ACABADA,"");
                        elQueToca=0;
                        laPalabraQueToca++;
                        miPuntaje="";
                        palabra=nextPalabraMonoJugador();
                    }
                }
                letrasAEliminar.add(letrita);
                letrita.remove();
                letrita.detach();
            }
        }
        for(Letra letrita: letrasAEliminar)
        {
                letraList.remove(letrita);
        }
    }

    private JSONObject letraToJson(int i,int idPartida) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idPartida", idPartida);
            jsonObject.put("letra",i);
            return jsonObject;
        }catch (Exception ex)
        {

        }
        return null;
    }

    public boolean letrasVacias() {
        return letraList.size()==0;
    }

    public void validarReceptor(Socket socket,int idPartida) {
        ArrayList<Letra> letrasAEliminar=new ArrayList<Letra>();
        for(int i=0;i<letraList.size();i++)
        {
            Letra letrita =letraList.get(i);
            boolean haSidoPulsada=letrita.haSidoPulsada();
                if(haSidoPulsada)
                {
                    socket.emit(Constants.TOUCHED_RES,letraToJson(i,idPartida));
                    if(elQueToca<palabra.length() && String.valueOf(palabra.charAt(elQueToca)).equals(letrita.tecladoVirtual.letra))
                    {
                        miPuntaje+=letrita.tecladoVirtual.letra;
                        elQueToca++;
                    }
                    if(elQueToca==palabra.length())
                    {
                        socket.emit(Constants.PALABRA_ACABADA,"");
                        elQueToca=0;
                        laPalabraQueToca++;
                        miPuntaje="";
                        palabra=nextPalabraMonoJugador();
                    }
                    letrasAEliminar.add(letrita);
                    letrita.remove();
                    letrita.detach();
                }
        }
        for(Letra letrita: letrasAEliminar)
        {
            letraList.remove(letrita);
        }
    }

    public void validarCreadorMonoJugador(Socket socket) {
        ArrayList<Letra> letrasAEliminar=new ArrayList<Letra>();
        for(Letra letrita: letraList)
        {
            boolean haSidoPulsada=letrita.haSidoPulsada();
            if(letrita.pasoLaPantalla()|| haSidoPulsada)
            {
                if(haSidoPulsada)
                {
                    touchSound.play();
                    if(elQueToca<palabra.length() && String.valueOf(palabra.charAt(elQueToca)).equals(letrita.tecladoVirtual.letra))
                    {
                        miPuntaje+=letrita.tecladoVirtual.letra;
                        elQueToca++;
                    }
                    if(elQueToca==palabra.length())
                    {

                        elQueToca=0;
                        miPuntaje="";
                        laPalabraQueToca++;
                        palabra=nextPalabraMonoJugador();
                    }

                }
                letrasAEliminar.add(letrita);
                letrita.remove();
                letrita.detach();
            }
        }
        for(Letra letrita: letrasAEliminar)
        {
            letraList.remove(letrita);
        }
    }

    private String nextPalabraMonoJugador() {
        if(laPalabraQueToca==palabras.size())
        {
            return "-1";
        }
        return palabras.get(laPalabraQueToca);
    }

    public void setLetraListFromJson(JSONArray letraListFromJson) {
        try{
            limpiarLetraList();
            for(int i=0;i<letraListFromJson.length();i++)
            {
                JSONObject jsonLetra=letraListFromJson.getJSONObject(i);
                String c= jsonLetra.getString("letra");
                Texture letraTextura=assetManager.get(this.Abecedario.get(c));
                ControlVirtual controlVirtual=new ControlVirtual();
                controlVirtual.letra=c;
                Letra letrita=new Letra(world,letraTextura,new Vector2((float)jsonLetra.getDouble("x"),(float)jsonLetra.getDouble("y")),controlVirtual);
                ProcesadorEntrada procesadorEntrada=new ProcesadorEntrada(controlVirtual);
                letrita.addCaptureListener(procesadorEntrada);
                this.letraList.add(letrita);
                stage.addActor(letrita);
            }
        }catch (Exception e)
        {

        }
    }

    private void limpiarLetraList() {
        stage.clear();
        for(int i=0;i<letraList.size();i++)
        {
            letraList.get(i).detach();
            letraList.get(i).tecladoVirtual=null;
        }
        letraList.clear();

    }

    public void prueba() {
        for(int i=0;i<letraList.size();i++)
        {
            if(letraList.get(i).pasoLaMitad()){
                letraList.get(i).getBody().setType(BodyDef.BodyType.StaticBody);
            }
        }
    }
}
