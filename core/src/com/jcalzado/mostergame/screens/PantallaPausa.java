package com.jcalzado.mostergame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.jcalzado.mostergame.Monster;

public class PantallaPausa implements Screen {

    final Monster juego;
    OrthographicCamera camara;
    Texture pantallaPausa;

    public PantallaPausa (Monster juego) {
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
        pantallaPausa = new Texture("PantallaPausa.png");
        juego.batch.draw(pantallaPausa, 0, 0);
        juego.batch.end();

        if (Gdx.input.isTouched()) {
            juego.setScreen(juego.getGameScreen());
            dispose();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            juego.setScreen(juego.getGameScreen());
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