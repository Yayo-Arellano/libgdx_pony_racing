package com.tiarsoft.ponyrace.game.objetos;

import java.util.Random;

import com.tiarsoft.ponyrace.objetos.GameObject;

public class Fogata extends GameObject {

	public float lastStatetime;
	public float stateTime;

	public Fogata(float x, float y,Random oRan) {
		super(x, y, 0);
		stateTime = oRan.nextFloat()*5f;
		lastStatetime=stateTime;
		

	}

	public void update(float delta) {
		lastStatetime=stateTime;
		stateTime += delta;

	}

}
