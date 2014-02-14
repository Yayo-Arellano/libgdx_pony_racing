package com.tiarsoft.ponyrace.game.objetos;

import java.util.Random;

import com.tiarsoft.ponyrace.game.WorldTiled;

public class Bandera1 {
	public enum State {
		normal,
		tomada;
	}

	public enum TipoAccion {
		saltoIzq,
		saltoDer,
		salto
	}

	Random oRan;
	public State state;
	public TipoAccion tipoAccion;

	public Bandera1(WorldTiled oWorld, TipoAccion tipoAccion) {
		oRan = oWorld.oRan;
		state = State.normal;
		this.tipoAccion = tipoAccion;
	}

	public boolean permitirSalto() {
		if (state == State.normal && oRan.nextBoolean()) {
			state = State.tomada;
			return true;
		}
		return false;

	}

}
