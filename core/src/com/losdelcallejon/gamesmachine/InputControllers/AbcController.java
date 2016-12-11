package com.losdelcallejon.gamesmachine.InputControllers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.losdelcallejon.gamesmachine.Actores.Letra;
import com.losdelcallejon.gamesmachine.Constants;

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

    public AbcController(HashMap<String, String> abecedario, String palabra, ArrayList<Letra> letraList, Stage stage, World world, AssetManager assetManager) {
        Abecedario = abecedario;
        this.palabra = palabra;
        this.letraList = letraList;
        this.stage = stage;
        this.world = world;
        this.assetManager=assetManager;
        miPuntaje="";
        elQueToca=0;
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
        for(int i=0;i<letras.size();i++)
        {
            String c= Constants.toString(letras.get(i));
            Texture letraTextura=assetManager.get(this.Abecedario.get(c));
            ControlVirtual controlVirtual=new ControlVirtual();
            controlVirtual.letra=c;
            Letra letrita=new Letra(world,letraTextura,new Vector2(generarX(),generarY()),controlVirtual);
            ProcesadorEntrada procesadorEntrada=new ProcesadorEntrada(controlVirtual);
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

    public void validarCreador(Socket socket) {
        ArrayList<Letra> letrasAEliminar=new ArrayList<Letra>();
        for(Letra letrita: letraList)
        {
            boolean haSidoPulsada=letrita.haSidoPulsada();
            if(letrita.pasoLaPantalla()|| haSidoPulsada)
            {
                if(haSidoPulsada)
                {
                    if(elQueToca<palabra.length() && String.valueOf(palabra.charAt(elQueToca)).equals(letrita.tecladoVirtual.letra))
                    {
                        miPuntaje+=letrita.tecladoVirtual.letra;
                        elQueToca++;
                    }
                    if(elQueToca==palabra.length())
                    {
                        socket.emit(Constants.PALABRA_ACABADA,"");
                        elQueToca=0;
                        miPuntaje="";
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

    public boolean letrasVacias() {
        return letraList.size()==0;
    }

    public void validarReceptor(Socket socket) {
        ArrayList<Letra> letrasAEliminar=new ArrayList<Letra>();
        for(Letra letrita: letraList)
        {
            boolean haSidoPulsada=letrita.haSidoPulsada();
                if(haSidoPulsada)
                {
                    if(elQueToca<palabra.length() && String.valueOf(palabra.charAt(elQueToca)).equals(letrita.tecladoVirtual.letra))
                    {
                        miPuntaje+=letrita.tecladoVirtual.letra;
                        elQueToca++;
                    }
                    if(elQueToca==palabra.length())
                    {
                        socket.emit(Constants.PALABRA_ACABADA,"");
                        elQueToca=0;
                    }
                    letrasAEliminar.add(letrita);
                }
        }
        for(Letra letrita: letrasAEliminar)
        {
            letraList.remove(letrita);
        }
    }
}
