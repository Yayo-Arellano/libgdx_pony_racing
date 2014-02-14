package com.tiarsoft.ponyrace;

import java.nio.IntBuffer;
import java.util.LinkedHashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.BufferUtils;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;

public class Assets extends AssetManager {

	public static boolean usarPacked = true;
	public static boolean drawDebugLines = false;

	public static int mundoMaximo = 17;

	String atlasComun = "data/atlasComun.txt";
	public Music musicaMenus;
	public Music musicaTiled;
	public com.badlogic.gdx.graphics.g2d.Animation nube;

	public NinePatchDrawable btSonidoON;
	public NinePatchDrawable btSonidoOff;
	public NinePatchDrawable btMusicaON;
	public NinePatchDrawable btMusicaOff;

	public LinkedHashMap<Integer, String> nombrePonys;

	public Skin skin;
	public NinePatchDrawable btSignInUp;
	public NinePatchDrawable btSignInDown;

	public NinePatchDrawable btShareFacebookUp;
	public NinePatchDrawable btShareFacebookDown;

	public Assets() {

		nombrePonys = new LinkedHashMap<Integer, String>(6);
		nombrePonys.put(0, "Cloud");
		nombrePonys.put(1, "Natylol");
		nombrePonys.put(2, "Ignis");
		nombrePonys.put(3, "cientifico");
		nombrePonys.put(4, "LAlba");
		nombrePonys.put(5, "enemigo");

		IntBuffer buf = BufferUtils.newIntBuffer(16);
		Gdx.gl.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, buf);
		int maxSize = buf.get();

		Gdx.app.log("Texture Size", maxSize + "");
		if (usarPacked)
			this.setLoader(TiledMap.class, new AtlasTmxMapLoader(new InternalFileHandleResolver()));

		else
			this.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		cargarFont();

		load(atlasComun, TextureAtlas.class);

	}

	public void cargarComun() {
		TextureAtlas atlas = get(atlasComun, TextureAtlas.class);
		AtlasRegion nube0 = atlas.findRegion("nube0");
		AtlasRegion nube1 = atlas.findRegion("nube1");
		AtlasRegion nube2 = atlas.findRegion("nube2");
		AtlasRegion nube3 = atlas.findRegion("nube3");
		AtlasRegion nube4 = atlas.findRegion("nube4");
		AtlasRegion nube5 = atlas.findRegion("nube5");
		AtlasRegion nube6 = atlas.findRegion("nube6");
		nube = new com.badlogic.gdx.graphics.g2d.Animation(.075f, nube0, nube1, nube2, nube3, nube4, nube5, nube6);

		btSonidoON = new NinePatchDrawable(new NinePatch(atlas.findRegion("soundpausa")));
		btSonidoOff = new NinePatchDrawable(new NinePatch(atlas.findRegion("soundoffpausa")));
		btMusicaON = new NinePatchDrawable(new NinePatch(atlas.findRegion("musicpausa")));
		btMusicaOff = new NinePatchDrawable(new NinePatch(atlas.findRegion("musicoffpausa")));

		int size = 80;
		btSonidoOff.setMinHeight(size);
		btSonidoOff.setMinWidth(size);
		btSonidoON.setMinHeight(size);
		btSonidoON.setMinWidth(size);
		btMusicaOff.setMinHeight(size);
		btMusicaOff.setMinWidth(size);
		btMusicaON.setMinHeight(size);
		btMusicaON.setMinWidth(size);

		skin = new Skin(Gdx.files.internal("data/menus/uiskin.json"), atlas);
		skin.getFont("default-font").setScale(.5f);
		btSignInUp = new NinePatchDrawable(new NinePatch(atlas.createPatch("btSignInUp")));
		btSignInDown = new NinePatchDrawable(new NinePatch(atlas.createPatch("btSignInDown")));

		btShareFacebookUp = new NinePatchDrawable(new NinePatch(atlas.createPatch("btShareFacebookUp")));
		btShareFacebookDown = new NinePatchDrawable(new NinePatch(atlas.createPatch("btShareFacebookDown")));

	}

	/**
	 * ######################################### FONTS ###########################################
	 */
	public BitmapFont fontGde;
	public BitmapFont fontChco;

	public void cargarFont() {

		Texture texturaFont = new Texture(Gdx.files.internal("data/fonts/fontMenus2.png"));
		texturaFont.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		fontGde = new BitmapFont(Gdx.files.internal("data/fonts/fontMenus2.fnt"), new TextureRegion(texturaFont), false);
		fontGde.setUseIntegerPositions(false);
		fontGde.setFixedWidthGlyphs("0123456789");

		fontChco = new BitmapFont(Gdx.files.internal("data/fonts/fontMenus2.fnt"), new TextureRegion(texturaFont), false);
		fontChco.setUseIntegerPositions(false);
		fontChco.setFixedWidthGlyphs("0123456789");
	}

	/**
	 * ######################################### MENUS ###########################################
	 */

	public AtlasRegion fondoMainMenu;
	public AtlasRegion fondoSettings;
	public AtlasRegion fondoAbout;
	public NinePatchDrawable btnFacebook;

	public Skeleton skeletonMenuTitle;
	public Animation animationMenuTitle;

	String atlasMenusRuta = "data/menus/atlasMenus.txt";
	String atlasWorldTiledScreenRuta = "data/worldMap/worldmap.tmx";

	public void loadMenus() {

		if (!isLoaded("data/musica/00.mp3"))
			load("data/musica/00.mp3", Music.class);

		if (!isLoaded(atlasMenusRuta))
			load(atlasMenusRuta, TextureAtlas.class);

		if (!isLoaded(atlasWorldTiledScreenRuta))
			load(atlasWorldTiledScreenRuta, TiledMap.class);

	}

	public void cargarMenus() {
		cargarComun();
		musicaMenus = get("data/musica/00.mp3", Music.class);
		musicaMenus.setLooping(true);

		playMusicMenus();

		TextureAtlas atlas = get(atlasMenusRuta, TextureAtlas.class);
		fondoMainMenu = atlas.findRegion("fondoMenu");
		btnFacebook = new NinePatchDrawable(new NinePatch(atlas.findRegion("botonFace")));

		SkeletonJson json = new SkeletonJson(atlas);

		json.setScale(.7f);
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("data/menus/titleponyracing.json"));
		animationMenuTitle = skeletonData.findAnimation("flag");
		skeletonMenuTitle = new Skeleton(skeletonData);

		cargarWorldMap(atlas);
		cargarTienda(atlas);

	}

	public void unLoadMenus() {
		musicaMenus.stop();
		unload("data/musica/00.mp3");
		unload(atlasMenusRuta);
		unload(atlasWorldTiledScreenRuta);
		System.gc();

	}

	/**
	 * ################################################################## TIENDA #############################################################
	 */

	public Skeleton skeletonTiendaTitle;
	public Animation animationTiendaTitle;

	public AtlasRegion fondoTienda;
	public AtlasRegion monedaTienda;
	public AtlasRegion bananaSpikeTienda;
	public AtlasRegion bombaTienda;
	public AtlasRegion chocolateTienda;
	public AtlasRegion globoTienda;
	public AtlasRegion chileTienda;

	public AtlasRegion cronometroTienda;
	public NinePatchDrawable btNubeUpTienda;
	public NinePatchDrawable btNubeDownTienda;

	public NinePatchDrawable miniNubeScroll;
	public NinePatchDrawable barraScroll;

	public NinePatchDrawable btMenuLeftUp;
	public NinePatchDrawable btMenuLeftDown;

	public TextureRegionDrawable perfilCloud;
	public TextureRegionDrawable perfilNatylol;
	public TextureRegionDrawable perfilIgnis;
	public TextureRegionDrawable perfilcientifico;
	public TextureRegionDrawable perfilLAlba;
	public TextureRegionDrawable perfilenemigo;

	public void cargarTienda(TextureAtlas atlas) {
		cargarComun();
		fondoTienda = atlas.findRegion("fondoTienda");

		monedaTienda = atlas.findRegion("minicointienda");
		bananaSpikeTienda = atlas.findRegion("bananaSpikes");
		bombaTienda = atlas.findRegion("minibombatienda");
		cronometroTienda = atlas.findRegion("cronometro");
		chocolateTienda = atlas.findRegion("miniChocolate");
		globoTienda = atlas.findRegion("miniGlobos");
		chileTienda = atlas.findRegion("miniChile");

		btNubeUpTienda = new NinePatchDrawable(new NinePatch(atlas.findRegion("botonBuy")));
		btNubeDownTienda = new NinePatchDrawable(new NinePatch(atlas.findRegion("botonBuyPresionado")));
		miniNubeScroll = new NinePatchDrawable(new NinePatch(atlas.findRegion("mininubescroll")));
		barraScroll = new NinePatchDrawable(new NinePatch(atlas.findRegion("barradescroll")));

		btMenuLeftUp = new NinePatchDrawable(new NinePatch(atlas.findRegion("btSinPresionar")));
		btMenuLeftDown = new NinePatchDrawable(new NinePatch(atlas.findRegion("btPresionado")));

		perfilCloud = new TextureRegionDrawable(atlas.findRegion("perfiles/cloud"));
		perfilNatylol = new TextureRegionDrawable(atlas.findRegion("perfiles/natylol"));
		perfilIgnis = new TextureRegionDrawable(atlas.findRegion("perfiles/ignis"));
		perfilcientifico = new TextureRegionDrawable(atlas.findRegion("perfiles/scientist"));
		perfilLAlba = new TextureRegionDrawable(atlas.findRegion("perfiles/lightingalba"));
		perfilenemigo = new TextureRegionDrawable(atlas.findRegion("perfiles/enemy"));

		SkeletonJson json = new SkeletonJson(atlas);

		json.setScale(1f);
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("data/menus/shoptitle.json"));
		animationTiendaTitle = skeletonData.findAnimation("animation");
		skeletonTiendaTitle = new Skeleton(skeletonData);

	}

	/**
	 * ################################################################## WORLD MAP #############################################################
	 */

	/** new struf */
	public TiledMap tiledWorldMap;
	// fin new stuff

	public Skeleton bolaSkeleton;
	public Animation bolaAnim;

	public Skeleton rayoSkeleton;
	public Animation rayoAnim;

	public Skeleton humoVolcanSkeleton;
	public Animation humoVolvanAnimation;

	public NinePatchDrawable btDerUp;
	public NinePatchDrawable btDerDown;
	public NinePatchDrawable btIzqUp;
	public NinePatchDrawable btIzqDown;

	public void cargarWorldMap(TextureAtlas atlas) {
		cargarComun();
		SkeletonJson json = new SkeletonJson(atlas);

		tiledWorldMap = get(atlasWorldTiledScreenRuta, TiledMap.class);

		json.setScale(.007f);
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("data/menus/ball.json"));
		bolaAnim = skeletonData.findAnimation("pulse");
		bolaSkeleton = new Skeleton(skeletonData);

		json.setScale(.0225f);
		skeletonData = json.readSkeletonData(Gdx.files.internal("data/menus/thunder.json"));
		rayoAnim = skeletonData.findAnimation("floating");
		rayoSkeleton = new Skeleton(skeletonData);

		json.setScale(.025f);
		skeletonData = json.readSkeletonData(Gdx.files.internal("data/menus/humovolcan.json"));
		humoVolvanAnimation = skeletonData.findAnimation("humo");
		humoVolcanSkeleton = new Skeleton(skeletonData);

		btDerUp = new NinePatchDrawable(new NinePatch(atlas.findRegion("derSinPresionar")));
		btDerDown = new NinePatchDrawable(new NinePatch(atlas.findRegion("derPresionado")));
		btIzqUp = new NinePatchDrawable(new NinePatch(atlas.findRegion("izqSinPresionar")));
		btIzqDown = new NinePatchDrawable(new NinePatch(atlas.findRegion("izqPresionado")));

	}

	/**
	 * ########################################################################## GameScreenTiled #################################################################################
	 */
	String rutaTiled;
	String rutaMusica;
	String atlasTiledStuff = "data/animaciones/animacionesJuego.txt";

	public AtlasRegion fondo;// Lo estoy cargando en atlasComun para ahorrar espacio OMG! ajaj

	public TiledMap tiledMap;
	public SkeletonData ponySkeletonData;

	public SkeletonData skeletonBombData;
	public Animation bombAnim;
	public Animation bombExAnim;

	public SkeletonData skeletonMonedaData;
	public Animation monedaAnim;
	public Animation monedaTomadaAnim;

	public SkeletonData chileSkeletonData;
	public Animation chileAnim;
	public Animation chileTomadaAnim;

	public SkeletonData globoSkeletonData;
	public Animation globoAnim;
	public Animation globoTomadaAnim;

	public SkeletonData dulceSkeletonData;
	public Animation dulceAnim;
	public Animation dulceTomadaAnim;

	public Skeleton fondoSkeleton;
	public Animation fondoAnim;

	public Skeleton fuegoSkeleton;
	public Animation fuegoAnim;

	public Skeleton fogataSkeleton;
	public Animation fogataAnim;

	public Skeleton plumaSkeleton;
	public Animation plumaAnim;

	public Skeleton bloodStoneSkeleton;
	public Animation bloodStoneAnim;

	public Skeleton bloodStone2Skeleton;
	public Animation bloodStone2Anim;

	public Skeleton finCarreraSkeleton;
	public Animation finCarreraAnimacion1Lugar;
	public Animation finCarreraAnimacion2Lugar;
	public Animation finCarreraAnimacion3Lugar;
	public Animation finCarreraAnimacionTimesUp;
	public Animation finCarreraAnimacionGameOver;

	public NinePatchDrawable padIzq;
	public NinePatchDrawable padDer;
	public NinePatchDrawable btBombaUp;
	public NinePatchDrawable btBombaDown;

	public NinePatchDrawable btTroncoUp;
	public NinePatchDrawable btTroncoDown;

	public NinePatchDrawable btJumpUp;
	public NinePatchDrawable btJumpDown;

	public NinePatchDrawable btPauseUp;

	public NinePatchDrawable fondoVentanaPause;

	public AtlasRegion indicador;
	public AtlasRegion indicadorCloud;
	public AtlasRegion indicadorCientifico;
	public AtlasRegion indicadorMinion;
	public AtlasRegion indicadorNatylol;
	public AtlasRegion indicadorLighthingAlba;
	public AtlasRegion indicadorIgnis;
	public AtlasRegion lugaresMarco;
	public AtlasRegion moneda;

	public AtlasRegion perfilRegionCloud;
	public AtlasRegion perfilRegionNatylol;
	public AtlasRegion perfilRegionIgnis;
	public AtlasRegion perfilRegionCientifico;
	public AtlasRegion perfilRegionLAlba;
	public AtlasRegion perfilRegionEnemigo;

	public AtlasRegion tronco;
	public AtlasRegion tachuelas;
	public AtlasRegion platano;

	public Sound pickCoin;
	public Sound jump;

	/** Primero se llama el load, cuando se termina de cargar se llama cargar **/
	public void loadGameScreenTiled(int nivelTiled) {
		cargarComun();
		String carpeta = "tiled";
		if (usarPacked)
			carpeta = "tiledp";

		if (nivelTiled == 1) {
			rutaTiled = "data/" + carpeta + "/mundo01.tmx";
			// rutaTiled =
			rutaMusica = "data/musica/01.mp3";
		}
		else if (nivelTiled == 2) {
			rutaTiled = "data/" + carpeta + "/mundo02.tmx";
			rutaMusica = "data/musica/02.mp3";
		}
		else if (nivelTiled == 3) {
			rutaTiled = "data/" + carpeta + "/mundo03.tmx";
			rutaMusica = "data/musica/03.mp3";
		}
		else if (nivelTiled == 4) {
			rutaTiled = "data/" + carpeta + "/mundo04.tmx";
			rutaMusica = "data/musica/04.mp3";
		}
		else if (nivelTiled == 5) {
			rutaTiled = "data/" + carpeta + "/mundo05.tmx";
			rutaMusica = "data/musica/05.mp3";
		}
		else if (nivelTiled == 6) {
			rutaTiled = "data/" + carpeta + "/mundo06.tmx";
			rutaMusica = "data/musica/01.mp3";
		}
		else if (nivelTiled == 7) {
			rutaTiled = "data/" + carpeta + "/mundo07.tmx";
			rutaMusica = "data/musica/02.mp3";
		}
		else if (nivelTiled == 8) {
			rutaTiled = "data/" + carpeta + "/mundo08.tmx";
			rutaMusica = "data/musica/03.mp3";
		}
		else if (nivelTiled == 9) {
			rutaTiled = "data/" + carpeta + "/mundo09.tmx";
			rutaMusica = "data/musica/04.mp3";
		}
		else if (nivelTiled == 10) {
			rutaTiled = "data/" + carpeta + "/mundo10.tmx";
			rutaMusica = "data/musica/05.mp3";
		}
		else if (nivelTiled == 11) {
			rutaTiled = "data/" + carpeta + "/mundo11.tmx";
			rutaMusica = "data/musica/01.mp3";
		}
		else if (nivelTiled == 12) {
			rutaTiled = "data/" + carpeta + "/mundo12.tmx";
			rutaMusica = "data/musica/02.mp3";
		}
		else if (nivelTiled == 12) {
			rutaTiled = "data/" + carpeta + "/mundo12.tmx";
			rutaMusica = "data/musica/03.mp3";
		}
		else if (nivelTiled == 13) {
			rutaTiled = "data/" + carpeta + "/mundo13.tmx";
			rutaMusica = "data/musica/04.mp3";
		}
		else if (nivelTiled == 14) {
			rutaTiled = "data/" + carpeta + "/mundo14.tmx";
			rutaMusica = "data/musica/05.mp3";
		}

		else if (nivelTiled == 15) {
			rutaTiled = "data/" + carpeta + "/mundo15.tmx";
			rutaMusica = "data/musica/01.mp3";
		}
		else if (nivelTiled == 16) {
			rutaTiled = "data/" + carpeta + "/mundo16.tmx";
			rutaMusica = "data/musica/02.mp3";
		}
		else if (nivelTiled == 17) {
			rutaTiled = "data/" + carpeta + "/mundo17.tmx";
			rutaMusica = "data/musica/03.mp3";
		}

		else if (nivelTiled == 1000) {// Mundo de muchas monedas
			int mundo = new Random().nextInt(2);

			if (mundo == 0) {
				rutaTiled = "data/" + carpeta + "/especial01.tmx";
			}
			else {
				rutaTiled = "data/" + carpeta + "/especial02.tmx";
			}

			rutaMusica = "data/musica/01.mp3";
		}

		if (!isLoaded(rutaMusica))
			load(rutaMusica, Music.class);

		if (!isLoaded(atlasTiledStuff))
			load(atlasTiledStuff, TextureAtlas.class);

		if (!isLoaded(rutaTiled))
			load(rutaTiled, TiledMap.class);

		if (!isLoaded("data/musica/coin.mp3"))
			load("data/musica/coin.mp3", Sound.class);

		if (!isLoaded("data/musica/salto.mp3"))
			load("data/musica/salto.mp3", Sound.class);

	}

	/** Antes de llamar a este metodo se debe llamar loadMenuPrincipal **/
	public void cargarGameScreenTiled() {

		musicaTiled = get(rutaMusica, Music.class);
		musicaTiled.setLooping(true);
		platMusicInGame();

		TextureAtlas atlas = get(atlasTiledStuff, TextureAtlas.class);
		tiledMap = get(rutaTiled, TiledMap.class);

		SkeletonJson json = new SkeletonJson(atlas);
		json.setScale(.01f);
		ponySkeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/personajes.json"));
		// ponySkeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/characters.json"));
		json.setScale(.004f);
		skeletonBombData = json.readSkeletonData(Gdx.files.internal("data/animaciones/bombs.json"));
		bombAnim = skeletonBombData.findAnimation("b1");
		bombExAnim = skeletonBombData.findAnimation("b2x");

		json.setScale(.005f);
		skeletonMonedaData = json.readSkeletonData(Gdx.files.internal("data/animaciones/coin.json"));
		monedaAnim = skeletonMonedaData.findAnimation("normal");
		monedaTomadaAnim = skeletonMonedaData.findAnimation("plus1");

		json.setScale(.009f);
		chileSkeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/chile.json"));
		chileAnim = chileSkeletonData.findAnimation("normal");
		chileTomadaAnim = chileSkeletonData.findAnimation("toospicy");

		json.setScale(.009f);
		globoSkeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/ballons.json"));
		globoAnim = globoSkeletonData.findAnimation("normal");
		globoTomadaAnim = globoSkeletonData.findAnimation("plus5");

		json.setScale(.009f);
		dulceSkeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/chocolate.json"));
		dulceAnim = dulceSkeletonData.findAnimation("normal");
		dulceTomadaAnim = dulceSkeletonData.findAnimation("speedup");

		json.setScale(1f);
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/alterminar.json"));
		finCarreraAnimacion1Lugar = skeletonData.findAnimation("1stplace");
		finCarreraAnimacion2Lugar = skeletonData.findAnimation("2ndplace");
		finCarreraAnimacion3Lugar = skeletonData.findAnimation("3rdplace");
		finCarreraAnimacionTimesUp = skeletonData.findAnimation("timeup");
		finCarreraAnimacionGameOver = skeletonData.findAnimation("youlose");
		finCarreraSkeleton = new Skeleton(skeletonData);

		// json.setScale(.003f);
		// SkeletonData fuegoSkeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/bombs.json"));
		// fuegoAnim = fuegoSkeletonData.findAnimation("firedancing");
		// fuegoSkeleton = new Skeleton(fuegoSkeletonData);

		json.setScale(.01f);
		SkeletonData fondoSkeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/background.json"));
		fondoAnim = fondoSkeletonData.findAnimation("animation");
		fondoSkeleton = new Skeleton(fondoSkeletonData);

		//
		// json.setScale(.011f);
		// SkeletonData fogataSkeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/fogata.json"));
		// fogataAnim = fogataSkeletonData.findAnimation("fogata");
		// fogataSkeleton = new Skeleton(fogataSkeletonData);
		//
		// json.setScale(.005f);
		// SkeletonData plumaSkeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/feather.json"));
		// plumaAnim = plumaSkeletonData.findAnimation("pluma");
		// plumaSkeleton = new Skeleton(plumaSkeletonData);
		//
		// json.setScale(.011f);
		// SkeletonData bloodStoneSkeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/bloodstone.json"));
		// bloodStoneAnim = bloodStoneSkeletonData.findAnimation("animation");
		// bloodStoneSkeleton = new Skeleton(bloodStoneSkeletonData);
		//
		// SkeletonData bloodStone2SkeletonData = json.readSkeletonData(Gdx.files.internal("data/animaciones/bloodstones.json"));
		// bloodStone2Anim = bloodStone2SkeletonData.findAnimation("glow1");
		// bloodStone2Skeleton = new Skeleton(bloodStone2SkeletonData);

		fondo = atlas.findRegion("imagenes/fondo");

		padIzq = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/pad_izq")));
		padDer = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/pad_derecha")));
		btBombaDown = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/bombasalpresionar")));
		btBombaUp = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/bombasinpresionar")));

		btJumpDown = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/saltoalpresionar")));
		btJumpUp = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/saltosinpresionar")));
		// btTroncoUp = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/botontroncosinpresionar")));
		// btTroncoDown = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/botontroncopresionado")));

		btTroncoUp = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/btPlatanoTachuelas")));
		btTroncoDown = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/btPlatanoTachuelasPresionado")));

		btPauseUp = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/pause")));

		fondoVentanaPause = new NinePatchDrawable(new NinePatch(atlas.findRegion("Interfaz/pausa")));

		indicador = atlas.findRegion("Interfaz/indicador");
		indicadorCloud = atlas.findRegion("Interfaz/icono000");
		indicadorCientifico = atlas.findRegion("Interfaz/icono001");
		indicadorMinion = atlas.findRegion("Interfaz/icono002");
		indicadorNatylol = atlas.findRegion("Interfaz/icono003");
		indicadorLighthingAlba = atlas.findRegion("Interfaz/icono004");
		indicadorIgnis = atlas.findRegion("Interfaz/icono005");

		perfilRegionCloud = atlas.findRegion("perfiles/cloud");
		perfilRegionNatylol = atlas.findRegion("perfiles/natylol");
		perfilRegionIgnis = atlas.findRegion("perfiles/ignis");
		perfilRegionCientifico = atlas.findRegion("perfiles/scientist");
		perfilRegionLAlba = atlas.findRegion("perfiles/lightingalba");
		perfilRegionEnemigo = atlas.findRegion("perfiles/enemy");

		lugaresMarco = atlas.findRegion("Interfaz/lugares");

		moneda = atlas.findRegion("moneda");

		tronco = atlas.findRegion("tachuelas");
		tachuelas = atlas.findRegion("tachuelas");
		platano = atlas.findRegion("platano");

		pickCoin = get("data/musica/coin.mp3");
		jump = get("data/musica/salto.mp3");
	}

	public void unloadGameScreenTiled() {
		musicaTiled.stop();
		unload(rutaMusica);
		unload(atlasTiledStuff);
		unload(rutaTiled);
		unload("data/musica/coin.mp3");
		unload("data/musica/salto.mp3");
		System.gc();
	}

	public void playMusicMenus() {
		if (Settings.isMusicaON) {
			if (!musicaMenus.isPlaying())
				musicaMenus.play();
		}
		else {
			musicaMenus.stop();
		}

	}

	public void platMusicInGame() {
		if (Settings.isMusicaON) {
			if (!musicaTiled.isPlaying())
				musicaTiled.play();
		}
		else {
			musicaTiled.stop();
		}
	}

	public void playSound(Sound sound, float volumen) {
		if (Settings.isSonidoON)
			sound.play(volumen);
	}

	public void playSound(Sound sound) {
		if (Settings.isSonidoON)
			sound.play(1);
	}

}
