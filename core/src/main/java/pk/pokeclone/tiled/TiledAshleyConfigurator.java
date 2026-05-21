package pk.pokeclone.tiled;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import lombok.AllArgsConstructor;
import pk.pokeclone.PokeClone;
import pk.pokeclone.asset.AssetService;
import pk.pokeclone.asset.AtlasAsset;
import pk.pokeclone.component.*;
import pk.pokeclone.component.Transform;

@AllArgsConstructor
public class TiledAshleyConfigurator {
    private static final Vector2 DEFAULT_PHYSIC_SCALING = new Vector2(1, 1);

    private final Engine engine;
    private final AssetService assetService;
    private final World physicWorld;

    public void onLoadTile(TiledMapTile tiledMapTile, float x, float y) {
        createBody(
            tiledMapTile.getObjects(),
            new Vector2(x, y),
            DEFAULT_PHYSIC_SCALING,
            BodyDef.BodyType.StaticBody,
            Vector2.Zero,
            "environment"
        );
    }

    private Body createBody(MapObjects mapObjects, Vector2 position,
                            Vector2 scaling, BodyDef.BodyType bodyType,
                            Vector2 relativeTo, Object userData) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position);
        bodyDef.fixedRotation = true;

        Body body = physicWorld.createBody(bodyDef);

        body.setUserData(userData);

        for(MapObject object : mapObjects) {
            FixtureDef fixtureDef = TiledPhysics.fixtureDef(object, scaling, relativeTo);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData(object.getName());
            fixtureDef.shape.dispose();
        }

        return body;
    }

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
        addEntityAnimation(tile, entity);
        BodyDef.BodyType bodyType = getObjectBodyType(tile);
        addEntityPhysics(tile.getObjects(), bodyType, Vector2.Zero, entity);
        entity.add(new Facing(Facing.FacingDirection.DOWN));
        entity.add(new Fsm(entity));

        engine.addEntity(entity);
    }

    private void addEntityPhysics(MapObjects objects, BodyDef.BodyType bodyType, Vector2 relativeTo, Entity entity) {
        if(objects.getCount() == 0) return;

        Transform transform = Transform.MAPPER.get(entity);
        Body body = createBody(objects, transform.getPosition(), transform.getScaling(), bodyType, relativeTo, entity);

        entity.add(new Physic(body, transform.getPosition().cpy()));
    }

    private BodyDef.BodyType getObjectBodyType(TiledMapTile tile) {
        String classType = tile.getProperties().get("type", "", String.class);

        if("Prop".equals(classType)) {
            return BodyDef.BodyType.StaticBody;
        }

        return BodyDef.BodyType.DynamicBody;
    }

    private void addEntityAnimation(TiledMapTile tile, Entity entity) {
        String animationString = tile.getProperties().get("animation", "", String.class);
        if(animationString.isBlank()) {
            return;
        }

        Animation2D.AnimationType type = Animation2D.AnimationType.valueOf(animationString);
        String atlasAssetString = tile.getProperties().get("atlasAsset", "OBJECTS", String.class);
        AtlasAsset asset = AtlasAsset.valueOf(atlasAssetString);
        FileTextureData textureData = (FileTextureData)tile.getTextureRegion().getTexture().getTextureData();
        String atlasKey = textureData.getFileHandle().nameWithoutExtension();
        float speed = tile.getProperties().get("animationSpeed", 0f, Float.class);

        entity.add(new Animation2D(asset, atlasKey, type, Animation.PlayMode.LOOP, speed));
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
