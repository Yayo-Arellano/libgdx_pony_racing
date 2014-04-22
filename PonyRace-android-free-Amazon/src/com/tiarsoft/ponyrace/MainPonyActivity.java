package com.tiarsoft.ponyrace;

import android.os.Bundle;

import com.tiarsoft.ponyrace.MainPonyRace.Tienda;

public class MainPonyActivity extends PonyRaceAGC {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		tienda = Tienda.amazon;
		super.onCreate(savedInstanceState);
	}

}
