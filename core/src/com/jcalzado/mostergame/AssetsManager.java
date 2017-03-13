package com.jcalzado.mostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetsManager {

    public Texture imagenGota;
    public Texture imagenMonster;
    public Sound sonidoGota;
    public Sound sonidoMonster;
    public Music musicaLluvia;

    public void loadAssets() {
        // Carga de las imágenes de la Gota y del Monster.
        imagenMonster = new Texture(Gdx.files.internal("monster.png"));
        imagenGota = new Texture(Gdx.files.internal("gota.png"));

        // Carga de los sonidos e inicio de la música de fondo.
        sonidoGota = Gdx.audio.newSound(Gdx.files.internal("gota.wav"));
        sonidoMonster = Gdx.audio.newSound(Gdx.files.internal("monster.wav"));
        musicaLluvia = Gdx.audio.newMusic(Gdx.files.internal("lluvia.mp3"));
        musicaLluvia.setLooping(true);
    }

    public void disposeAssets() {
        imagenGota.dispose();
        imagenMonster.dispose();
        sonidoGota.dispose();
        sonidoMonster.dispose();
        musicaLluvia.dispose();
    }
}
