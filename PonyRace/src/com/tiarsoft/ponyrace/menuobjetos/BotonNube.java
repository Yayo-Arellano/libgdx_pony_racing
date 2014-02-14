package com.tiarsoft.ponyrace.menuobjetos;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BotonNube extends Actor {

	Animation animation;
	BitmapFont font;
	String texto;
	public IniciarAnimacion accionInicial;

	float stateTime;

	public boolean wasSelected;

	TextBounds txBounds;

	public BotonNube(Animation animation, String texto, BitmapFont font) {
		this.animation = animation;
		this.font = font;
		stateTime = 0;
		wasSelected = false;
		this.texto = texto;
		accionInicial = new IniciarAnimacion();
		txBounds = new TextBounds();

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (wasSelected) {
			stateTime += delta;
			if (stateTime > animation.animationDuration + .5f) {// Despues de que se le da click al boton y se termina la animacion se resetea el boton
				reset();
			}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(1, 1, 1, 1);
		TextureRegion keyFrame = animation.getKeyFrame(stateTime, false);

		// Para cuando se termine la animacion desaparezca y dude poquito tiempo sin la nube.. por eso al tiempoDuracionAnimacion se
		// le agregaron .3f esos .3f son los extra que va durar en el menu despues de que desaparecio la animacion
		if (stateTime <= animation.animationDuration)
			batch.draw(keyFrame, getX(), getY(), 0, 0, getWidth(), getHeight(), 1, 1, getRotation());

		if (wasSelected)
			return;

		// Acomodar la posicion del texto en el centro
		txBounds = font.getBounds(texto);// obtiene las medidas del texto de las monedas
		float x = getX() + (getWidth() / 2f) - (txBounds.width / 2f) - 10; // Menos 10 para que se carge un poco a la izq font
		float y = getY() + (getHeight() / 2f) + (txBounds.height / 2f) - 10;// Menos 10 para que se baje poquito el font

		font.draw(batch, texto, x, y);
	}

	public void reset() {
		wasSelected = false;
		stateTime = 0;
		accionInicial.tiempoAnimacion = 0;
	}

	public class IniciarAnimacion extends Action {

		float tiempoAnimacion = 0;

		@Override
		public boolean act(float delta) {
			tiempoAnimacion += delta;
			if (tiempoAnimacion > animation.animationDuration)
				return true;
			return false;
		}

	}

}
