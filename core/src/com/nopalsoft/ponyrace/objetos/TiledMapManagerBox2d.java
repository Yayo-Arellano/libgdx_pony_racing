package com.nopalsoft.ponyrace.objetos;

import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Logger;
import com.nopalsoft.ponyrace.Settings;
import com.nopalsoft.ponyrace.game.WorldTiled;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class TiledMapManagerBox2d {

    public final static short CONTACT_CORREDORES = -1;

    private WorldTiled oWorld;
    private World oWorldBox;
    private float m_units;
    private Logger logger;
    private FixtureDef defaultFixture;
    private LinkedHashMap<Integer, String> nombrePonys;

    public TiledMapManagerBox2d(WorldTiled oWorld, float unitsPerPixel) {
        this.oWorld = oWorld;
        oWorldBox = oWorld.oWorldBox;
        m_units = unitsPerPixel;
        logger = new Logger("MapBodyManager", 1);
        nombrePonys = oWorld.game.oAssets.nombrePonys;

        defaultFixture = new FixtureDef();
        defaultFixture.density = 1.0f;
        defaultFixture.friction = .5f;
        defaultFixture.restitution = 0.0f;

    }

    public void createObjetosDesdeTiled(Map map) {
        crearFisicos(map, "fisicos");
        crearInteaccion(map, "interaccion");

    }

    private void crearInteaccion(Map map, String layerName) {
        MapLayer layer = map.getLayers().get(layerName);

        if (layer == null) {
            logger.error("layer " + layerName + " no existe");
            return;
        }

        MapObjects objects = layer.getObjects();
        Iterator<MapObject> objectIt = objects.iterator();
        while (objectIt.hasNext()) {
            MapObject object = objectIt.next();

            if (object instanceof TextureMapObject) {
                continue;
            }

            MapProperties properties = object.getProperties();
            String tipo = (String) properties.get("type");
            if (tipo == null)
                continue;
            else if (tipo.equals("moneda")) {
                crearMoneda(object);
                continue;
            } else if (tipo.equals("dulce")) {
                crearDulce(object);
                continue;
            } else if (tipo.equals("chile")) {
                crearChile(object);
                continue;
            } else if (tipo.equals("globo")) {
                crearGlobo(object);
                continue;
            }

        }

    }

    public void crearFisicos(Map map, String layerName) {
        MapLayer layer = map.getLayers().get(layerName);

        if (layer == null) {
            logger.error("layer " + layerName + " no existe");
            return;
        }

        MapObjects objects = layer.getObjects();
        Iterator<MapObject> objectIt = objects.iterator();

        while (objectIt.hasNext()) {
            MapObject object = objectIt.next();

            if (object instanceof TextureMapObject) {
                continue;
            }

            MapProperties properties = object.getProperties();
            String tipo = (String) properties.get("type");
            if (tipo == null)
                continue;
            else if (tipo.equals("pony")) {
                crearPony(object, tipo);
                continue;
            } else if (tipo.equals("malos")) {
                crearPony(object, tipo);
                continue;
            } else if (tipo.equals("plumas")) {
                crearPlumas(object);
                continue;
            } else if (tipo.equals("fogata")) {
                crearFogata(object);
                continue;
            } else if (tipo.equals("pisable")) {
                if (object instanceof RectangleMapObject) {

                    crearPisable(object);
                    continue;
                }
            } else if (tipo.equals("fin")) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
                float y = (rectangle.y + rectangle.height * 0.5f) * m_units;
                oWorld.finJuego = new Vector2(x, y);
                continue;
            } else if (tipo.equals("gemaChica")) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
                float y = (rectangle.y + rectangle.height * 0.5f) * m_units;
                oWorld.arrBloodStone.add(new BloodStone(x, y, BloodStone.Tipo.chica, oWorld.oRan));
                continue;
            } else if (tipo.equals("gemaMediana")) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
                float y = (rectangle.y + rectangle.height * 0.5f) * m_units;
                oWorld.arrBloodStone.add(new BloodStone(x, y, BloodStone.Tipo.mediana, oWorld.oRan));
                continue;
            } else if (tipo.equals("gemaGrande")) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
                float y = (rectangle.y + rectangle.height * 0.5f) * m_units;
                oWorld.arrBloodStone.add(new BloodStone(x, y, BloodStone.Tipo.grande, oWorld.oRan));
                continue;
            }

            Shape shape;
            if (object instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject) object);
            } else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject) object);
            } else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject) object);
            } else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject) object);
            } else {
                logger.error("non suported shape " + object);
                continue;
            }

            defaultFixture.shape = shape;

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.StaticBody;

            if (tipo.equals("bandera1") || tipo.equals("caer") || tipo.equals("saltoDerecha") || tipo.equals("bandera") || tipo.equals("regresoHoyo")
                    || tipo.equals("caminarIzquierda") || tipo.equals("caminarDerecha") || tipo.equals("saltoIzquierda") || tipo.equals("salto")) {
                defaultFixture.isSensor = true;
            }

            Body body = oWorldBox.createBody(bodyDef);
            body.createFixture(defaultFixture);

            if (tipo.equals("bandera1")) {
                if (properties.get("jump").equals("left"))
                    body.setUserData(new Bandera1(oWorld,
                            Bandera1.TipoAccion.saltoIzq));
                else if (properties.get("jump").equals("right"))
                    body.setUserData(new Bandera1(oWorld,
                            Bandera1.TipoAccion.saltoDer));
                else if (properties.get("jump").equals("stand"))
                    body.setUserData(new Bandera1(oWorld, Bandera1.TipoAccion.salto));

            } else
                body.setUserData(tipo);

            defaultFixture.shape = null;
            defaultFixture.isSensor = false;
            defaultFixture.filter.groupIndex = 0;
            shape.dispose();

        }

    }

    private void crearPisable(MapObject object) {
        Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) * m_units, (rectangle.y + rectangle.height * 0.5f) * m_units);
        polygon.setAsBox(rectangle.getWidth() * 0.5f * m_units, rectangle.height * 0.5f * m_units, size, 0.0f);
        defaultFixture.shape = polygon;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        Body body = oWorldBox.createBody(bodyDef);
        body.createFixture(defaultFixture);

        float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
        float y = (rectangle.y + rectangle.height * 0.5f) * m_units;
        float height = (rectangle.height * m_units * 0.5f);
        float width = (rectangle.width * m_units * 0.5f);
        body.setUserData(new Pisable(x, y, width, height));

    }

    private Shape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) * m_units, (rectangle.y + rectangle.height * 0.5f) * m_units);
        polygon.setAsBox(rectangle.getWidth() * 0.5f * m_units, rectangle.height * 0.5f * m_units, size, 0.0f);
        return polygon;
    }

    private Shape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius * m_units);
        circleShape.setPosition(new Vector2(circle.x * m_units, circle.y * m_units));
        return circleShape;

    }

    private Shape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getVertices();
        float[] worldVertices = new float[vertices.length];
        float yLost = polygonObject.getPolygon().getY() * m_units;
        float xLost = polygonObject.getPolygon().getX() * m_units;

        for (int i = 0; i < vertices.length; ++i) {
            if (i % 2 == 0)
                worldVertices[i] = vertices[i] * m_units + xLost;
            else
                worldVertices[i] = vertices[i] * m_units + yLost;
        }
        polygon.set(worldVertices);

        return polygon;
    }

    private Shape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getVertices();

        Vector2[] worldVertices = new Vector2[vertices.length / 2];
        float yLost = polylineObject.getPolyline().getY() * m_units;
        float xLost = polylineObject.getPolyline().getX() * m_units;
        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] * m_units + xLost;
            worldVertices[i].y = vertices[i * 2 + 1] * m_units + yLost;
        }
        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }

    int contadorPonisCreados = 0;

    private void crearPony(MapObject object, String tipo) {
        Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
        float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
        float y = (rectangle.y + rectangle.height * 0.5f) * m_units;

        Pony oPony;

        String nombreSkin;

        if (tipo.equals("pony")) {
            nombreSkin = Settings.skinSeleccionada;
            oPony = new PonyPlayer(x, y, nombreSkin, oWorld);

        } else {// Ponis malos

            if (Settings.skinSeleccionada.equals(nombrePonys.get(contadorPonisCreados)))
                contadorPonisCreados++;
            nombreSkin = nombrePonys.get(contadorPonisCreados);

            oPony = new PonyMalo(x, y, nombreSkin, oWorld);
        }
        contadorPonisCreados++; // Se comenta esta linea si se quieren poner muchos ponys. PAra debugear

        BodyDef bd = new BodyDef();
        bd.position.y = oPony.position.y;
        bd.position.x = oPony.position.x;
        bd.type = BodyType.DynamicBody;
        Body oBody = oWorldBox.createBody(bd);

        PolygonShape pies = new PolygonShape();
        // pies.setAsBox(.2f, .23f);//Lo puse mejor con vertices pa que no tuviera esquinas picudas y tratar de minimizar
        // que los ponis se caian al vacio

        float[] vert = {-.2f, -.2f, -.18f, -.23f, .18f, -.23f, .2f, -.2f, .2f, .23f, -.2f, .23f};
        pies.set(vert);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = pies;
        fixture.density = .5f;
        fixture.restitution = 0f;
        fixture.friction = 0f;
        fixture.filter.groupIndex = CONTACT_CORREDORES;
        Fixture cuerpo = oBody.createFixture(fixture);
        cuerpo.setUserData("cuerpo");
        oBody.createFixture(fixture);

        // Sensor pa cuando tenga el chile pueda danar a los enemigos
        // pies.setAsBox(.2f, .23f, new Vector2(.1f, .2f), 0);
        fixture.shape = pies;
        fixture.density = .1f;
        fixture.restitution = 0;
        fixture.friction = 0f;
        fixture.isSensor = true;
        fixture.filter.groupIndex = 0;
        cuerpo = oBody.createFixture(fixture);
        cuerpo.setUserData("cuerpoSensor");
        oBody.createFixture(fixture);

        CircleShape sensorBottomIzq = new CircleShape();
        sensorBottomIzq.setRadius(.03f);
        sensorBottomIzq.setPosition(new Vector2(-.14f, -.23f));
        fixture.shape = sensorBottomIzq;
        fixture.density = 03f;
        fixture.restitution = 0f;
        fixture.friction = 0;
        fixture.isSensor = true;
        fixture.filter.groupIndex = CONTACT_CORREDORES;
        Fixture sensorBottomIzqFixture = oBody.createFixture(fixture);
        sensorBottomIzqFixture.setUserData("sensorBottomIzq");
        oBody.createFixture(fixture);

        sensorBottomIzq = new CircleShape();
        sensorBottomIzq.setRadius(.03f);
        sensorBottomIzq.setPosition(new Vector2(.14f, -.23f));
        fixture.shape = sensorBottomIzq;
        fixture.density = 03f;
        fixture.restitution = 0f;
        fixture.friction = 0;
        fixture.isSensor = true;
        fixture.filter.groupIndex = CONTACT_CORREDORES;
        sensorBottomIzqFixture = oBody.createFixture(fixture);
        sensorBottomIzqFixture.setUserData("sensorBottomDer");
        oBody.createFixture(fixture);

        // oBody.setFixedRotation(true);
        oBody.setUserData(oPony);
        oBody.setBullet(true);

        if (tipo.equals("pony")) {
            oWorld.oPony = (PonyPlayer) oPony;
        } else {// ponis malos
            oWorld.arrPonysMalos.add((PonyMalo) oPony);
        }

        pies.dispose();
        sensorBottomIzq.dispose();
    }

    private void crearFogata(MapObject object) {
        PolygonShape polygon = new PolygonShape();
        PolygonMapObject polygonObject = (PolygonMapObject) object;
        float[] vertices = polygonObject.getPolygon().getVertices();
        float[] worldVertices = new float[vertices.length];
        float yLost = polygonObject.getPolygon().getY() * m_units;
        float xLost = polygonObject.getPolygon().getX() * m_units;

        for (int i = 0; i < vertices.length; ++i) {
            if (i % 2 == 0)
                worldVertices[i] = vertices[i] * m_units + xLost;
            else
                worldVertices[i] = vertices[i] * m_units + yLost;
        }

        Polygon as = new Polygon(vertices);
        Fogata oFogata;
        oFogata = new Fogata(as.getBoundingRectangle().width / 2f * m_units + as.getBoundingRectangle().x * m_units + xLost,
                as.getBoundingRectangle().height / 2f * m_units + as.getBoundingRectangle().y * m_units + yLost - .15f, oWorld.oRan);

        polygon.set(worldVertices);

        FixtureDef fixture = new FixtureDef();
        fixture.density = 1.0f;
        fixture.friction = 1f;
        fixture.restitution = 0.0f;
        fixture.shape = polygon;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;

        Body body = oWorldBox.createBody(bodyDef);
        body.createFixture(fixture);
        body.setUserData(oFogata);

        oWorld.arrFogatas.add(oFogata);
        polygon.dispose();
    }

    private void crearPlumas(MapObject object) {
        Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
        float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
        float y = (rectangle.y + rectangle.height * 0.5f) * m_units;

        Pluma obj = new Pluma(x, y, oWorld.oRan);
        BodyDef bd = new BodyDef();
        bd.position.y = obj.position.y;
        bd.position.x = obj.position.x;
        bd.type = BodyType.StaticBody;

        PolygonShape pies = new PolygonShape();
        pies.setAsBox(.1f, .1f);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = pies;
        fixture.density = .5f;
        fixture.restitution = 0f;
        fixture.friction = 0f;
        fixture.isSensor = true;
        fixture.filter.groupIndex = CONTACT_CORREDORES;

        Body oBody = oWorldBox.createBody(bd);
        oBody.createFixture(fixture);

        oBody.setUserData(obj);
        oWorld.arrPlumas.add(obj);
        pies.dispose();
    }

    private void crearMoneda(MapObject object) {
        Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
        float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
        float y = (rectangle.y + rectangle.height * 0.5f) * m_units;

        Moneda obj = new Moneda(x, y, oWorld);
        BodyDef bd = new BodyDef();
        bd.position.y = obj.position.y;
        bd.position.x = obj.position.x;
        bd.type = BodyType.StaticBody;

        PolygonShape pies = new PolygonShape();
        pies.setAsBox(.1f, .1f);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = pies;
        fixture.density = .5f;
        fixture.restitution = 0f;
        fixture.friction = 0f;
        fixture.isSensor = true;
        // fixture.filter.groupIndex = CONTACT_CORREDORES;

        Body oBody = oWorldBox.createBody(bd);
        oBody.createFixture(fixture);

        oBody.setUserData(obj);
        oWorld.arrMonedas.add(obj);
        pies.dispose();

    }

    private void crearChile(MapObject object) {
        Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
        float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
        float y = (rectangle.y + rectangle.height * 0.5f) * m_units;

        Chile obj = new Chile(x, y, oWorld);
        BodyDef bd = new BodyDef();
        bd.position.y = obj.position.y;
        bd.position.x = obj.position.x;
        bd.type = BodyType.StaticBody;

        PolygonShape pies = new PolygonShape();
        pies.setAsBox(.25f, .15f);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = pies;
        fixture.density = .5f;
        fixture.restitution = 0f;
        fixture.friction = 0f;
        fixture.isSensor = true;
        // fixture.filter.groupIndex = CONTACT_CORREDORES;

        Body oBody = oWorldBox.createBody(bd);
        oBody.createFixture(fixture);

        oBody.setUserData(obj);
        oWorld.arrChiles.add(obj);
        pies.dispose();

    }

    private void crearGlobo(MapObject object) {
        Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
        float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
        float y = (rectangle.y + rectangle.height * 0.5f) * m_units;

        Globo obj = new Globo(x, y, oWorld);
        BodyDef bd = new BodyDef();
        bd.position.y = obj.position.y;
        bd.position.x = obj.position.x;
        bd.type = BodyType.StaticBody;

        PolygonShape pies = new PolygonShape();
        pies.setAsBox(.15f, .4f);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = pies;
        fixture.density = .5f;
        fixture.restitution = 0f;
        fixture.friction = 0f;
        fixture.isSensor = true;
        // fixture.filter.groupIndex = CONTACT_CORREDORES;

        Body oBody = oWorldBox.createBody(bd);
        oBody.createFixture(fixture);

        oBody.setUserData(obj);
        oWorld.arrGlobos.add(obj);
        pies.dispose();

    }

    private void crearDulce(MapObject object) {
        Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
        float x = (rectangle.x + rectangle.width * 0.5f) * m_units;
        float y = (rectangle.y + rectangle.height * 0.5f) * m_units;

        Dulce obj = new Dulce(x, y, oWorld);
        BodyDef bd = new BodyDef();
        bd.position.y = obj.position.y;
        bd.position.x = obj.position.x;
        bd.type = BodyType.StaticBody;

        PolygonShape pies = new PolygonShape();
        pies.setAsBox(.15f, .25f);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = pies;
        fixture.density = .5f;
        fixture.restitution = 0f;
        fixture.friction = 0f;
        fixture.isSensor = true;
        // fixture.filter.groupIndex = CONTACT_CORREDORES;

        Body oBody = oWorldBox.createBody(bd);
        oBody.createFixture(fixture);

        oBody.setUserData(obj);
        oWorld.arrDulces.add(obj);
        pies.dispose();

    }

}
