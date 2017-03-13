package com.jcalzado.mostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class PantallaJuego implements Screen {

	final Monster juego;

	OrthographicCamera camara;
	Rectangle monster;
	Array<Rectangle> gotasLluvia;
	long tiempoUltimaGota;

	public PantallaJuego (Monster juego) {
		this.juego = juego;

		// Creación la cámara.
		camara = new OrthographicCamera();
		camara.setToOrtho(false, 800, 480);

		// Creación e inicialización el rectángulo del Monster.
		monster = new Rectangle();
		monster.x = 800/2 - 64/2; // Centramos el centro del Monster (64/2) en el centro de la pantalla.
		monster.y = 0;
		monster.width = 64;
		monster.height = 64;

		// Creación del Array que contiene las Gotas.
		gotasLluvia = new Array<Rectangle>();

		// Lanzamiento de la primera Gota.
		lanzarGota();
	}

	private void lanzarGota() {
		Rectangle gotaLluvia = new Rectangle();
		gotaLluvia.x = MathUtils.random(0, 800-64);
		gotaLluvia.y = 480;
		gotaLluvia.width = 64;
		gotaLluvia.height = 64;
		gotasLluvia.add(gotaLluvia);
		tiempoUltimaGota = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
		// Color de fondo de la pantalla.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);

		// Limpeza del búfer de la la tarjeta gráfica.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Actualización de la cámara.
		camara.update();

		// Se indica al batch que se usará el sistema de coordenadas dado por la cámara.
		juego.batch.setProjectionMatrix(camara.combined);

		juego.batch.begin(); // INICIO DEL RENDERIZADO.
		// Renderizado del Monster.
		juego.batch.draw(juego.assetsManager().imagenMonster, monster.x, monster.y);
		// Renderizado de las Gotas.
		for (Rectangle gota: gotasLluvia) {
			juego.batch.draw(juego.assetsManager().imagenGota, gota.x, gota.y);
		}
		juego.batch.end(); // FIN DEL RENDERIZADO.

		// Captura de la entrada (Táctil o Ratón) del jugador para mover el Cubo.
		if (Gdx.input.isTouched()) {
			Vector3 posicionTocada = new Vector3();
			posicionTocada.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camara.unproject(posicionTocada);
			monster.x = posicionTocada.x - 64/2;
			monster.y = posicionTocada.y - 64/2;
		}

		// Captura de la entrada (Teclado) del jugador para mover el Monster.
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			monster.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			monster.x += 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			monster.y += 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			monster.y -= 200 * Gdx.graphics.getDeltaTime();
		}

		// Delimitación de movimiento del Monster en base a los bordes de la pantalla.
		if (monster.x < 0) monster.x = 0;
		if (monster.x > 800 - 64) monster.x = 800 - 64;
		if (monster.y < 0) monster.y = 0;
		if (monster.y > 480 - 64) monster.y = 480 - 64;

		// Cálculo del lanzamiento de la última Gota y lanzamiento de la siguiente.
		if (TimeUtils.nanoTime() - tiempoUltimaGota > 1000000000) {
			lanzarGota();
		}

		// Se hace que las Gotas caigan y al tocar el suelo o el Monster se eliminan.
		Iterator<Rectangle> iterador = gotasLluvia.iterator();
		while (iterador.hasNext()) {
			Rectangle gotaLluvia = iterador.next();
			gotaLluvia.y -= 200 * Gdx.graphics.getDeltaTime();
			if (gotaLluvia.y + 64 < 0) {
				juego.assetsManager().sonidoGota.play();
				iterador.remove();
			}
			if (gotaLluvia.overlaps(monster)) {
				iterador.remove();
				juego.assetsManager().sonidoMonster.play();
				juego.setScreen(juego.getGameOverScreen());
				dispose();
			}
		}
	}

	@Override
	public void show() {
		juego.assetsManager().musicaLluvia.play();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
}