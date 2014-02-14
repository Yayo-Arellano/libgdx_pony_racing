package com.tiarsoft.ponyrace.game.objetos;

import com.esotericsoftware.spine.Skeleton;
import com.tiarsoft.ponyrace.game.WorldTiled;
import com.tiarsoft.ponyrace.objetos.GameObject;

public class Moneda extends GameObject {
	public enum State {
		normal,
		tomada
	}

	public static float TIEMPO_TOMADA;

	public float lastStatetime;
	public float stateTime;

	public State state;
	public Skeleton monedaSkeleton;

	public Moneda(float x, float y, WorldTiled oWorld) {
		super(x, y, 0);
		stateTime = oWorld.oRan.nextFloat() * 5f;
		lastStatetime = stateTime;
		state = State.normal;
		monedaSkeleton = new Skeleton(oWorld.game.oAssets.skeletonMonedaData);
		TIEMPO_TOMADA = oWorld.game.oAssets.monedaTomadaAnim.getDuration();

	}

	public void update(float delta) {
		lastStatetime = stateTime;
		stateTime += delta;

	}

	public void hitPony() {
		state = State.tomada;
		stateTime = 0;
	}

}
