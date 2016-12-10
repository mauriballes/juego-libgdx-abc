package com.losdelcallejon.gamesmachine.InputControllers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.losdelcallejon.gamesmachine.Actores.Letra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
    public AbcController(HashMap<String, String> abecedario, String palabra, ArrayList<Letra> letraList, Stage stage, World world, AssetManager assetManager) {
        Abecedario = abecedario;
        this.palabra = palabra;
        this.letraList = letraList;
        this.stage = stage;
        this.world = world;
        this.assetManager=assetManager;
    }

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

    public void generarLetras() {
        letraList=new ArrayList<Letra>();
        for(int i=0;i<palabra.length();i++)
        {
            char c=palabra.charAt(i);
            Texture letraTextura=assetManager.get(this.Abecedario.get(String.valueOf(c)));
            ControlVirtual controlVirtual=new ControlVirtual();
            Letra letrita=new Letra(world,letraTextura,new Vector2(generarX(),generarY()),controlVirtual);
            ProcesadorEntrada procesadorEntrada=new ProcesadorEntrada(controlVirtual);
            letrita.addCaptureListener(procesadorEntrada);
            this.letraList.add(letrita);
            stage.addActor(letrita);
        }
    }

    private float generarY() {
        Random r = new Random();
        int t = r.nextInt(6 - 5) + 5;
        return t;
    }

    private float generarX() {
        Random r = new Random();
        int t = r.nextInt(6 - 2) + 2;
        return t;
    }


}
