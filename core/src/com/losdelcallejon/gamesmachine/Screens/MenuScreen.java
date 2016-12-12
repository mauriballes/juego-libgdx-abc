package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.losdelcallejon.gamesmachine.ActionResolver;
import com.losdelcallejon.gamesmachine.Models.MCursados;
import com.losdelcallejon.gamesmachine.Models.MUnidades;


/**
 * Created by HP on 09/12/2016.
 */
public class MenuScreen extends BaseScreen {

    /*
    *                           LEEME
    *   TODO AQUI APARECERAN LAS DISTINTAS NIVELES Y EL CABRON PODRA ELEGIR QUE JUGAR Y SI JUEGA SOLO O MULTIUSUARIO
    *
    * */
    private Image monoJugador;
    private Image multiJugador;
    private Image Unidad1;
    private Image Unidad2;
    private Stage interfazGrafica;
    boolean esMultijugador;
    int nivel;
    boolean primerPantalla;
    private Stage interfazGraficaDos;
    java.util.List<MUnidades> listUnidades;
    java.util.List<MCursados> listCursados;
    java.util.List<Integer> listCursadosId;

    private String sexo;
    private ActionResolver actionResolver;

    public MenuScreen(AbcGameMain g, String sexo, final ActionResolver actionResolver) {
        super(g);
        this.primerPantalla = true;
        this.sexo = sexo;
        this.actionResolver = actionResolver;
        this.listUnidades = this.actionResolver.obtenerListUnidades();
        this.listCursados = this.actionResolver.obtenerListUnidadesCursadas();
        this.listCursadosId = this.actionResolver.obtenerUnidadesDelUsuario();
        esMultijugador = false;
        nivel = -1;
        this.interfazGrafica = new Stage(new FitViewport(512, 360));
        this.interfazGraficaDos = new Stage(new FitViewport(512, 360));
        cargarUnidades();

        monoJugador = new Image(game.getManager().get("monoplayer.jpg", Texture.class));
        multiJugador = new Image(game.getManager().get("multiplayer.png", Texture.class));

        multiJugador.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                createSegundaPantalla(true);
                return true;
            }
        });
        monoJugador.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                createSegundaPantalla(false);
                return true;
            }
        });
//        Unidad1.addListener(new InputListener()
//        {
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                nivel=0;
//                createOptionsGameScreen();
//                return true;
//
//            }
//        });
//        Unidad2.addListener(new InputListener()
//        {
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                nivel=1;
//                createOptionsGameScreen();
//                return true;
//            }
//        });
        monoJugador.setPosition(0, 360 - monoJugador.getHeight());
        multiJugador.setPosition(multiJugador.getWidth(), 360 - monoJugador.getHeight());
        interfazGrafica.addActor(monoJugador);
        interfazGrafica.addActor(multiJugador);


//        Unidad1 = new Image(game.getManager().get("overfloor.png", Texture.class));
//        Unidad2 = new Image(game.getManager().get("overfloor.png", Texture.class));
//        Unidad1.setPosition(50,10);
//        Unidad2.setPosition(200+Unidad1.getWidth(),10);
//        interfazGraficaDos.addActor(Unidad1);
//        interfazGraficaDos.addActor(Unidad2);
    }

    private void cargarUnidades() {
        int separadorAncho = 20;
        int separadorLargo = 20;
//        float ctsepAnch = 0.5f;
//        float ctsepLarg = 0.5f;
        int fila = 2;
        int columna = 0;
        for (int i = 0; i < this.listUnidades.size(); i++) {
            final MUnidades mUnidad = this.listUnidades.get(i);
            Image Unidad = new Image(game.getManager().get("overfloor"+(i+1)+".png", Texture.class));
            if (i == 0) {
                Unidad.setPosition(0, 360 - Unidad.getHeight());
            } else {
                Unidad.setPosition(columna * Unidad.getWidth() + separadorAncho, 360 - (fila * Unidad.getHeight() + separadorLargo));
//                separadorAncho= (int) (separadorAncho * ctsepAnch);

                columna++;
                if ((i % 3) == 0) {
                    fila++;
//                    separadorLargo = (int) (separadorLargo * ctsepLarg);
                    columna = 0;
//                    separadorAncho = 20;
                }
            }
            Unidad.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    actionResolver.showToast("HAS SELECCIONADO " + mUnidad.getNombre() +" "
                            + mUnidad.getDescripcion()+" id= " + mUnidad.getId() + " nivel= "+ mUnidad.getNivel(),5000);
                    nivel = mUnidad.getNivel();
//                    createOptionsGameScreen();
                    return true;
                }
            });
//            Unidad.setVisible(mostrarUnidadesDisponibles(mUnidad));
            interfazGraficaDos.addActor(Unidad);
        }
    }

    private void createSegundaPantalla(boolean esMultijugador) {
        this.esMultijugador = esMultijugador;
        primerPantalla = false;
        Gdx.input.setInputProcessor(interfazGraficaDos);
    }

    private boolean mostrarUnidadesDisponibles(MUnidades unidad) {
        for (int i = 0; i < listCursados.size(); i++) {
            MCursados cursado = this.listCursados.get(i);
            if (unidad.getId() == cursado.getUnidad_id()) {
                return true;
            }
        }
        return false;
    }

    private void createOptionsGameScreen() {
        OptionGameScreen optionGameScreen = new OptionGameScreen(game, esMultijugador, nivel, actionResolver);
        game.setScreen(optionGameScreen);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(interfazGrafica);
    }


    @Override
    public void render(float delta) {
        if (this.sexo.equals("M")) {
            Gdx.gl.glClearColor(0f, 0f, 1f, 1f);
        } else {
            Gdx.gl.glClearColor(1f, 0.43f, 0.78f, 1f);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (primerPantalla) {
            interfazGrafica.act();
            interfazGrafica.draw();
        } else {
            interfazGraficaDos.act();
            interfazGraficaDos.draw();
        }

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        interfazGrafica.dispose();
        interfazGraficaDos.dispose();

    }
}
