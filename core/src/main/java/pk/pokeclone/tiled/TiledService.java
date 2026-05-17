package pk.pokeclone.tiled;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.utils.GdxRuntimeException;
import lombok.Setter;
import pk.pokeclone.asset.AssetService;
import pk.pokeclone.asset.MapAsset;

import java.util.function.Consumer;

public class TiledService {
    private AssetService assetService;
    private TiledMap currentMap = null;
    @Setter
    private Consumer<TiledMap> mapChangeConsumer = null;
    @Setter
    private Consumer<TiledMapTileMapObject> loadObjectConsumer = null;

    public TiledService(AssetService assetService) {
        this.assetService = assetService;
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
}
