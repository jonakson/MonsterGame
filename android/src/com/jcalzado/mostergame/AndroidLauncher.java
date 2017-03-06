package com.jcalzado.mostergame;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.jcalzado.mostergame.MonsterGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		// Desactivamos la brújula y el acelerómetro para ahorrar batería.
		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new MonsterGame(), config);
	}
}