package pk.pokeclone.tiled;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import pk.pokeclone.PokeClone;
import pk.pokeclone.asset.AssetService;
import pk.pokeclone.asset.AtlasAsset;
import pk.pokeclone.component.Controller;
import pk.pokeclone.component.Graphic;
import pk.pokeclone.component.Move;
import pk.pokeclone.component.Transform;

@AllArgsConstructor
public class TiledAshleyConfigurator {
    private final Engine engine;
    private final AssetService assetService;

    public void onLoadObject(TiledMapTileMapObject object) {
        Entity entity = engine.createEntity();
        TiledMapTile tile = object.getTile();
        TextureRegion region = getTextureRegion(tile);
        int z = tile.getProperties().get("z", 1, Integer.class);

        entity.add(new Graphic(region, Color.WHITE.cpy()));
        addEntityTransform(
            object.getX(), object.getY(),
            z,
            region.getRegionWidth(), region.getRegionHeight(),
            object.getScaleX(), object.getScaleY(),
            entity);

        addEntityController(tile, entity);
        addEntityMove(tile, entity);

        engine.addEntity(entity);
    }

    private void addEntityMove(TiledMapTile tile, Entity entity) {
        float speed = tile.getProperties().get("speed", 0f, Float.class);

        if(speed == 0) return;

        entity.add(new Move(speed));
    }

    private void addEntityController(TiledMapTile tile, Entity entity) {
        boolean controller = tile.getProperties().get("controller", false, Boolean.class);
        if(!controller) return;

        entity.add(new Controller());
    }

    private void addEntityTransform(float x, float y, int z, float w, float h, float scaleX, float scaleY, Entity entity) {
        Vector2 position = new Vector2(x, y);
        Vector2 size = new Vector2(w, h);
        Vector2 scaling = new Vector2(scaleX, scaleY);

        position.scl(PokeClone.SCALE);
        size.scl(PokeClone.SCALE);

        entity.add(new Transform(position, z, size, scaling, 0f));
    }

    private TextureRegion getTextureRegion(TiledMapTile tile) {
        String atlasAssetStr = tile.getProperties().get("atlasAsset", AtlasAsset.OBJECTS.name(), String.class);
        AtlasAsset atlasAsset = AtlasAsset.valueOf(atlasAssetStr);
        TextureAtlas textureAtlas = this.assetService.get(atlasAsset);
        FileTextureData textureData = (FileTextureData)tile.getTextureRegion().getTexture().getTextureData();
        String atlasKey = textureData.getFileHandle().nameWithoutExtension();

        TextureAtlas.AtlasRegion region = textureAtlas.findRegion(atlasKey + "/" + atlasKey);
        if(region != null) {
            return region;
        }

        return tile.getTextureRegion();
    }
}
