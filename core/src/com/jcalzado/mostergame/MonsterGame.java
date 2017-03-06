package com.jcalzado.mostergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MonsterGame extends ApplicationAdapter {

	private Texture imagenGota;
	private Texture imagenMonster;
	private Sound sonidoGota, sonidoMonster;
	private Music musicaLluvia;
	private OrthographicCamera camara;
	private SpriteBatch batch;
	private Rectangle monster;
	private Array<Rectangle> gotasLluvia;
	private long tiempoUltimaGota;

	@Override
	public void create () {
		// Carga de las imágenes de la Gota y del Monster.
		imagenGota = new Texture(Gdx.files.internal("gota.png"));
		imagenMonster = new Texture(Gdx.files.internal("monster.png"));

		// Carga del audio.
		sonidoGota = Gdx.audio.newSound(Gdx.files.internal("gota.wav"));
		sonidoMonster = Gdx.audio.newSound(Gdx.files.internal("monster.wav"));
		musicaLluvia = Gdx.audio.newMusic(Gdx.files.internal("lluvia.mp3"));

		// Inicio del bucle con la música de fondo.
		musicaLluvia.setLooping(true);
		musicaLluvia.play();

		// Creación la cámara.
		camara = new OrthographicCamera();
		camara.setToOrtho(false, 800, 480);

		// Creación del SpriteBatch.
		batch = new SpriteBatch();

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

	@Override
	public void render () {
		// Color de fondo de la pantalla.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);

		// Limpeza del búfer de la la tarjeta gráfica.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Actualización de la cámara.
		camara.update();

		// Se indica al batch que se usará el sistema de coordenadas dado por la cámara.
		batch.setProjectionMatrix(camara.combined);

		batch.begin(); // INICIO DEL RENDERIZADO.
		// Renderizado del Monster.
		batch.draw(imagenMonster, monster.x, monster.y);
		// Renderizado de las Gotas.
		for (Rectangle gota: gotasLluvia) {
			batch.draw(imagenGota, gota.x, gota.y);
		}
		batch.end(); // FIN DEL RENDERIZADO.

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
				sonidoGota.play();
				iterador.remove();
			}
			if (gotaLluvia.overlaps(monster)) {
				iterador.remove();
				sonidoMonster.play();
				// Si una Gota tocal al Monster finalizamos el juego.
				Gdx.app.exit();
			}
		}
	}

	@Override
	public void dispose () {
		imagenGota.dispose();
		imagenMonster.dispose();
		sonidoGota.dispose();
		sonidoMonster.dispose();
		musicaLluvia.dispose();
		batch.dispose();
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
}
