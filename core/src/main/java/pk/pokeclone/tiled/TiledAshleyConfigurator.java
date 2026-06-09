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
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import lombok.AllArgsConstructor;
import pk.pokeclone.PokeClone;
import pk.pokeclone.asset.AssetService;
import pk.pokeclone.asset.AtlasAsset;
import pk.pokeclone.asset.MapAsset;
import pk.pokeclone.component.*;
import pk.pokeclone.component.Transform;

import java.util.ArrayList;

@AllArgsConstructor
public class TiledAshleyConfigurator {
    private static final Vector2 DEFAULT_PHYSIC_SCALING = new Vector2(1, 1);

    private final Engine engine;
    private final AssetService assetService;
    private final World physicWorld;
    private final MapObjects tmpMapObjects = new MapObjects();
    private final Vector2 tmpVector = new Vector2();

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
        int z = tile.getProperties().get(TiledPropertyNames.Z, 1, Integer.class);

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
        addEntityCameraFollow(tile, entity);
        BodyDef.BodyType bodyType = getObjectBodyType(tile);
        addEntityPhysics(tile.getObjects(), bodyType, Vector2.Zero, entity);
        entity.add(new Facing(Facing.FacingDirection.DOWN));
        entity.add(new Fsm(entity));
        addEntityPlayer(object, entity);

        engine.addEntity(entity);
    }

    private void addEntityPlayer(TiledMapTileMapObject object, Entity entity) {
        if(TiledPropertyNames.PLAYER.equals(object.getName())) {
            entity.add(new Player());
        }
    }

    private void addEntityCameraFollow(TiledMapTile tile, Entity entity) {
        boolean cameraFollow = tile.getProperties().get(TiledPropertyNames.CAMERA_FOLLOW, false, Boolean.class);
        if(!cameraFollow) return;

        entity.add(new CameraFollow());
    }

    private void addEntityPhysics(MapObjects objects, BodyDef.BodyType bodyType, Vector2 relativeTo, Entity entity) {
        if(objects.getCount() == 0) return;

        Transform transform = Transform.MAPPER.get(entity);
        Body body = createBody(objects, transform.getPosition(), transform.getScaling(), bodyType, relativeTo, entity);

        entity.add(new Physic(body, transform.getPosition().cpy()));
    }

    private void addEntityPhysics(MapObject object, BodyDef.BodyType bodyType, Vector2 relativeTo, Entity entity) {
        if(tmpMapObjects.getCount() > 0) {
            tmpMapObjects.remove(0);
        }

        tmpMapObjects.add(object);
        addEntityPhysics(tmpMapObjects, bodyType, relativeTo, entity);
    }

    private BodyDef.BodyType getObjectBodyType(TiledMapTile tile) {
        String classType = tile.getProperties().get(TiledPropertyNames.TYPE, "", String.class);

        if(TiledPropertyNames.PROP.equals(classType)) {
            return BodyDef.BodyType.StaticBody;
        }

        return BodyDef.BodyType.DynamicBody;
    }

    private void addEntityAnimation(TiledMapTile tile, Entity entity) {
        String animationString = tile.getProperties().get(TiledPropertyNames.ANIMATION, "", String.class);
        if(animationString.isBlank()) {
            return;
        }

        Animation2D.AnimationType type = Animation2D.AnimationType.valueOf(animationString);
        String atlasAssetString = tile.getProperties().get(TiledPropertyNames.ATLAS_ASSET, TiledPropertyNames.OBJECTS, String.class);
        AtlasAsset asset = AtlasAsset.valueOf(atlasAssetString);
        FileTextureData textureData = (FileTextureData)tile.getTextureRegion().getTexture().getTextureData();
        String atlasKey = textureData.getFileHandle().nameWithoutExtension();
        float speed = tile.getProperties().get(TiledPropertyNames.ANIMATION_SPEED, 0f, Float.class);

        entity.add(new Animation2D(asset, atlasKey, type, Animation.PlayMode.LOOP, speed));
    }

    private void addEntityMove(TiledMapTile tile, Entity entity) {
        float speed = tile.getProperties().get(TiledPropertyNames.SPEED, 0f, Float.class);

        if(speed == 0) return;

        entity.add(new Move(speed));
    }

    private void addEntityController(TiledMapTile tile, Entity entity) {
        boolean controller = tile.getProperties().get(TiledPropertyNames.CONTROLLER, false, Boolean.class);
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
        String atlasAssetStr = tile.getProperties().get(TiledPropertyNames.ATLAS_ASSET, AtlasAsset.OBJECTS.name(), String.class);
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

    public void onLoadTrigger(TriggerClass triggerClass, MapObject mapObject) {
        Entity entity = engine.createEntity();
        Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();

        addEntityTransform(rectangle.getX(), rectangle.getY(), 0,
            rectangle.getWidth(), rectangle.getHeight(),
            1f, 1f, entity);
        addEntityPhysics(
            mapObject,
            BodyDef.BodyType.StaticBody,
            tmpVector.set(rectangle.getX(), rectangle.getY()).scl(PokeClone.SCALE),
            entity);

        if(triggerClass == TriggerClass.MAP_CHANGE) {
            String mapName = mapObject.getProperties().get(TiledPropertyNames.TARGET_MAP, "", String.class);
            if(mapName.isEmpty()) return;

            MapAsset mapAsset = MapAsset.valueOf(mapName);

            entity.add(new ChangeMapTrigger(mapAsset));
            engine.addEntity(entity);
        } else if(triggerClass == TriggerClass.ADD_INTERACTION) {
            String type = mapObject.getProperties().get(TiledPropertyNames.INTERACTION_TYPE, "", String.class);

            if(type.isEmpty()) return;

            InteractionType interactionType = InteractionType.valueOf(type);

            entity.add(new AddInteractionTrigger(interactionType, null));

            engine.addEntity(entity);
        } else if(triggerClass == TriggerClass.BATTLE) {
            Integer lowerLevel = mapObject.getProperties().get(TiledPropertyNames.LEVEL_LOWER_LIMIT, 1, Integer.class);
            Integer upperLevel = mapObject.getProperties().get(TiledPropertyNames.LEVEL_UPPER_LIMIT, 1, Integer.class);
            String pokemonIds = mapObject.getProperties().get(TiledPropertyNames.POKEMON_IDS, "", String.class);

            if(pokemonIds.isEmpty()) return;

            ArrayList<Integer> pokemonIdList = new ArrayList<>();

            for(String id : pokemonIds.split(",")) {
                pokemonIdList.add(Integer.valueOf(id));
            }

            entity.add(new BattleTrigger(lowerLevel, upperLevel, pokemonIdList));
            engine.addEntity(entity);
        }
    }
}
