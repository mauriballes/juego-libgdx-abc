package com.losdelcallejon.gamesmachine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.losdelcallejon.gamesmachine.Screens.LoadingScreen;
import com.losdelcallejon.gamesmachine.Screens.LoginScreen;
import com.losdelcallejon.gamesmachine.Screens.MenuScreen;

import io.socket.client.IO;
import io.socket.client.Socket;

public class AbcGameMain extends Game {

	/// RECURSOS
	private AssetManager manager;
	public Socket socket;

	// SCREENS PRINCIPALES
	public LoadingScreen loadingScreen;
	public LoginScreen loginScreen;
	public MenuScreen menuScreen;

	//// IDENTIFICADOR DE USUARIO
	public int usuarioId;

	public AbcGameMain()
	{
		//Todo falta inicializar el socket
		//connectSocket();
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
		//Aqui se cargan las imagenes como en el siguiente ejemplo:
		// TODO cargar recursos
		cargarRecursos();

		loadingScreen=new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	private void cargarRecursos() {
		manager.load("logo.png", Texture.class);
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


	}

	public void goToLogin() {
		loginScreen=new LoginScreen(this);
		menuScreen=new MenuScreen(this);
		setScreen(menuScreen);
	}
}