package com.jcalzado.mostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class PantallaInicial implements Screen {

    final Monster juego;
    OrthographicCamera camara;
    Texture pantallaInicial;

    public PantallaInicial (final Monster juego) {
        this.juego = juego;
        camara = new OrthographicCamera();
        camara.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camara.update();
        juego.batch.setProjectionMatrix(camara.combined);

        juego.batch.begin();
        pantallaInicial = new Texture("PantallaInicio.png");
        juego.batch.draw(pantallaInicial, 0, 0);
        juego.batch.end();

        if (Gdx.input.isTouched()) {
            juego.setScreen(new PantallaJuego(juego));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
