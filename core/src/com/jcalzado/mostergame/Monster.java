package com.jcalzado.mostergame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jcalzado.mostergame.helpers.AssetsManager;
import com.jcalzado.mostergame.screens.PantallaDerrota;
import com.jcalzado.mostergame.screens.PantallaInicial;
import com.jcalzado.mostergame.screens.PantallaJuego;
import com.jcalzado.mostergame.screens.PantallaPausa;

public class Monster extends Game {

    public SpriteBatch batch;
    private AssetsManager assets;
    private final PantallaJuego GameScreen = new PantallaJuego(this);
    private final PantallaInicial HomeScreen = new PantallaInicial(this);
    private final PantallaDerrota GameOverScreen = new PantallaDerrota(this);
    private final PantallaPausa PauseScreen = new PantallaPausa(this);


    @Override
    public void create() {
        batch = new SpriteBatch();
        assets = new AssetsManager();
        assets.loadAssets();
        this.setScreen(HomeScreen);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        assets.disposeAssets();
    }

    @Override
    public void pause() {
        this.setScreen(PauseScreen);
    }

    @Override
    public void resume() {

    }

    public PantallaInicial getHomeScreen() {
        return  HomeScreen;
    }

    public PantallaDerrota getGameOverScreen() {
        return GameOverScreen;
    }

    public PantallaJuego getGameScreen() {
        return GameScreen;
    }

    public AssetsManager assetsManager() {
        return assets;
    }
}