package pk.pokeclone.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import pk.pokeclone.PokeClone;
import pk.pokeclone.component.CameraFollow;
import pk.pokeclone.component.Transform;
import pk.pokeclone.tiled.TiledPropertyNames;

public class CameraSystem extends IteratingSystem {
    private final static float SMOOTHING = 4f;

    private final Camera camera;
    private float mapWidth;
    private float mapHeight;
    private final Vector2 targetPosition = new Vector2();

    public CameraSystem(Camera camera) {
        super(Family.all(CameraFollow.class, Transform.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Transform transform = Transform.MAPPER.get(entity);

        calculateCameraPosition(transform.getPosition());

        float progress = SMOOTHING * deltaTime;
        float smoothedX = MathUtils.lerp(camera.position.x, targetPosition.x, progress);
        float smoothedY = MathUtils.lerp(camera.position.y, targetPosition.y, progress);

        camera.position.set(smoothedX, smoothedY, camera.position.z);
    }

    private void calculateCameraPosition(Vector2 entityPosition) {
        float targetX = entityPosition.x;
        float targetY = entityPosition.y;
        float cameraHalfW = camera.viewportWidth / 2;
        float cameraHalfH = camera.viewportHeight / 2;

        if(mapWidth > cameraHalfW) {
            float min = Math.min(cameraHalfW, mapWidth - cameraHalfW);
            float max = Math.max(cameraHalfW, mapWidth - cameraHalfW);
            targetX = MathUtils.clamp(targetX, min, max);
        }

        if(mapHeight > cameraHalfW) {
            float min = Math.min(cameraHalfH, mapHeight - cameraHalfH);
            float max = Math.max(cameraHalfH, mapHeight - cameraHalfH);
            targetY = MathUtils.clamp(targetY, min, max);
        }

        targetPosition.x = targetX;
        targetPosition.y = targetY;
    }

    public void setMap(TiledMap map) {
        int width = map.getProperties().get(TiledPropertyNames.WIDTH, 0, Integer.class);
        int height = map.getProperties().get(TiledPropertyNames.HEIGHT, 0, Integer.class);
        int tileWidth = map.getProperties().get(TiledPropertyNames.TILEWIDTH, 0, Integer.class);
        int tileHeight = map.getProperties().get(TiledPropertyNames.TILEHEIGHT, 0, Integer.class);

        mapWidth = width * tileWidth * PokeClone.SCALE;
        mapHeight = height * tileHeight * PokeClone.SCALE;

        Entity cameraEntity = getEntities().first();

        if(cameraEntity==null) return;

        processEntity(cameraEntity, 0);
    }
}
