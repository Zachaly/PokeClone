package pk.pokeclone.tiled;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import lombok.Setter;
import pk.pokeclone.PokeClone;
import pk.pokeclone.asset.AssetService;
import pk.pokeclone.asset.MapAsset;

import java.util.function.Consumer;

public class TiledService {
    private final AssetService assetService;
    private final World world;
    private TiledMap currentMap = null;
    @Setter
    private Consumer<TiledMap> mapChangeConsumer = null;
    @Setter
    private Consumer<TiledMapTileMapObject> loadObjectConsumer = null;
    @Setter
    private LoadTileConsumer loadTileConsumer = null;

    public TiledService(AssetService assetService, World world) {
        this.assetService = assetService;
        this.world = world;
    }

    public TiledMap loadMap(MapAsset mapAsset) {
        TiledMap map = this.assetService.load(mapAsset);
        map.getProperties().put("mapAsset", mapAsset);
        return map;
    }

    public void setMap(TiledMap map) {
        if(currentMap != map && currentMap != null) {
            assetService.unload(currentMap.getProperties().get("mapAsset", MapAsset.class));
        }

        currentMap = map;

        loadMapObjects(map);

        if(mapChangeConsumer != null) {
            mapChangeConsumer.accept(map);
        }
    }

    private void loadMapObjects(TiledMap map) {
        for(MapLayer layer : map.getLayers()) {
            if("objects".equals(layer.getName())) {
                loadObjectLayer(layer);
            } else if(layer instanceof TiledMapTileLayer tileLayer) {
                loadTileLayer(tileLayer);
            }
        }

        spawnMapBoundary(map);
    }

    private void spawnMapBoundary(TiledMap map) {
        int width = map.getProperties().get("width", 0, Integer.class);
        int height = map.getProperties().get("height", 0, Integer.class);
        int tileWidth = map.getProperties().get("tilewidth", 0, Integer.class);
        int tileHeight = map.getProperties().get("tileheight", 0, Integer.class);

        float mapWidth = width * tileWidth * PokeClone.SCALE;
        float mapHeight = height * tileHeight * PokeClone.SCALE;

        float halfMapWidth = mapWidth / 2;
        float halfMapHeight = mapHeight / 2;
        float thickness = 0.5f;

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.setZero();
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(thickness, halfMapHeight, new Vector2(-thickness, halfMapHeight), 0);
        body.createFixture(shape, 0f).setFriction(0);
        shape.dispose();

        shape = new PolygonShape();
        shape.setAsBox(thickness, halfMapHeight, new Vector2(mapWidth + thickness, halfMapHeight), 0);
        body.createFixture(shape, 0f).setFriction(0);
        shape.dispose();

        shape = new PolygonShape();
        shape.setAsBox(halfMapWidth, thickness, new Vector2(halfMapWidth, -thickness), 0);
        body.createFixture(shape, 0f).setFriction(0);
        shape.dispose();

        shape = new PolygonShape();
        shape.setAsBox(halfMapWidth, thickness, new Vector2(halfMapWidth, mapHeight + thickness), 0);
        body.createFixture(shape, 0f).setFriction(0);
        shape.dispose();
    }

    private void loadTileLayer(TiledMapTileLayer tileLayer) {
        if(loadTileConsumer == null) return;

        for(int y = 0; y < tileLayer.getHeight(); y++) {
            for(int x = 0; x < tileLayer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                if(cell == null) continue;

                loadTileConsumer.accept(cell.getTile(), x, y);
            }
        }
    }

    private void loadObjectLayer(MapLayer objectLayer) {
        if(loadObjectConsumer == null) return;

        for(MapObject object : objectLayer.getObjects()) {
            if(object instanceof  TiledMapTileMapObject tileMapObject) {
                loadObjectConsumer.accept(tileMapObject);
            } else {
                throw new GdxRuntimeException("Invalid object");
            }
        }
    }

    @FunctionalInterface
    public interface LoadTileConsumer {
        void accept(TiledMapTile tile, float x, float y);
    }
}
