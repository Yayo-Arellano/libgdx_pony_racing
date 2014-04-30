package com.tiarsoft.ponyrace;

import android.os.Bundle;

import com.tiarsoft.ponyrace.MainPonyRace.Tienda;

public class MainPonyActivity extends MainAGC {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		tienda = Tienda.otros;
		super.onCreate(savedInstanceState);
	}

}
