package pk.pokeclone.tiled;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.GdxRuntimeException;
import pk.pokeclone.PokeClone;

public class TiledPhysics {

    public static FixtureDef fixtureDef(MapObject mapObject, Vector2 scaling, Vector2 relativeTo) {
        if(mapObject instanceof RectangleMapObject rectMapObject) {
            return rectangleFixtureDef(rectMapObject, scaling, relativeTo);
        } else if (mapObject instanceof PolygonMapObject polygonMapObj) {
            Polygon polygon = polygonMapObj.getPolygon();
            float offsetX = polygon.getX() * PokeClone.SCALE;
            float offsetY = polygon.getY() * PokeClone.SCALE;
            return polygonFixtureDef(polygonMapObj, polygon.getVertices(), offsetX, offsetY, scaling, relativeTo);
        } else if (mapObject instanceof EllipseMapObject ellipseMapObj) {
            return ellipseFixtureDef(ellipseMapObj, scaling, relativeTo);
        }

        throw new GdxRuntimeException("undefined collision object");
    }

    private static FixtureDef rectangleFixtureDef(RectangleMapObject rectMapObject, Vector2 scaling, Vector2 relativeTo) {
        Rectangle rectangle = rectMapObject.getRectangle();

        float rectX = rectangle.x;
        float rectY = rectangle.y;
        float rectW = rectangle.width;
        float rectH = rectangle.height;

        float boxX = rectX * PokeClone.SCALE * scaling.x - relativeTo.x;
        float boxY = rectY * PokeClone.SCALE * scaling.y - relativeTo.y;
        float boxW = rectW * PokeClone.SCALE * scaling.x * 0.5f;
        float boxH = rectH * PokeClone.SCALE * scaling.y * 0.5f;

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(boxW, boxH, new Vector2(boxX + boxW, boxY + boxH), 0f);

        return fixtureDefinitionOfMapObjectAndShape(rectMapObject, shape);
    }

    private static FixtureDef ellipseFixtureDef(EllipseMapObject mapObject, Vector2 scaling, Vector2 relativeTo) {
        Ellipse ellipse = mapObject.getEllipse();
        float x = ellipse.x;
        float y = ellipse.y;
        float w = ellipse.width;
        float h = ellipse.height;

        float ellipseX = x * PokeClone.SCALE * scaling.x - relativeTo.x;
        float ellipseY = y * PokeClone.SCALE * scaling.y - relativeTo.y;
        float ellipseW = w * PokeClone.SCALE * scaling.x * 0.5f;
        float ellipseH = h * PokeClone.SCALE * scaling.y * 0.5f;

        if (MathUtils.isEqual(ellipseW, ellipseH, 0.1f)) {
            // width and height are equal -> return a circle shape
            CircleShape shape = new CircleShape();
            shape.setPosition(new Vector2(ellipseX + ellipseW, ellipseY + ellipseH));
            shape.setRadius(ellipseW);
            return fixtureDefinitionOfMapObjectAndShape(mapObject, shape);
        }

        // width and height are not equal -> return an ellipse shape (=polygon with 'numVertices' vertices)
        // PolygonShape only supports 8 vertices
        // ChainShape supports more but does not properly collide in some scenarios
        final int numVertices = 8;
        float angleStep = MathUtils.PI2 / numVertices;
        Vector2[] vertices = new Vector2[numVertices];

        for (int vertexIdx = 0; vertexIdx < numVertices; vertexIdx++) {
            float angle = vertexIdx * angleStep;
            float offsetX = ellipseW * MathUtils.cos(angle);
            float offsetY = ellipseH * MathUtils.sin(angle);
            vertices[vertexIdx] = new Vector2(ellipseX + ellipseW + offsetX, ellipseY + ellipseH + offsetY);
        }

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        return fixtureDefinitionOfMapObjectAndShape(mapObject, shape);
    }

    private static FixtureDef polygonFixtureDef(
        MapObject mapObject, // Could be PolygonMapObject or PolylineMapObject
        float[] polyVertices,
        float offsetX,
        float offsetY,
        Vector2 scaling,
        Vector2 relativeTo
    ) {
        offsetX = (offsetX * scaling.x) - relativeTo.x;
        offsetY = (offsetY * scaling.y) - relativeTo.y;
        float[] vertices = new float[polyVertices.length];
        for (int vertexIdx = 0; vertexIdx < polyVertices.length; vertexIdx += 2) {
            // x-coordinate
            vertices[vertexIdx] = offsetX + polyVertices[vertexIdx] * PokeClone.SCALE * scaling.x;
            // y-coordinate
            vertices[vertexIdx + 1] = offsetY + polyVertices[vertexIdx + 1] * PokeClone.SCALE * scaling.y;
        }

        ChainShape shape = new ChainShape();
        if (mapObject instanceof PolygonMapObject) {
            shape.createLoop(vertices);
        } else { // PolylineMapObject
            shape.createChain(vertices);
        }
        return fixtureDefinitionOfMapObjectAndShape(mapObject, shape);
    }

    private static FixtureDef fixtureDefinitionOfMapObjectAndShape(MapObject rectMapObject, Shape shape) {
        FixtureDef def = new FixtureDef();
        def.shape = shape;
        def.friction = rectMapObject.getProperties().get(TiledPropertyNames.FRICTION, 0f, Float.class);
        def.restitution = rectMapObject.getProperties().get(TiledPropertyNames.RESTITUTION, 0f, Float.class);;
        def.density = rectMapObject.getProperties().get(TiledPropertyNames.DENSITY, 0f, Float.class);;
        def.isSensor = rectMapObject.getProperties().get(TiledPropertyNames.SENSOR, false, Boolean.class);;
        return def;
    }
}
