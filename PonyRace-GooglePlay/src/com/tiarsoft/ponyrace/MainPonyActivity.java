package com.tiarsoft.ponyrace;

import android.os.Bundle;

import com.tiarsoft.ponyrace.MainPonyRace.Tienda;

public class MainPonyActivity extends MainGPGS {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		tienda = Tienda.googlePlay;
		super.onCreate(savedInstanceState);
	}

}
