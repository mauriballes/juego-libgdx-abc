package com.losdelcallejon.gamesmachine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.losdelcallejon.gamesmachine.Screens.GameScreen;
import com.losdelcallejon.gamesmachine.Screens.LoginScreen;
import com.losdelcallejon.gamesmachine.Screens.MenuScreen;
import com.losdelcallejon.gamesmachine.Screens.OptionGameScreen;

import io.socket.client.IO;
import io.socket.client.Socket;

public class AbcGameMain extends Game {

	ActionResolver actionResolver;
	/// RECURSOS
	private AssetManager manager;
	public Socket socket;

	// SCREENS PRINCIPALES
	public LoginScreen loginScreen;
	public MenuScreen menuScreen;

	//// IDENTIFICADOR DE USUARIO
	public int usuarioId;

	public AbcGameMain(ActionResolver actionResolver)
	{
		this.actionResolver = actionResolver;
		//Inicializamos el scoket
		connectSocket();
	}
	public AssetManager getManager() {
		return manager;
	}

	public void setManager(AssetManager manager) {
		this.manager = manager;
	}
	private void connectSocket() {
		try {
			socket= IO.socket(Constants.SOCKET_URL);
			socket.connect();
		}catch (Exception ex)
		{
			System.out.printf(ex.getMessage());
		}
	}

	@Override
	public void create () {
		manager=new AssetManager();
		goToOptionGameScreen();//rlly go to LOGIN SCREEN
	}

	public void goToOptionGameScreen() {
		cargarRecursos();
		while (!getManager().update()) ;
		//OptionGameScreen gameScreen= new OptionGameScreen(this,true,Constants.EJEMPLO_NIVEL,actionResolver);
		loginScreen = new LoginScreen(this,actionResolver);
		setScreen(loginScreen);
	}

	public void goToMenuScreen(String sexo) {

		menuScreen=new MenuScreen(this,sexo,actionResolver);
		setScreen(menuScreen);
	}
	private void cargarRecursos() {
		manager.load("monoplayer.jpg", Texture.class);
		manager.load("playerShip.png",Texture.class);
		manager.load("multiplayer.png", Texture.class);
		manager.load("overfloor1.png", Texture.class);
		manager.load("buscando.jpeg", Texture.class);
		manager.load("fondogame.jpg", Texture.class);
		manager.load("learn.png",Texture.class);
		manager.load("lion.png",Texture.class);
		manager.load("play.png",Texture.class);
		manager.load("abc/A.png",Texture.class);
		manager.load("abc/B.png",Texture.class);
		manager.load("abc/C.png",Texture.class);
		manager.load("abc/D.png",Texture.class);
		manager.load("abc/E.png",Texture.class);
		manager.load("abc/F.png",Texture.class);
		manager.load("abc/G.png",Texture.class);
		manager.load("abc/H.png",Texture.class);
		manager.load("abc/I.png",Texture.class);
		manager.load("abc/J.png",Texture.class);
		manager.load("abc/K.png",Texture.class);
		manager.load("abc/L.png",Texture.class);
		manager.load("abc/M.png",Texture.class);
		manager.load("abc/N.png",Texture.class);
		manager.load("abc/Ñ.png",Texture.class);
		manager.load("abc/O.png",Texture.class);
		manager.load("abc/P.png",Texture.class);
		manager.load("abc/Q.png",Texture.class);
		manager.load("abc/R.png",Texture.class);
		manager.load("abc/S.png",Texture.class);
		manager.load("abc/T.png",Texture.class);
		manager.load("abc/U.png",Texture.class);
		manager.load("abc/V.png",Texture.class);
		manager.load("abc/W.png",Texture.class);
		manager.load("abc/X.png",Texture.class);
		manager.load("abc/Y.png",Texture.class);
		manager.load("abc/Z.png",Texture.class);
		manager.load("audio/die.ogg", Sound.class);
		manager.load("audio/jump.ogg", Sound.class);
		manager.load("audio/song.ogg", Music.class);
		manager.load("overfloor2.png", Texture.class);
		manager.load("overfloor3.png", Texture.class);
		manager.load("overfloor4.png", Texture.class);
		manager.load("overfloor5.png", Texture.class);
		manager.load("overfloor6.png", Texture.class);
		manager.load("overfloor7.png", Texture.class);
		manager.load("overfloor8.png", Texture.class);
		manager.load("overfloor9.png", Texture.class);
		manager.load("overfloor10.png", Texture.class);

	}
}